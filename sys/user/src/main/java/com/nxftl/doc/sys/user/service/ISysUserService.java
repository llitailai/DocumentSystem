package com.nxftl.doc.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.user.entity.SysUser;

import java.util.HashMap;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author darkltl
 * @since 2021-01-20
 */
public interface ISysUserService extends IService<SysUser> {


    ApiResult registerService(SysUser user) throws Exception;

    ApiResult loginService(String userAccount,String password) throws Exception;


    String getPasswordById(Long userId);
}
