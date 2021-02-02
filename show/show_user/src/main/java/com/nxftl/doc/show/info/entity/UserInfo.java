package com.nxftl.doc.show.info.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户个人信息表
 * </p>
 *
 * @author darkltl
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_info")
@ApiModel(value="UserInfo对象", description="用户个人信息表")
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "info_id", type = IdType.AUTO)
    private Integer infoId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String headImage;

    @ApiModelProperty(value = "用户简介")
    private String userIntro;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
