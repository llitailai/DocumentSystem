package com.nxftl.doc.sys.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.sys.user.entity.SysUser;
import com.nxftl.doc.sys.user.mapper.SysUserMapper;
import com.nxftl.doc.sys.user.service.ISysUserService;
import org.springframework.stereotype.Service;

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

}
