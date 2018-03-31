package com.zero.service.impl;

import com.zero.service.LuceneIndexService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jianqing.li
 * @date 2018/3/21
 */
@Service
public class LuceneIndexServiceImpl implements LuceneIndexService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneIndexServiceImpl.class);

    /**
     * 索引存放的位置:磁盘
     */
    private Directory diskDirectory;

    /**
     * 索引写入配置
     */
    private IndexWriterConfig writerConfig;

    /**
     * 分词
     */
    private Analyzer analyzer;

    private IndexWriter indexWriter;

    private IndexReader indexReader;

    /**
     * 索引搜索对象
     */
    private IndexSearcher indexSearcher;

    @Value("${search.lucene.path}")
    private String indexPath;


    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            Path path = Paths.get(indexPath);
            //索引存放位置
            diskDirectory = FSDirectory.open(path);
            //中文分词
//            analyzer = new SmartChineseAnalyzer();
            analyzer = new IKAnalyzer();
            //创建索引写入配置
            writerConfig = new IndexWriterConfig(analyzer);

            LOGGER.info("初始化lucene成功");

        } catch (IOException e) {
            LOGGER.error("初始化lucene异常", e);
            throw e;
        }
    }

    @Override
    public IndexWriter getIndexWriter(boolean create) throws IOException {
        // 操作索引的对象,
//        CREATE：创建一个新的索引或者覆写已经存在的索引
//        APPEND：打开一个已经存在的索引
//        CREATE_OR_APPEND：如果不存在则创建新的索引，如果存在则追加索引
        if (create) {
            writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        } else {
            writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        }
        indexWriter = new IndexWriter(diskDirectory, writerConfig);
        return indexWriter;
    }

    @Override
    public IndexSearcher getIndexSearcher() throws IOException {
        // 创建索引读取器
        indexReader = DirectoryReader.open(diskDirectory);

        //创建一个索引的查找器，来检索索引库
        indexSearcher = new IndexSearcher(indexReader);
        return indexSearcher;
    }

    @Override
    public Analyzer getAnalyzer() {
        return analyzer;
    }


}
