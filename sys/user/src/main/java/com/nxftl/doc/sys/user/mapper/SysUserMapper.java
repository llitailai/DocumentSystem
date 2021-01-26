package com.nxftl.doc.sys.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nxftl.doc.sys.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author darkltl
 * @since 2021-01-20
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    // ------------------------ SQL --------------------------------------------

    /**
     * 只查询PASSWORD
     * @param userId
     * @return
     */
    @Select("SELECT PASSWORD FROM sys_user WHERE user_id = ${userId} limit 1")
    String getPasswordByUserIdSql(@Param("userId") Integer userId);
}
