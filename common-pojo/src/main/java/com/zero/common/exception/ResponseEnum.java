package com.zero.common.exception;

import com.zero.common.ResultInfo;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 异常信息枚举
 */
public enum ResponseEnum implements IException {

    SUCCESS(200, "OK"),
    FAIL(500, "fail"),
    SYS_ERROR(999, "system error"),

    RESULT_IS_NULL(300, "resultVO is null"),
    BAD_REQUEST(402, "bad request"),
    AUTHORIZATION(403, "无权限"),

    MQ_ERROR(600, "send RabbitMQ exception"),
    ACCOUNT_NOT_EXITS(406, "账号不存在"),
    PASSWORD_ERROR(407, "密码错误"),
    AUTHENTICATION_ERROR(408, "登录校验异常");

    public int code;
    public String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public ServiceException exception() {
        return new ServiceException(this);
    }

    public final <T> ResultInfo<T> buildResultVO(ResultInfo<T> resultVO, T... arg) {
        if (null == resultVO) {
            resultVO = ResultInfo.newInstance();
        }
        T result = ArrayUtils.isNotEmpty(arg) ? arg[0] : null;
        resultVO.setData(result);
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }

    public final <T> ResultInfo<T> buildNewResultVO(T... arg) {
        ResultInfo resultVO = new ResultInfo<>();
        T result = ArrayUtils.isNotEmpty(arg) ? arg[0] : null;
        resultVO.setData(result);
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }
}
