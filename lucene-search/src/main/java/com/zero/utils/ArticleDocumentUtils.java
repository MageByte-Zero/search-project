package com.zero.utils;

import com.zero.common.model.Article;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * Article转换document工具
 * @author jianqing.li
 * @date 2018/3/31
 */
public class ArticleDocumentUtils {

    /**
     * Article 转换成document 将Article 的值设置到document里面去
     * @param article
     * @return
     */
    public static Document article2Document(Article article) {
        Document document = new Document();
        document.add(new StringField("id", Long.toString(article.getId()), Field.Store.YES));
        document.add(new StringField("title", article.getTitle(), Field.Store.YES));
        //分词
        document.add(new TextField("content", article.getContent(), Field.Store.YES));
        return document;
    }
}
