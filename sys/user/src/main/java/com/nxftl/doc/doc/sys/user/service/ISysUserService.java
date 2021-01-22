package com.nxftl.doc.doc.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nxftl.doc.doc.common.api.ApiResult;
import com.nxftl.doc.doc.sys.user.entity.SysUser;

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

}
