package com.nxftl.doc.common.api;


/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/20 17:40
 * @discription
 */
public enum ApiCode {

    SUCCESS(200,"响应正常");


    private Integer resultCode;

    private String resultMsg;

    private ApiCode(Integer resultCode,String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }


    public Integer getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }


}
