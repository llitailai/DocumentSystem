package com.nxftl.doc.doc.sys.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nxftl.doc.doc.common.annotation.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author darkltl
 * @since 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("后台用户")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 账号
     */
    @NotNull(value = "用户账号不许为空")
    @ApiModelProperty(value = "用户账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "用户密码")
    @NotNull(value = "用户密码不许为空")
    private String password;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记")
    private Boolean delFlag;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private Boolean userStatus;


}
