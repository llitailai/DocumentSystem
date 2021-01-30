create time 2021-01-20
insert this data time is 2021-01-19


create database sys_trace;

use sys_trace;

```sql
create table sys_user(
                        user_id bigint primary key auto_increment comment '用户ID',
                        account varchar(25) not null comment '账号',
                        password varchar(255) not null comment '密码',
                        del_flag tinyint(1) default 0 comment '删除标记',
                        user_status tinyint(1) default 0 comment '用户状态',
                        create_time date not null comment '创建时间',
                        update_time date not null comment '修改时间'
                     )comment '后台用户表';


create table sys_user_info (
  info_id bigint primary key auto_increment comment '用户信息ID',
  username varchar(15) default '' comment '用户姓名',
  email varchar(64) default '' comment '用户邮箱',
  tel varchar(25) default '' comment '用户电话',
  head_image varchar(655) default '' comment '头像地址',
  nick_name varchar(35) default '' comment '昵称',
  signature varchar(100) default '' comment '个性签名',
  del_flag tinyint(1) default 0 comment '删除标记',
  user_id bigint comment '用户ID',
  create_time date not null comment '创建时间',
  update_time date not null comment '修改时间'
)comment '后台用户信息表';

create table sys_sign_in(
  sign_id bigint primary key auto_increment comment '用户签到信息ID',
  sign_time date not null comment '签到时间',
  sign_status varchar(55) not null comment '签到状态',
  is_retroactive tinyint(1) default 0 comment '是否是补签记录',
  del_flag tinyint(1) default 0 comment '删除标记',
  create_time date not null comment '创建时间',
  update_time date not null comment '修改时间'
)comment '用户签到表';

create table sys_doc(
  doc_id bigint primary key auto_increment
  comment '文档表ID',
  doc_url varchar(255) not null comment '文档路径',
  dict_id bigint not null comment '字典ID',
  doc_type varchar(15) not null comment '文档类型',
  del_flag tinyint(1) default 0 comment '删除标记',
  create_time date not null comment '创建时间',
  update_time date not null comment '修改时间'
)comment '文档表';

create table sys_dict(
  dict_id bigint primary key auto_increment,
  dict_name varchar(25) not null comment '字典项名称',
  dict_code varchar(15) not null comment '字典项英文值',
  p_code varchar(15) not null comment '字典项父code',
  del_flag tinyint(1) default 0 comment '删除标记',
  create_time date not null comment '创建时间',
  update_time date not null comment '修改时间'
)comment '字典表';



-- update : 2021-01-28 20:30 
create table sys_role(
    role_id smallint primary key auto_increment,
    role_name varchar(15) not null comment '角色名称',
    role_remark varchar(35) not null comment '角色描述',
    del_flag tinyint(1) default 0 comment '删除标记',
    create_time date not null comment '创建时间',
    update_time date not null comment '修改时间'
)comment '角色表';


create table sys_permission(
    perm_id smallint primary key auto_increment,
    role_id smallint not null comment '角色ID',
    dict_code varchar(15) not null comment '权限名称',
    del_flag tinyint(1) default 0 comment '删除标记',
    create_time date not null comment '创建时间',
    update_time date not null comment '修改时间'
)comment '权限表';

create table sys_role_user(
    ru_id smallint primary key auto_increment,
    role_id smallint not null comment '角色ID',
    user_id bigint not null comment '用户ID',
    del_flag tinyint(1) default 0 comment '删除标记',
    create_time date not null comment '创建时间',
    update_time date not null comment '修改时间'
)comment '用户角色权限表';



```


## 权限详解
    超级管理员
    管理员
    普通用户

    普通用户 查看,