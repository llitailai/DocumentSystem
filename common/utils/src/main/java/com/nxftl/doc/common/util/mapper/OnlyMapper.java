package com.nxftl.doc.common.util.mapper;


import com.nxftl.doc.common.util.provider.OnlyProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 10:28
 * @discription
 */
@Repository
public interface OnlyMapper  {


    @SelectProvider(type = OnlyProvider.class,method = "queryOnly")
    Integer isOnly(String tableName, HashMap<StringBuilder,StringBuilder> columnAndValue);
}
