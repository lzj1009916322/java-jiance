package com.weservice.catering.wtakeout.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

/**
 * @author
 * @date 2020/10/31 16:31
 **/
@ApiModel("响应消息体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
        this.code = 0;
        this.msg = "success";
    }
    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok(){
        return new Result();
    }

    public static Result error(){
        return new Result(-1,"error");
    }

    public static Result error(int code){
        return new Result(code,"error");
    }
    public static Result error(String msg){
        return new Result(-1,msg);
    }

    public static Result error(int code,String msg){
        return new Result(-1,msg);
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
}
