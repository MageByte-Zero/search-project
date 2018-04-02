package com.zero.controller;

import com.zero.common.ResultInfo;
import com.zero.common.model.Article;
import com.zero.service.ArticleService;
import com.zero.vo.ArticleVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zero.common.exception.ResponseEnum.SUCCESS;

/**
 * @author jianqing.li
 * @date 2018/3/20
 */
@RestController
@RequestMapping("/lucene-search")
public class LuceneController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "全文搜索文章", response = ArticleVO.class)
    @GetMapping(value = "articles")
    public ResultInfo search(@RequestParam("keywords") String keywords,
                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<Article> articleList = articleService.articleSearch(keywords, pageNum, pageSize);
        return SUCCESS.buildNewResultInfo(articleList);
    }
}
