package com.nxftl.doc.show.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.show.info.entity.UserInfo;

/**
 * <p>
 * 用户个人信息表 服务类
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
public interface IUserInfoService extends IService<UserInfo> {

    void addUserInfoAsyncService(UserInfo userInfo);
}
