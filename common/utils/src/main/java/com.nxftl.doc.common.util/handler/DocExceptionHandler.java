package com.nxftl.doc.common.util.handler;

import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author darkltl
 * @date 2020/12/29 16:20
 * @discription
 * @className DocExceptionHandler
 * @group com.alpha.crm.handle
 */
@ControllerAdvice
@Slf4j
public class DocExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult exceptionHandler(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return new ApiResult(ApiCode.ERROR);
    }


    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ApiResult baseExceptionHandler(BaseException e){
        e.printStackTrace();
        log.warn(e.getMessage());
        return new ApiResult(e.getErrorCode(),e.getErrorMsg());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ApiResult runTimeExceptionHandler(RuntimeException e){
        log.warn(e.getMessage());
        return new ApiResult(ApiCode.ERROR);
    }
}
