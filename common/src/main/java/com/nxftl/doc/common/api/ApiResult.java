package com.nxftl.doc.common.api;


import lombok.Data;

import java.io.Serializable;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/20 17:25
 * @discription Api统一返回结果封装对象
 */
@Data
public class ApiResult<T> implements Serializable {

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回状态信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public ApiResult(ApiCode apiCode){
        this.code = apiCode.getResultCode();
        this.msg = apiCode.getResultMsg();
    }

    public ApiResult(Integer resultCode,String resultMsg){
        this.code = resultCode;
        this.msg = resultMsg;
    }
    public ApiResult(ApiCode apiCode,T data){
        this.code = apiCode.getResultCode();
        this.msg = apiCode.getResultMsg();
        this.data = data;
    }

    public ApiResult(){

    }

    public ApiResult<T> success(Integer code, String resultMsg, T data){
        this.code = code;
        this.msg = resultMsg;
        this.data = data;
        return this;
    }

    public ApiResult success(ApiCode apiCode){
        this.code = apiCode.getResultCode();
        this.msg = apiCode.getResultMsg();
        return this;
    }


    public ApiResult success(){
        this.code = ApiCode.SUCCESS.getResultCode();
        this.msg = ApiCode.SUCCESS.getResultMsg();
        return this;
    }


    public ApiResult fail(ApiCode apiCode){
        this.code = apiCode.getResultCode();
        this.msg = apiCode.getResultMsg();
        return this;
    }

    public ApiResult<T> fail(Integer code,String resultMsg,T data){
        this.code = code;
        this.msg = resultMsg;
        this.data = data;
        return this;
    }

    public ApiResult fail(){
        this.code = ApiCode.ERROR.getResultCode();
        this.msg = ApiCode.ERROR.getResultMsg();
        return this;
    }


}
