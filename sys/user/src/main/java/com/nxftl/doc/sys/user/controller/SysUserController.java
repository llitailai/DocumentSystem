package com.nxftl.doc.sys.user.controller;

import com.nxftl.doc.common.util.annotation.NotNull;
import com.nxftl.doc.common.util.annotation.RequiredToken;
import com.nxftl.doc.common.util.annotation.ValidAny;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.user.service.ISysUserService;
import io.swagger.annotations.*;
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
@Api(value = "用户Controller",tags = {"用户操作接口"})
public class SysUserController {


    @Resource
    private ISysUserService iSysUserService;

    @PostMapping("/register")
    @ApiOperation("注册后台用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",value = "用户账号",dataType = "String"),
            @ApiImplicitParam(name = "userPass",value = "用户密码",dataType = "String")
    })
    public ApiResult register(@ValidAny(existError = "用户账号不许为空",exist = true) String userAccount, @ValidAny(existError = "用户密码不许为空",exist = true,password = true) String userPass) throws Exception {
        return iSysUserService.registerService(userAccount,userPass);
    }

    @ApiOperation("后台用户登录")
    @PostMapping("/login")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userAccount",value = "用户账号",dataType = "String"),
        @ApiImplicitParam(name = "userPass",value = "用户密码",dataType = "String")
    })
    public ApiResult login(@ValidAny(existError = "用户账号不许为空",exist = true)String userAccount,@ValidAny(existError = "用户密码不许为空",exist = true,password = true)String userPass) throws Exception {
        return iSysUserService.loginService(userAccount,userPass);
    }

    @ApiOperation("后台用户登出,具体实现已在登录拦截器处理")
    @PostMapping("/logOut")
    public void logOut(){

    }
}

