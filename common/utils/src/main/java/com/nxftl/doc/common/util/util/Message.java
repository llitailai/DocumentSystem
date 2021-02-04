package com.nxftl.doc.common.util.util;

import com.nxftl.doc.common.util.api.ApiCode;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/4 10:11
 * @discription
 */
@Data
@Accessors(chain = true)
public class Message {
    private volatile ApiCode apiCode;
}
