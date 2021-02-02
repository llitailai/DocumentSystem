package com.nxftl.doc.sys.dict.service;

import com.nxftl.doc.common.util.api.ApiResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nxftl.doc.sys.dict.entity.SysDict;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author darkltl
 * @since 2021-01-29
 */
public interface ISysDictService extends IService<SysDict> {
    /**
     * 添加字典项服务
     *  字典表为较为重要的表,很多其他表都受该表依赖
     * @param dictName 字典名称
     * @param dictCode 字典值
     * @param pCode 父级code
     * @return
     */
    ApiResult addDictService(String dictName,String dictCode,String pCode) throws Exception;


    /**
     * 通过字典名称获取字典项(单查询)
     * @param dictName 字典名称
     * @return
     * @throws Exception
     */
    ApiResult findDictByDictNameService(String dictName) throws Exception;

    /**
     * 根据字典英文值获取字典项(单查询)
     * @param dictCode 字典英文值
     * @return
     * @throws Exception
     */
    ApiResult findDictByDictCodeService(String dictCode) throws Exception;

    /**
     * 根据父级Code查询所有属于该父级code下的字典项
     * @param pCode
     * @return
     * @throws Exception
     */
    ApiResult findDictAnyByPCodeService(String pCode) throws Exception;
}
