package com.weservice.catering.wtakeout.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.http.HttpStatus;

@ApiModel("返回体")
@Data
public class Response<T> {
    @ApiModelProperty("0成功")
    private int code;
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("返回数据")
    private T data;
    private static final long serialVersionUID = 1L;

    public Response() {
        this.code = 0;
        this.msg = "success";
    }

    public static Response<?> error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static Response<?> error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Response<?> error(int code, String msg) {
        Response<?> r = new Response<>();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public static Response<?> ok(String msg) {
        Response<?> r = new Response<>();
        r.code = 0;
        r.msg = msg;
        return r;
    }

    public static <T> Response<T> ok(T t) {
        Response<T> r = new Response<>();
        r.data = t;
        return r;
    }

    public static Response<?> ok() {
        return new Response<>();
    }


}
