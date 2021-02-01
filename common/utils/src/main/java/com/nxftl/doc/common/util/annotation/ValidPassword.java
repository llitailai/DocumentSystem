package com.nxftl.doc.common.util.annotation;

import com.nxftl.doc.config.setting.Config;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/1 17:30
 * @discription
 */
public @interface ValidPassword {

    public String value() default Config.PASSWORD_NOT_QUALIFIED;

}
