package com.nxftl.doc.show.user.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.nxftl.doc.common.util.annotation.ValidAny;
import com.nxftl.doc.config.setting.Config;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "用户电话号码")
    @ValidAny(value = Config.TEL_NOT_QUALIFIED,tel = true)
    private String tel;

    @ApiModelProperty(value = "邮箱")
    @ValidAny(value = Config.EMAIL_NOT_QUALIFIED,email = true)
    private String email;

    @ApiModelProperty(value = "密码")
    @ValidAny(value = Config.PASSWORD_NOT_QUALIFIED,password = true)
    private String password;

}
