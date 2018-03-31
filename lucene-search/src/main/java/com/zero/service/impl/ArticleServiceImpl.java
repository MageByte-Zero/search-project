package com.zero.service.impl;

import com.zero.common.model.Article;
import com.zero.dao.ArticleMapper;
import com.zero.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jianqing.li
 * @date 2018/3/21
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> listArticles() {
        return articleMapper.listArticles();
    }
}
