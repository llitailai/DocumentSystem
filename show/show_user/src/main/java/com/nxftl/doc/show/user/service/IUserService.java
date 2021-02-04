package com.nxftl.doc.show.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.show.user.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
public interface IUserService extends IService<User> {

    /**
     * 异步方法不要返回值,发生异常由全局异常处理
     * @param user
     */
    ApiResult registerUserService(User user);


    /**
     * 登录服务
     * @param account 账号
     * @param password 密码1
     * @return
     */
    ApiResult loginService(String account,String password);
}
