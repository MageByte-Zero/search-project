package com.zero.common.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 资讯文章表
 **/
public class Article implements Serializable, Comparable<Article> {

    /****/
    private Long id;

    /**
     * 创建日期
     **/
    private Date createDate;

    /**
     * 更新日期
     **/
    private Date updateDate;

    /**
     * 是否已删除：0-否、1-是
     **/
    private Integer isDeleted;

    /**
     * 前端排列权重，从0开始，值越大排列越靠前
     **/
    private Integer sequence;

    /**
     * 归属商户唯一编码，爬虫来的房源归属的商户编码为SYS00001
     **/
    private String merchantId;

    /**
     * 所属栏目标识，例如：PLAN_BUY-计划买房、SIGNING_ORDER-签约订房、TRANSFER_RIGHT-产权过户、APPLY_LOAN-贷款办理
     **/
    private String columnKey;

    /**
     * 标题
     **/
    private String title;

    /**
     * 摘要文本
     **/
    private String abstractTxt;

    /**
     * 来源
     **/
    private String source;

    /**
     * 缩略图片entityId
     **/
    private String imgEntityId;

    /**
     * 全文
     **/
    private String content;

    /**
     * 状态：0-草稿、1-发布
     **/
    private Integer status;

    /**
     * 发表时间
     **/
    private String publishTime;

    /**
     * 阅读数
     **/
    private Integer readNum;

    /**
     * 关联国家的英文编码：CN-中国、US-美国、TH-泰国、JP-日本、AU-澳大利亚
     **/
    private String countryCode;

    private String countryName;

    private Integer isNext;

    public Integer getIsNext() {
        return isNext;
    }

    public void setIsNext(Integer isNext) {
        this.isNext = isNext;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getColumnKey() {
        return this.columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractTxt() {
        return this.abstractTxt;
    }

    public void setAbstractTxt(String abstractTxt) {
        this.abstractTxt = abstractTxt;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgEntityId() {
        return this.imgEntityId;
    }

    public void setImgEntityId(String imgEntityId) {
        this.imgEntityId = imgEntityId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getReadNum() {
        return this.readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public int compareTo(Article o) {
        if (o.getSequence().compareTo(this.getSequence()) == 0) {
            return o.getReadNum().compareTo(this.getReadNum());
        } else {
            return o.getSequence().compareTo(this.getSequence());
        }
    }
}
