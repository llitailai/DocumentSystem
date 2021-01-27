package com.nxftl.doc.sys.user.controller;

import com.nxftl.doc.common.util.annotation.RequiredToken;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.user.entity.SysUser;
import com.nxftl.doc.sys.user.service.ISysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("注册后台用户")
    @RequiredToken
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",value = "用户账号",dataType = "String"),
            @ApiImplicitParam(name = "userPass",value = "用户账号",dataType = "String")
    })
    public ApiResult register(String userAccount,String userPass) throws Exception {
        return userService.registerService(new SysUser().setAccount(userAccount).setPassword(userPass));
    }

    @ApiOperation("后台用户登录")
    @PostMapping("/login")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userAccount",value = "用户账号",dataType = "String"),
        @ApiImplicitParam(name = "userPass",value = "用户账号",dataType = "String")
    })
    public ApiResult login(String userAccount,String userPass){
        return new ApiResult().success();
    }
    
}

