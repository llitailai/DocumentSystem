package com.nxftl.doc.sys.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nxftl.doc.common.util.annotation.NotNull;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.*;
import com.nxftl.doc.config.setting.Config;
import com.nxftl.doc.sys.user.entity.SysUser;
import com.nxftl.doc.sys.user.mapper.SysUserMapper;
import com.nxftl.doc.sys.user.service.ISysUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author darkltl
 * @since 2021-01-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    SysUserMapper userMapper;


    @Override
    public ApiResult registerService(SysUser user) throws Exception {
        VerifyParam.verifyParam(user.getClass());
        return userMapper.insert(user)>0?new ApiResult(ApiCode.INSERT_SUCCESS):new ApiResult(ApiCode.INSERT_ERROR);
    }

    @Override
    public ApiResult loginService(@NotNull(Config.ACCOUNT_NOT_NULL) String userAccount, @NotNull(Config.PASSWORD_NOT_NULL) String password) throws Exception {
        VerifyParam.verifyParam(userAccount,password);
        return verifyLogin(userAccount,password);
    }

    @Override
    public String getPasswordById(Integer userId) {
        String password = userMapper.getPasswordByUserIdSql(userId);
        if(StringUtils.isEmpty(password)){
            throw new BaseException(ApiCode.NOT_USER);
        }
        return password;
    }

    private ApiResult verifyLogin(String userAccount,String userPass){
        SysUser curUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getDelFlag,
                        SysUser::getUserId,
                        SysUser::getUserStatus,
                        SysUser::getPassword)
                .eq(SysUser::getAccount, userAccount));

        if(curUser == null || curUser.getDelFlag() || curUser.getUserStatus()  ){
            throw new BaseException(Config.ACCOUNT_OR_PASSWORD_FAIL);
        }

        return generateToken(userPass,curUser.getPassword(),curUser.getUserId());
    }


    private ApiResult generateToken(String password,String md5,Long userId){
        md5Verify(password,md5);
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("token",Token.createToken(userId,password));
        resultMap.put("user_id",userId);
        return new ApiResult(ApiCode.LOGIN_SUCCESS,resultMap);
    }

    private void md5Verify(String password,String md5){
        if(!MD5.verify(password,md5)){
            throw new BaseException(Config.ACCOUNT_OR_PASSWORD_FAIL);
        }
    }
}
