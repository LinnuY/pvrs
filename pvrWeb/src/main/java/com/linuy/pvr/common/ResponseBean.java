package com.linuy.pvr.common;

import java.io.Serializable;

/**
 * @author LongTeng
 * @date 2021/02/09
 **/
public class ResponseBean<T> implements Serializable {

    private static final Long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE_SUCCESS = "SUCCESS";

    private static final String DEFAULT_MESSAGE_ERROR = "ERROR";

    private static final String CODE_SUCCESS = "0";

    private static final String CODE_ERROR = "-1";

    private String code;

    private String message;

    private String extParam;

    private T data;

    public Boolean isSuccess() {
        return "0".equals(code);
    }

    public Boolean isError() {
        return !isSuccess();
    }

    public ResponseBean()
    {
        this.code = CODE_SUCCESS;
        this.message = DEFAULT_MESSAGE_SUCCESS;
    }

    public ResponseBean(String message) {
        this.message = message;
    }

    public ResponseBean(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public ResponseBean(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseBean(T data) {
        this.data = data;
    }

    public ResponseBean<T> addDefaultError()
    {
        this.code = CODE_ERROR;
        this.message = DEFAULT_MESSAGE_ERROR;
        return this;
    }

    public ResponseBean<T> addError(String message)
    {
        this.code = CODE_ERROR;
        this.message = message;
        return this;
    }

    public ResponseBean<T> addError(String message, String extParam) {
        this.message = message;
        this.extParam = extParam;
        return this;
    }

    public ResponseBean<T> addSuccess()
    {
        this.code = CODE_SUCCESS;
        this.message = DEFAULT_MESSAGE_SUCCESS;
        return this;
    }
    public ResponseBean<T> addSuccess(String message)
    {
        this.message = message;
        return this;
    }

    public ResponseBean<T> addSuccess(T data)
    {
        this.code = CODE_SUCCESS;
        this.message = DEFAULT_MESSAGE_SUCCESS;
        this.data = data;
        return this;
    }

    public ResponseBean<T> addSuccess(String message, T data) {
        this.message = message;
        this.data = data;
        return this;
    }

    public final String getCode() {
        return code;
    }

    public final String getMessage() {
        return message;
    }

    public final String getExtParam() {
        return extParam;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
