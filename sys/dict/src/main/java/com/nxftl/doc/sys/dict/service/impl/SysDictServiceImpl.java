package com.nxftl.doc.sys.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.VerifyParam;
import com.nxftl.doc.sys.dict.entity.SysDict;
import com.nxftl.doc.sys.dict.mapper.SysDictMapper;
import com.nxftl.doc.sys.dict.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private SysDictMapper dictMapper;

    @Override
    public ApiResult addDictService(String dictName,String dictCode,String pCode) throws Exception {
        SysDict sysDict = new SysDict()
                .setDictName(dictName)
                .setDictCode(dictCode)
                .setPCode(pCode);
        VerifyParam.verifyParam(sysDict.getClass(),sysDict);
        dictMapper.insert(sysDict);
        return new ApiResult().success();
    }

    @Override
    public ApiResult findDictByDictNameService(String dictName)throws Exception {
        VerifyParam.verifyParam(dictName);
        return new ApiResult().success(ApiCode.QUERY_SUCCESS,
                dictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getDictName,dictName)));
    }

    @Override
    public ApiResult findDictByDictCodeService(String dictCode) throws Exception{
        VerifyParam.verifyParam(dictCode);
        return new ApiResult().success(ApiCode.QUERY_SUCCESS,
                dictMapper.selectOne(new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getDictCode,dictCode)));
    }

    @Override
    public ApiResult findDictAnyByPCodeService(String pCode) throws Exception{
        VerifyParam.verifyParam(pCode);
        return new ApiResult().success(ApiCode.QUERY_SUCCESS,
                dictMapper.selectList(new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getPCode,pCode)));
    }
}
