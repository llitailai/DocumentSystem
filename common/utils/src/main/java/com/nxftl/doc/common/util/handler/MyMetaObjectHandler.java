package com.nxftl.doc.common.util.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Auther:
 * @Date:
 * @Description: MP自动填充类
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final Logger LOGGER= LoggerFactory.getLogger(MyMetaObjectHandler.class);


    //insert操作时要填充的字段
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ...");
        //根据属性名字设置要填充的值

        if (metaObject.hasGetter("createTime") && metaObject.hasGetter("updateTime")) {
            this.setFieldValByName("createTime", new Date(), metaObject);
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }

    }

    //update操作时要填充的字段
    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ...");
        if ( metaObject.hasGetter("updateTime")) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}