package com.nxftl.doc.common.api;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/20 17:25
 * @discription Api统一返回结果封装对象
 */
public class ApiResult<T>{

    /**
     * 返回状态码
     */
    private String resultCode;

    /**
     * 返回状态信息
     */
    private String resultMsg;

    /**
     * 返回数据
     */
    private T data;

    public ApiResult(String resultCode,String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public ApiResult(String resultCode,String resultMsg,T data){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }

    public ApiResult(){

    }

    public ApiResult<T> success(String resultCode, String resultMsg, T data){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
        return this;
    }

    public ApiResult success(String resultCode,String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }


    public ApiResult fail(String resultCode,String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }

    public ApiResult<T> fail(String resultCode,String resultMsg,T data){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }


}
