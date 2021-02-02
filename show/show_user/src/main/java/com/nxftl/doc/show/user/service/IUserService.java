package com.nxftl.doc.show.user.service;

import com.nxftl.doc.common.util.api.ApiResult;
import com.baomidou.mybatisplus.extension.service.IService;
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

    ApiResult registerUserService(User user);
}
