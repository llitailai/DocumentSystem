package com.nxftl.doc.common.util.api;


import com.nxftl.doc.common.util.http.HttpStatus;
import com.nxftl.doc.config.setting.Config;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/20 17:40
 * @discription
 */
public enum ApiCode {

    SUCCESS(HttpStatus.SUCCESS,"操作成功"),
    ERROR(HttpStatus.ACCEPTED,"操作失败"),
    NOT_ONLY(HttpStatus.ACCEPTED,"字段不唯一"),
    NOT_USER(HttpStatus.ACCEPTED,"没有该用户"),
    NOT_TOKEN(HttpStatus.NOT_TOKEN,"未携带Token"),
    LOGIN_FAIL(HttpStatus.ERROR,Config.ACCOUNT_OR_PASSWORD_FAIL),
    INVALID_PASSWORD(HttpStatus.ACCEPTED, Config.PASSWORD_NOT_QUALIFIED),
    INVALID_EMAIL(HttpStatus.ACCEPTED,Config.EMAIL_NOT_QUALIFIED),
    INVALID_TEL(HttpStatus.ACCEPTED,Config.TEL_NOT_QUALIFIED),
    NOT_METHOD(HttpStatus.ERROR,Config.NOT_METHOD),
    PARAM_IS_NULL(HttpStatus.ACCEPTED,"参数为空"),
    TOKEN_INVALID(HttpStatus.ERROR,"Token失效"),
    QUERY_SUCCESS(HttpStatus.SUCCESS,"查询成功"),
    LOGIN_SUCCESS(HttpStatus.SUCCESS,"登录成功"),
    IS_LOG_OUT(HttpStatus.LOG_OUT,"请登录"),
    INSERT_SUCCESS(HttpStatus.SUCCESS,"插入成功"),
    INSERT_ERROR(HttpStatus.ACCEPTED,"插入失败");

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
