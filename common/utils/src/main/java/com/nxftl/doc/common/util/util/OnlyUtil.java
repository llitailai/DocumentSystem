package com.nxftl.doc.common.util.util;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.mapper.OnlyMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 10:24
 * @discription 判断数据库层面是否唯一
 */
@Component
public class OnlyUtil {

    @Resource
    OnlyMapper onlyMapper;


    /**
     * 查询是否在数据库中是唯一
     * @param tableName 表名
     * @param columnAndValue 字段名和value值
     */
    private void onlyIsTrue(String tableName, HashMap<String,String> columnAndValue){
        isOnly(onlyMapper.isOnly(tableName,columnAndValue));
    }

    private void isOnly(Integer only) {
        if(only>0)
            throw new BaseException(ApiCode.NOT_ONLY);
    }


    /**
     * 封装为HashMap
     * 第一种模式是一比一 也就是 column ,value,column2,value2,column3,value3 ...
     * 第二种模式是多比多 column,column2,column3,value,value2,value3
     * true开启第一种模式,false开启第二种模式(默认第二种)
     * @param columnAndValue 一定要是双数,如果是单数则抛出异常
     * @return
     */
    private HashMap<String,String> encapsulationColumnAndValue(boolean isFirstPattern,String ... columnAndValue){
        isQuantity(columnAndValue);
        return encapsulation(isFirstPattern,columnAndValue);
    }

    private HashMap<String, String> encapsulation(boolean isFirstPattern, String[] columnAndValue) {
        if(isFirstPattern)
            return firstPattern(columnAndValue);

        return secondPattern(columnAndValue);
    }

    private HashMap<String, String> firstPattern(String[] columnAndValue) {
        HashMap<String,String> resultMap = new HashMap<>();
        for (int i = 0, let = columnAndValue.length; i < let; i+=2) {
            resultMap.put(columnAndValue[i],columnAndValue[i+1]);
        }
        return resultMap;
    }

    private HashMap<String, String> secondPattern(String[] columnAndValue) {
        HashMap<String,String> resultMap = new HashMap<>();
        for (int i = 0 ,let = (columnAndValue.length>>1); i < let; i++) {
            resultMap.put(columnAndValue[i],columnAndValue[let+i]);
        }
        return resultMap;
    }

    private void isQuantity(String [] columnAndValue) {
        if((columnAndValue.length & 2) != 0)
            throw new BaseException("columnAndValue is Illegal Argument");
    }

    /**
     * 传入的实体类一定要有TableName注解
     * @param cls
     * @return
     */
    private String getTableName(Class<?> cls){
        return isHaveTableNameAnnotation(cls).value();
    }


    private TableName isHaveTableNameAnnotation(Class<?> cls){
        TableName annotation = cls.getAnnotation(TableName.class);
        if(annotation == null){
            throw new BaseException("annotation TableName not found ");
        }
        return annotation;
    }


    public void invokeIsOnly(String taleName,HashMap<String,String> columnAndValue){
        onlyIsTrue(taleName,columnAndValue);
    }

    public HashMap<String,String> invokeEncapsulationColumnAndValue(boolean isFirstPattern, String ... columnAndValue){
        return encapsulationColumnAndValue(isFirstPattern,columnAndValue);
    }

    public String invokeGetTableName(Class<?> cls){
        return getTableName(cls);
    }


}
