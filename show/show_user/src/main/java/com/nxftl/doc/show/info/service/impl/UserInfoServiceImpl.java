package com.nxftl.doc.show.info.service.impl;

import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.show.info.entity.UserInfo;
import com.nxftl.doc.show.info.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.show.info.service.IUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户个人信息表 服务实现类
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public ApiResult addUserInfoService(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        return new ApiResult().success();
    }
}
