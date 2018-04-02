package com.zero.service.impl;

import com.zero.common.exception.ServiceException;
import com.zero.common.model.Article;
import com.zero.dao.ArticleMapper;
import com.zero.dao.LuceneDao;
import com.zero.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zero.common.exception.ResponseEnum.SEARCH_ERROR;

/**
 * @author jianqing.li
 * @date 2018/3/21
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private LuceneDao luceneDao;

    @Override
    public List<Article> listArticles() {
        return articleMapper.listArticles();
    }

    @Override
    public List<Article> articleSearch(String keywords, int pageNum, int pageSize) {
        try {
            int start = pageNum * pageSize;
            return luceneDao.listIndexPage(keywords, start, pageSize);
        } catch (Exception e) {
            throw new ServiceException(e, SEARCH_ERROR);
        }
    }
}
