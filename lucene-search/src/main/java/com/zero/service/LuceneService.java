package com.zero.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;

import java.io.IOException;

/**
 * @author jianqing.li
 * @date 2018/3/21
 */
public interface LuceneService {
    /**
     * 返回索引写入器
     * @param create true=创建一个新的索引或者覆写已经存在的索引，
     *               false=如果不存在则创建新的索引，如果存在则追加索引
     * @return
     * @throws IOException {if path not exist}
     */
    IndexWriter getIndexWriter(boolean create) throws IOException;

    /**
     * 返回索引的查找器，来检索索引库
     * @return
     * @throws IOException {if path not exist}
     */
    IndexSearcher getIndexSearcher() throws IOException;

    /**
     * 获取分词器
     * @return
     */
    Analyzer getAnalyzer();

}
