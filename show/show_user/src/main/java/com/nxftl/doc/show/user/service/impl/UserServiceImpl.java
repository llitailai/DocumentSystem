package com.nxftl.doc.show.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.*;
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
import java.util.HashMap;

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
        user.setPassword(MD5.generate(user.getPassword()));
        int result = userMapper.insert(user);
        iUserInfoService.addUserInfoAsyncService(new UserInfo().setUserId(user.getUserId()).setCreateTime(new Date()));
        return result>0?new ApiResult<>().success():new ApiResult<>().fail();
    }

    @Override
    public ApiResult loginService(String account, String password) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, account)
                .or()
                .eq(User::getTel, account));
        if(user == null || !MD5.verify(password,user.getPassword())) throw new BaseException(ApiCode.LOGIN_FAIL);
        return encapsulationResult(user);
    }

    private ApiResult encapsulationResult(User user) {
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("account",getResultValue(user));
        resultMap.put("token", Token.createToken(user.getUserId().longValue(),user.getPassword()));
        return new ApiResult().success(ApiCode.LOGIN_SUCCESS,resultMap);
    }

    private String getResultValue(User user) {
        return StringUtils.isEmpty(user.getEmail())?user.getTel():user.getEmail();
    }


    /**
     * 判断是否重复,如果重复则不可注册
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
