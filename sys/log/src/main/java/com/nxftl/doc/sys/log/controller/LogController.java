package com.nxftl.doc.sys.log.controller;

import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.log.entity.LogEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @GetMapping("/getLogNumber")
    @ResponseBody
    public ApiResult getLogNum(){
        return new ApiResult(ApiCode.SUCCESS,new LogEntity()
                .setTestEntityNum(1)
                .setTestEntityName("testFastJson")
                .setTestEntityAddr("NXFTL"));
    }
}
