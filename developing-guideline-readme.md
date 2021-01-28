#开发规范

##包名
    1. 域名反写.当前项目名称.模块名称.**
    
    
##类名
    1. 类名首字母大写,且见名知意
    2. Service,Controller,config,filter,handler 等模块下的类应以上述模块结尾
        如 : UserService ,UserController ,UserConfig ,UserFilter ,UserHandler
        
## 方法名
    1. 方法名首字母小写,
        # service层
               loginService(), registerService()
               
        # mapper层(自定义SQL)
            selectUserAccountByIdSql()
            
    
**更新时间 : 2021-01-28 08:42**