package com.nxftl.doc.sys.log.controller;

import com.nxftl.doc.common.api.ApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/21 10:57
 * @discription
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @GetMapping("/getLog")
    public void getLogNumber() throws Exception{
        throw new Exception();
    }
}
