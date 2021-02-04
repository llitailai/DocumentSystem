package com.nxftl.doc.show.user.controller;


import com.nxftl.doc.common.util.annotation.ValidAny;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.show.user.entity.User;
import com.nxftl.doc.show.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/show/user")
@Api("前台用户登录")
public class UserController {

    @Resource
    IUserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册前台用户")
    public ApiResult registerUser(@ValidAny(isEntity = true)User user){
        return userService.registerUserService(user);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ApiResult login(@ValidAny(exist = true,existError = "用户密码不能为空") String username,@ValidAny(exist = true,existError = "用户密码不能为空",password = true) String password){
        return userService.loginService(username,password);
    }
}
