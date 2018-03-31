package com.zero.dao;

import com.zero.common.model.Article;

import java.util.List;

/**
 * @author jianqing.li
 * @date 2018/3/22
 */
public interface ArticleMapper {

    List<Article> listArticles();

    List<Article> getPreAndNext(long id);
}
