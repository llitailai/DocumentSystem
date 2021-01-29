package com.nxftl.doc.sys.dict.service;

import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.dict.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param dict
     * @return
     */
    ApiResult addDictService(String dictName,String dictCode,String pCode) throws Exception;
}
