package com.nxftl.doc.show.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.show.info.entity.UserInfo;
import com.nxftl.doc.show.info.mapper.UserInfoMapper;
import com.nxftl.doc.show.info.service.IUserInfoService;
import com.nxftl.doc.show.user.entity.User;
import com.nxftl.doc.show.user.mapper.UserMapper;
import com.nxftl.doc.show.user.service.IUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;

    @Resource
    IUserInfoService iUserInfoService;

    @Override
    @Async
    public ApiResult registerUserService(User user) {
        ApiResult success = new ApiResult().success();
        userMapper.insert(user);
        iUserInfoService.addUserInfoService(new UserInfo().setUserId(user.getUserId()).setCreateTime(new Date()));
        return success;
    }




}
