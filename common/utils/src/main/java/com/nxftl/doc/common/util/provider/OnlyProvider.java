package com.nxftl.doc.common.util.provider;

import com.nxftl.doc.common.util.util.BaseException;
import com.nxftl.doc.common.util.util.StringUtils;
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


    public String queryOnly(String tableName, HashMap<StringBuilder,StringBuilder> columnAndValue){
        verify(tableName,columnAndValue);
        SQL sql = new SQL().SELECT("1").FROM(tableName);
        sql.WHERE(appendSql(columnAndValue)).LIMIT(1);
        return sql.toString();
    }

    private String appendSql(HashMap<StringBuilder, StringBuilder> columnAndValue) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<StringBuilder, StringBuilder> column : columnAndValue.entrySet()) {
            if(StringUtils.isEmptyNotNullStr(column.getValue().toString()))
                continue;
            builder.append(column.getKey().append("='").append(column.getValue().append("'\ror\r")));
        }
        builder.trimToSize();
        return builder.substring(0,builder.lastIndexOf("or"));
    }


    private void verify(String tableName,HashMap<StringBuilder,StringBuilder> value){
        if(StringUtils.isEmpty(tableName) || StringUtils.isEmpty(value))
            throw new BaseException("NullPointerException");
    }

}
