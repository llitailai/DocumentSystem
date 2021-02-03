package com.nxftl.doc.show.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.OnlyUtil;
import com.nxftl.doc.config.setting.Config;
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

    @Resource
    OnlyUtil onlyUtil;

    @Override
    public ApiResult registerUserService(User user) {
        distinct(user);
        int result = userMapper.insert(user);
        iUserInfoService.addUserInfoAsyncService(new UserInfo().setUserId(user.getUserId()).setCreateTime(new Date()));
        return result>0?new ApiResult<>().success():new ApiResult<>().fail();
    }

    /**
     * 判断是否重复,如果重复则不可
     * @param user
     */
    private void distinct(User user) {
        onlyUtil.invokeIsOnly(
                onlyUtil.invokeGetTableName(user.getClass()),
                onlyUtil.invokeEncapsulationColumnAndValue(
                        true,
                        Config.EMAIL,user.getEmail(),
                        Config.TEL,user.getTel()));
    }


}
