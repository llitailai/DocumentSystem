package com.nxftl.doc.doc.common.api;

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
    private Integer resultCode;

    /**
     * 返回状态信息
     */
    private String resultMsg;

    /**
     * 返回数据
     */
    private T data;

    public ApiResult(ApiCode apiCode){
        this.resultCode = apiCode.getResultCode();
        this.resultMsg = apiCode.getResultMsg();
    }

    public ApiResult(Integer resultCode,String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
    public ApiResult(ApiCode apiCode,T data){
        this.resultCode = apiCode.getResultCode();
        this.resultMsg = apiCode.getResultMsg();
        this.data = data;
    }

    public ApiResult(){

    }

    public ApiResult<T> success(Integer resultCode, String resultMsg, T data){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
        return this;
    }

    public ApiResult success(ApiCode apiCode){
        this.resultCode = apiCode.getResultCode();
        this.resultMsg = apiCode.getResultMsg();
        return this;
    }


    public ApiResult success(){
        this.resultCode = ApiCode.SUCCESS.getResultCode();
        this.resultMsg = ApiCode.SUCCESS.getResultMsg();
        return this;
    }


    public ApiResult fail(ApiCode apiCode){
        this.resultCode = apiCode.getResultCode();
        this.resultMsg = apiCode.getResultMsg();
        return this;
    }

    public ApiResult<T> fail(Integer resultCode,String resultMsg,T data){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
        return this;
    }

    public ApiResult fail(){
        this.resultCode = ApiCode.ERROR.getResultCode();
        this.resultMsg = ApiCode.ERROR.getResultMsg();
        return this;
    }


}
