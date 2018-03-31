package com.zero.lucene;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.zero.Application;
import com.zero.common.model.Article;
import com.zero.service.ArticleService;
import com.zero.service.LuceneService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * @author jianqing.li
 * @date 2018/3/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class LuceneTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneTest.class);

    @Autowired
    private LuceneService luceneService;

    @Autowired
    private ArticleService articleService;

    /**
     * 索引创建
     */
    @Test
    public void testIndexWriter() {
        IndexWriter indexWriterOfDisk = null;
        try {
            indexWriterOfDisk = luceneService.getIndexWriter(true);
            //创建Document对象
            Document doc;
            //为Document 添加Field
            List<Article> list = articleService.listArticles();
            for (Article item : list) {
                doc = new Document();
//                doc.add(new LongPoint("id", item.getId()));
//                doc.add(new StoredField("id", item.getId()));

                doc.add(new StringField("id", Long.toString(item.getId()), Field.Store.YES));
                doc.add(new StringField("title", item.getTitle(), Field.Store.YES));
                //分词
                doc.add(new TextField("content", item.getContent(), Field.Store.YES));

                //将doc对象保存到索引库中
                indexWriterOfDisk.addDocument(doc);
            }
            indexWriterOfDisk.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexWriterOfDisk != null) {
                try {
                    indexWriterOfDisk.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 更新文档：实际上就是删除后新增一条
     */
    @Test
    public void testUpdateDocument() throws IOException {
        long id = 567;
        LOGGER.info("修改id{},data={}", id);
        testQueryById();
        try (IndexWriter indexWriter = luceneService.getIndexWriter(false)) {

            Term term = new Term("id", String.valueOf(id));
            Document doc = new Document();

            //更新的时候失败
//            doc.add(new LongPoint("id", id));
//            doc.add(new StoredField("id", id));

            doc.add(new StringField("id", Long.toString(id), Field.Store.YES));
            doc.add(new StringField("title", "迈阿密 - 第二大金融中心修改2", Field.Store.YES));
            doc.add(new TextField("content", "dfhg ghdfgjdjdfhsdfhgs爱上的奇偶干2", Field.Store.YES));
            long count = indexWriter.updateDocument(term, doc);
            indexWriter.commit();
            LOGGER.info("修改成功：{}", count);

        }
        testQueryById();
    }

    /**
     * 删除文档
     *
     * @throws IOException
     */
    @Test
    public void testDeleteDocument() throws IOException {

        try (IndexWriter indexWriter = luceneService.getIndexWriter(false)) {

            String field = "title";
            String value = "迈阿密 - 第二大金融中心";
            long count = indexWriter.deleteDocuments(new Term(field, value));
            indexWriter.commit();
            LOGGER.info("删除完成：{}", count);
            testTermQuery();
        }

    }

    /**
     * deleteDocuments(Query… queries)：删除所有匹配到查询语句的Document
     * deleteDocuments(Term… terms)：删除所有包含有terms的Document
     * deleteAll()：删除索引中所有的Document
     * NOTES: deleteDocuments(Term… terms)方法，只接受Term参数，而Term只提供如下四个构造函数
     * <p>
     * Term(String fld, BytesRef bytes)
     * Term(String fld, BytesRefBuilder bytesBuilder)
     * Term(String fld, String text)
     * Term(String fld)
     * 所以我们无法使用deleteDocuments(Term… terms)去删除一些非String值的Field，例如IntPoint，LongPoint，FloatPoint，DoublePoint等。
     * 这时候就需要借助传递Query实例的方法去删除包含某些特定类型Field的Document。
     */
    @Test
    public void testDeleteByQuery() throws IOException {
        try (IndexWriter indexWriter = luceneService.getIndexWriter(false)) {
            Query query = LongPoint.newExactQuery("id", 569);
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
            LOGGER.info("删除成功");
            testLongPointQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 按词条搜索
     * <p>
     * TermQuery是最简单、也是最常用的Query。TermQuery可以理解成为“词条搜索”，
     * 在搜索引擎中最基本的搜索就是在索引中搜索某一词条，而TermQuery就是用来完成这项工作的。
     * 在Lucene中词条是最基本的搜索单位，从本质上来讲一个词条其实就是一个名/值对。
     * 只不过这个“名”是字段名，而“值”则表示字段中所包含的某个关键字。
     *
     * @throws IOException
     */
    @Test
    public void testTermQuery() throws IOException {
        String searchField = "title";
        TermQuery query = new TermQuery(new Term(searchField, "迈阿密 - 第二大金融中心"));
        //执行查询，并打印查询到的记录数
        executeQuery(query, 100);
    }

    @Test
    public void testQueryById() throws IOException {
        String searchField = "id";
        String value = "567";
        TermQuery query = new TermQuery(new Term(searchField, value));
        //执行查询，并打印查询到的记录数
        executeQuery(query, 100);
    }

    /**
     * 多条件查询
     * <p>
     * BooleanQuery也是实际开发过程中经常使用的一种Query。
     * 它其实是一个组合的Query，在使用时可以把各种Query对象添加进去并标明它们之间的逻辑关系。
     * BooleanQuery本身来讲是一个布尔子句的容器，它提供了专门的API方法往其中添加子句，
     * 并标明它们之间的关系，以下代码为BooleanQuery提供的用于添加子句的API接口：
     *
     * @throws IOException
     */
    @Test
    public void booleanQueryTest() {

        String searchField1 = "title";
        String searchField2 = "content";

        Query termQuery1 = new TermQuery(new Term(searchField1, "美国买房贷款流程"));
        Query termQuery2 = new TermQuery(new Term(searchField2, "美国买房"));

        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        // BooleanClause用于表示布尔查询子句关系的类，
        // 包 括：
        // BooleanClause.Occur.MUST，
        // BooleanClause.Occur.MUST_NOT，
        // BooleanClause.Occur.SHOULD。
        // 必须包含,不能包含,可以包含三种.有以下6种组合：
        //
        // 1．MUST和MUST：取得连个查询子句的交集。
        // 2．MUST和MUST_NOT：表示查询结果中不能包含MUST_NOT所对应得查询子句的检索结果。
        // 3．SHOULD与MUST_NOT：连用时，功能同MUST和MUST_NOT。
        // 4．SHOULD与MUST连用时，结果为MUST子句的检索结果,但是SHOULD可影响排序。
        // 5．SHOULD与SHOULD：表示“或”关系，最终检索结果为所有检索子句的并集。
        // 6．MUST_NOT和MUST_NOT：无意义，检索无结果。
        builder.add(termQuery1, BooleanClause.Occur.SHOULD);
        builder.add(termQuery2, BooleanClause.Occur.SHOULD);

        BooleanQuery query = builder.build();

        try {
            executeQuery(query, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 非String 值的field搜索
     *
     * @throws IOException
     */
    @Test
    public void testLongPointQuery() throws IOException {
        Query query = LongPoint.newExactQuery("id", 567);
        executeQuery(query, 100);
    }

    /**
     * 匹配前缀：用于匹配其索引开始以指定的字符串的文档。就是文档中存在xxx%
     */
    @Test
    public void prefixQueryTest() {
        String searchField = "title";
        Term term = new Term(searchField, "旧金山 - 西海岸门户");
        PrefixQuery query = new PrefixQuery(term);
        try {
            executeQuery(query, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 短词搜索
     * 所谓PhraseQuery，就是通过短语来检索，比如我想查“big car”这个短语，
     * 那么如果待匹配的document的指定项里包含了"big car"这个短语，
     * 这个document就算匹配成功。可如果待匹配的句子里包含的是“big black car”，
     * 那么就无法匹配成功了，如果也想让这个匹配，就需要设定slop，
     * 先给出slop的概念：slop是指两个项的位置之间允许的最大间隔距离
     *
     * @throws IOException
     */
    @Test
    public void phraseQueryTest() throws IOException {
        String searchField = "content";
        String query1 = "如今的";
        String query2 = "美国";

        PhraseQuery.Builder builder = new PhraseQuery.Builder();

        builder.add(new Term(searchField, query1));
        builder.add(new Term(searchField, query2));
        builder.setSlop(0);
        PhraseQuery phraseQuery = builder.build();
        executeQuery(phraseQuery, 100);
    }

    /**
     * 相近词语搜索:
     * FuzzyQuery是一种模糊查询，它可以简单地识别两个相近的词语。
     *
     * @throws IOException
     */
    @Test
    public void fuzzQueryTest() throws IOException {
        String searchField = "content";
        Term term = new Term(searchField, "美国买房");
        FuzzyQuery fuzzyQuery = new FuzzyQuery(term);
        executeQuery(fuzzyQuery, 10);
    }

    /**
     * 通配符搜索
     * <p>
     * Lucene也提供了通配符的查询，这就是WildcardQuery。
     * 通配符“?”代表1个字符，而“*”则代表0至多个字符。
     *
     * @throws IOException
     */
    @Test
    public void wildcardQueryTest() throws IOException {
        String searchField = "content";
        Term term = new Term(searchField, "美国*房产");
        Query wildcardQuery = new WildcardQuery(term);
        executeQuery(wildcardQuery, 10);
    }

    /**
     * 分词查询：最常用应该
     *
     * @throws IOException
     */
    @Test
    public void queryParseTest() throws IOException {
        String searchField = "content";
        Analyzer analyzer = luceneService.getAnalyzer();

        //指定搜索词段和分析器
        QueryParser parser = new QueryParser(searchField, analyzer);
        try {
            Query parse = parser.parse("美国买房");
            executeQuery(parse, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 多个Field分词查询
     */
    @Test
    public void multiFieldQueryParserTest() {
        Analyzer analyzer = luceneService.getAnalyzer();
        String[] fieldStr = new String[]{"title", "content"};
        QueryParser queryParser = new MultiFieldQueryParser(fieldStr, analyzer);

        try {
            Query query = queryParser.parse("美国国家概述");
            executeQuery(query, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分词测试
     * IKAnalyzer  中文分词器
     * SmartChineseAnalyzer  smartcn分词器 需要lucene依赖 且和lucene版本同步
     *
     * @throws IOException
     */
    @Test
    public void analyzerTest() throws IOException {
        //Analyzer analyzer = new StandardAnalyzer(); // 标准分词器，适用于英文
        //Analyzer analyzer = new SmartChineseAnalyzer();//中文分词
        //Analyzer analyzer = new ComplexAnalyzer();//中文分词
        //Analyzer analyzer = new IKAnalyzer();//中文分词
        Analyzer analyzer = null;
        String text = "Apache Spark 是专为大规模数据处理而设计的快速通用的计算引擎";
//        analyzer = new IKAnalyzer();//IKAnalyzer 中文分词
//        printAnalyzerDoc(analyzer, text);
//        LOGGER.info("*********************");

        analyzer = new ComplexAnalyzer();//MMSeg4j 中文分词
        printAnalyzerDoc(analyzer, text);
        LOGGER.info("*********************");

//        analyzer = new SmartChineseAnalyzer();//Lucene 中文分词器
//        printAnalyzerDoc(analyzer, text);

    }

    /**
     * 高亮处理
     */
    @Test
    public void highlighterTest() throws ParseException, IOException, InvalidTokenOffsetsException {
        Analyzer analyzer = luceneService.getAnalyzer();
        String searchField = "content";
        String text = "美国买房";
        //指定搜索字段和分析器
        QueryParser queryParser = new QueryParser(searchField, analyzer);

        Query query = queryParser.parse(text);
        IndexSearcher indexSearcher = luceneService.getIndexSearcher();
        TopDocs topDocs = indexSearcher.search(query, 100);

        // 关键字高亮显示的html标签，需要导入lucene-highlighter-xxx.jar
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            // 内容增加高亮显示
            String content = document.get(searchField);
            TokenStream tokenStream = analyzer.tokenStream(searchField, new StringReader(content));
            String contentHighlighter = highlighter.getBestFragment(tokenStream, content);
            System.out.println(contentHighlighter);
        }


    }

    /**
     * 执行查询，并打印查询到的记录数
     *
     * @param query
     * @param pageSize
     * @throws IOException
     */
    public void executeQuery(Query query, int pageSize) throws IOException {
        IndexSearcher indexSearcher = luceneService.getIndexSearcher();
        TopDocs topDocs = indexSearcher.search(query, pageSize);
        LOGGER.info("总共查询到{}个文档", topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //取得对应的文档对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            LOGGER.info("id:{},title:{},content:{}", document.get("id"), document.get("title"), document.get("content"));
        }
    }

    /**
     * 分词打印
     *
     * @param analyzer
     * @param text
     * @throws IOException
     */
    public void printAnalyzerDoc(Analyzer analyzer, String text) throws IOException {
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                LOGGER.info(charTermAttribute.toString());
            }
            tokenStream.end();
        } finally {
            tokenStream.close();
            analyzer.close();
        }
    }


}
