package com.nxftl.doc.sys.user.controller;

import com.nxftl.doc.common.api.ApiResult;
import com.nxftl.doc.sys.user.entity.SysUser;
import com.nxftl.doc.sys.user.service.ISysUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author darkltl
 * @since 2021-01-20
 */
@RestController
@RequestMapping("/user/sysUser")
public class SysUserController {


    @Resource
    ISysUserService userService;

    @PostMapping("/register")
    public ApiResult register(@ApiParam("注册用户实体对象") SysUser user) throws Exception {
        return userService.registerService(user);
    }
    
}

