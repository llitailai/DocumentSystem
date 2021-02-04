package com.nxftl.doc.sys.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nxftl.doc.common.util.annotation.NotNull;
import com.nxftl.doc.common.util.annotation.ValidAny;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@Accessors(chain = true)
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
    @ValidAny(exist = true,existError = "用户账号不许为空")
    @ApiModelProperty(value = "用户账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "用户密码")
    @ValidAny(password = true,exist = true,existError = "用户密码不许为空")
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

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
