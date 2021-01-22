package com.nxftl.doc.doc.sys.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.doc.common.api.ApiCode;
import com.nxftl.doc.doc.common.api.ApiResult;
import com.nxftl.doc.doc.common.util.VerifyParam;
import com.nxftl.doc.doc.sys.user.entity.SysUser;
import com.nxftl.doc.doc.sys.user.service.ISysUserService;
import com.nxftl.doc.doc.sys.user.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author darkltl
 * @since 2021-01-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    SysUserMapper userMapper;

    @Override
    public ApiResult registerService(SysUser user) throws Exception {
        VerifyParam.verifyParam(user.getClass());
        return userMapper.insert(user)>0?new ApiResult(ApiCode.INSERT_SUCCESS):new ApiResult(ApiCode.INSERT_ERROR);
    }
}
