package com.nxftl.doc.doc.sys.log.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/21 10:57
 * @discription
 */
@RestController
@RequestMapping("/sample")
@ApiIgnore(value = "日志统计接口")
public class SampleController {

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @GetMapping("/getLog")
    public void getLogNumber() throws Exception{
        throw new Exception("aaabbcc");
    }
}
