package com.nxftl.doc.common.handler;

import com.nxftl.doc.common.api.ApiCode;
import com.nxftl.doc.common.api.ApiResult;
import com.nxftl.doc.common.util.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class DocExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult exceptionHandler(Exception e){
        LOGGER.error(e.getMessage());
        Logger logger3 = LogManager.getLogger("MailLog");
        logger3.error(e.getMessage());
        e.printStackTrace();
        return new ApiResult(ApiCode.ERROR);
    }


    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ApiResult baseExceptionHandler(BaseException e){
        e.printStackTrace();
        LOGGER.error(e.getMessage());
        return new ApiResult(e.getErrorCode(),e.getErrorMsg());
    }


}
