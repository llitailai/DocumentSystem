package com.nxftl.doc.config.setting.reg;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/1 17:24
 * @discription
 */
public enum Reg {
    EMAIL("邮箱校验","^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"),
    PHONE("电话号码校验","^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

    private String regName;

    private String regValue;

    Reg(String regName, String regValue) {
        this.regName = regName;
        this.regValue = regValue;
    }

    public String getRegName() {
        return regName;
    }

    public String getRegValue() {
        return regValue;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public void setRegValue(String regValue) {
        this.regValue = regValue;
    }
}
