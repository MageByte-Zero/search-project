package com.zero.dao;

import com.zero.common.model.Article;
import com.zero.service.LuceneIndexService;
import com.zero.utils.ArticleDocumentUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * lucene操作索引库:增删改索引都是通过indexWriter对象完成
 *
 * @author jianqing.li
 * @date 2018/3/31
 */
@Repository
public class LuceneDao {

    private static final Logger LOG =  LoggerFactory.getLogger(LuceneDao.class);

    @Autowired
    private LuceneIndexService luceneIndexService;

    /**
     * 添加索引
     * @param article
     * @throws IOException
     */
    public void addIndex(Article article) throws IOException {
        try (IndexWriter indexWriter = luceneIndexService.getIndexWriter(false)) {
            Document document = ArticleDocumentUtils.article2Document(article);
            indexWriter.addDocument(document);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 删除索引
     * @param fieldName
     * @param fieldValue
     * @throws IOException
     */
    public void deleteIndex(String fieldName, String fieldValue) throws IOException {
        try (IndexWriter indexWriter = luceneIndexService.getIndexWriter(false)) {
            Term term = new Term(fieldName, fieldValue);
            indexWriter.deleteDocuments(term);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 更新索引
     * @param fieldName
     * @param fieldValue
     * @param article
     * @throws IOException
     */
    public void updateIndex(String fieldName, String fieldValue, Article article) throws IOException {
        try (IndexWriter indexWriter = luceneIndexService.getIndexWriter(false)) {
            Term term = new Term(fieldName, fieldValue);
            Document document = ArticleDocumentUtils.article2Document(article);
            indexWriter.updateDocument(term, document);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 分页查找
     * @param keywords 关键字
     * @param start 开始位置
     * @param rows 条数
     * @return
     */
    public List<Article> listIndexPage(String keywords, int start, int rows) throws Exception {
        Analyzer analyzer = luceneIndexService.getAnalyzer();
        // 需要根据哪几个字段进行检索...
        String titleField = "title";
        String contentField = "content";
        String[] fieldStr = new String[]{titleField, contentField};
        // 查询分析程序（查询解析）
        QueryParser queryParser = new MultiFieldQueryParser(fieldStr, analyzer);
        //不同的规则构造不同的子类...
        Query query = queryParser.parse(keywords);
        IndexSearcher indexSearcher = luceneIndexService.getIndexSearcher();
        //这里检索的是索引目录,会把整个索引目录都读取一遍
        //根据query查询，返回前N条
        TopDocs topDocs = indexSearcher.search(query, start + rows);
        LOG.info("总记录数={}", topDocs.totalHits);

        /**添加设置文字高亮begin*/
        //htmly页面高亮显示的格式化，默认是<b></b>即加粗
        Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        Scorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);

        //设置文字摘要（高亮的部分），此时摘要大小为10
        //int fragmentSize = 10;
        Fragmenter fragmenter = new SimpleFragmenter();
        highlighter.setTextFragmenter(fragmenter);
        /**添加设置文字高亮end*/

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        //防止数组溢出
        int endResult = Math.min(scoreDocs.length, start + rows);

        Article article;
        List<Article> articleList = new ArrayList<>();
        for (int i = start; i< endResult; i++) {
            article = new Article();
            //docID lucene的索引库里面有很多的document，lucene为每个document定义了一个编号，唯一标识，自增长
            int docID = scoreDocs[i].doc;
            LOG.info("标识docID={}", docID);
            Document document = indexSearcher.doc(docID);
            /**获取文字高亮的信息begin*/
            LOG.info("==========================");
            TokenStream tokenStream = analyzer.tokenStream(contentField, new StringReader(document.get(contentField)));
            String content = highlighter.getBestFragment(tokenStream, document.get(contentField));
            LOG.info("content={}", content);
            LOG.info("==========================");
            /**获取文字高亮的信息end*/

            article.setId(Long.parseLong(document.get("id")));
            article.setTitle(document.get(titleField));
            article.setContent(document.get(contentField));
            articleList.add(article);
        }

        return articleList;
    }

}
