package com.nxftl.doc.common.util.provider;

import com.nxftl.doc.common.util.annotation.NotNull;
import com.nxftl.doc.common.util.util.BaseException;
import com.nxftl.doc.common.util.util.StringUtils;
import com.nxftl.doc.config.setting.Config;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 10:31
 * @discription
 */
public class OnlyProvider {


    public String queryOnly(String tableName, HashMap<String,String> columnAndValue){
        verify(tableName,columnAndValue);
        SQL sql = new SQL().SELECT("count(1)").FROM(tableName);
        sql.WHERE(appendSql(columnAndValue));
        return sql.toString();
    }

    private String appendSql(HashMap<String, String> columnAndValue) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> column : columnAndValue.entrySet()) {
            if(StringUtils.isEmpty(column.getValue()))
                continue;
            builder.append(column.getKey()+"='"+column.getValue()+"',");
        }
        return builder.toString().substring(0,builder.length()-1);
    }


    private void verify(String tableName,HashMap<String,String> value){
        if(StringUtils.isEmpty(tableName) || StringUtils.isEmpty(value))
            throw new BaseException("NullPointerException");
    }

}
