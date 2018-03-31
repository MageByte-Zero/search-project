package com.zero.dao;

import com.zero.common.model.Article;
import com.zero.service.LuceneIndexService;
import com.zero.utils.ArticleDocumentUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

/**
 * lucene操作索引库:增删改索引都是通过indexWriter对象完成
 *
 * @author jianqing.li
 * @date 2018/3/31
 */
@Repository
public class LuceneDao {

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
        return null;
    }

}
