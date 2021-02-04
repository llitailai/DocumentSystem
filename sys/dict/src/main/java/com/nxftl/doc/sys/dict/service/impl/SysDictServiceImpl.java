package com.nxftl.doc.sys.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.VerifyParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.sys.dict.entity.SysDict;
import com.nxftl.doc.sys.dict.mapper.SysDictMapper;
import com.nxftl.doc.sys.dict.service.ISysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author darkltl
 * @since 2021-01-29
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public ApiResult addDictService(String dictName,String dictCode,String pCode){
        SysDict sysDict = new SysDict()
                .setDictName(dictName)
                .setDictCode(dictCode)
                .setPCode(pCode);
        sysDictMapper.insert(sysDict);
        return new ApiResult().success();
    }

    @Override
    public ApiResult findDictByDictNameService(String dictName) {
        return new ApiResult().success(ApiCode.QUERY_SUCCESS,
                sysDictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getDictName,dictName)));
    }

    @Override
    public ApiResult findDictByDictCodeService(String dictCode){
        return new ApiResult().success(ApiCode.QUERY_SUCCESS,
                sysDictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getDictCode,dictCode)));
    }

    @Override
    public ApiResult findDictAnyByPCodeService(String pCode){
        return new ApiResult().success(ApiCode.QUERY_SUCCESS,
                sysDictMapper.selectList(new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getPCode,pCode)));
    }
}
