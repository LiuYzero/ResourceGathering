package com.liuyang.toolbox.ResourceGathering.entity;

import lombok.Data;

/**
 * API接口统一返回类
 */
@Data

public class ResponseResult<T> {

    private Integer code;
    private String message;
    private T data;

    public static ResponseResult<Void> success(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResultCode.SUCCESS.getCode());
        responseResult.setMessage(ResultCode.SUCCESS.getMessage());
        return responseResult;
    }

    public static <T> ResponseResult<T> success(T t){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResultCode.SUCCESS.getCode());
        responseResult.setMessage(ResultCode.SUCCESS.getMessage());
        responseResult.setData(t);
        return responseResult;
    }

    public static ResponseResult<Void> faild(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResultCode.FAILD.getCode());
        responseResult.setMessage(ResultCode.FAILD.getMessage());
        return responseResult;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
