package com.zero.common;

import com.zero.common.exception.ResponseEnum;

import java.io.Serializable;

/**
 * 通用VO结果集
 * Created by jianqing.li on 2017/6/19.
 */
public class ResultInfo<T> implements Serializable {

    private boolean success = false;

    private int code;

    private String msg;

    private T data;

    public ResultInfo() {
    }

    public static <T> ResultInfo newInstance() {
        return new ResultInfo<T>();
    }

    public ResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        if (code == ResponseEnum.SUCCESS.getCode()) {
            this.success = true;
        }
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
