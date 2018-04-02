package com.zero.service;

import com.zero.common.model.Article;

import java.util.List;

/**
 * @author jianqing.li
 * @date 2018/3/21
 */
public interface ArticleService {
    List<Article> listArticles();

    /**
     * 全文搜索文章（lucene）
     * @param keywords
     * @param pageNum
     *@param pageSize @return
     */
    List<Article> articleSearch(String keywords, int pageNum, int pageSize);

}
