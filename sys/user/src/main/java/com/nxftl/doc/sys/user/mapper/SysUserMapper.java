package com.nxftl.doc.sys.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nxftl.doc.sys.user.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    @Select("SELECT password FROM sys_user WHERE user_id = #{userId} limit 1")
    String getPasswordByUserIdSql(@Param("userId") Long userId);
}


