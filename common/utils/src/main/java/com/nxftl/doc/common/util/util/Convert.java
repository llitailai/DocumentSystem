package com.nxftl.doc.common.util.util;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/5 9:28
 * @discription
 */
public interface Convert {

    /**
     * 铜鼓参数args转换城为Map
     * 参数传递请遵从 key value key value
     * key : 参数名, 指定为传入的参数名
     * value : 参数值
     * @param args
     * @return
     */
    Map<String,Object> convertMapByStrings(boolean analysis,String ... args);


    public static class ConvertImpl implements Convert{

        @Override
        public Map<String, Object> convertMapByStrings(boolean analysis,String... args) {
            isQuantity(ConvertMap.builder().args(args).analysis(analysis).build());
            return null;
        }


        private void isQuantity(ConvertMap convertMap) {
            if((convertMap.getArgs().length & 2) != 0)
                throw new BaseException("columnAndValue is Illegal Argument");

        }


        @Data
        @Accessors(chain = true)
        @Builder
        private static class ConvertMap{

            private boolean analysis;

            private String [] args;

        }
    }
}
