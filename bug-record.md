# bug汇总

更新时间 2021-01-29 
<hr/>

    反射
    1. Method.invork(Object o,Object ... args) 
    该方法首参数o为实例对象,而非Class字节码对象
    异常出现场景 : 
     原方法 : VerifyParam.verifyParam(Class<?> cls) 
    该方法是由service实现层做参数校验而定义的校验方法,通过传入的字节码对象
    反射获取该类所有字段上标注的具有NotNull注解的字段,进行非空校验
    因调用Method.invork(cls)时 ,出现反射异常 this object not instance of object
    后将方法改为 :
        VerifyParam.verifyParam(Class <?> cls,Object o )
        Param cls : 需校验对象字节码对象
        Param o : 需校验对象实体

    2.  反射执行方法时,方法产出的任何异常都会被InvocationTargetException捕获

    模块分布
        原 common模块下没有子模块,common作为整体公共方法,包含校验不符合单一职责原则的特性
        固拆分为auth(验证模块) utils(工具类模块)
        将原sys模块下user模块启动类移除,添加start模块进行统一依赖,以便扫描所有模块下的方法
        原模块设计会产生如果不依赖如common模块.common模块无法扫描,校验方法失效,因common模块下
        校验需要user模块的支持(token验证),所以进行拆分模块,user模块过多依赖其他没有用的包裹不符合
        单一职责原则,所以新增启动模块来解决依赖循环,保证单一职责原则


    可变长参数
        异常情形 : 
```java

public class VerifyParam {


    /**
     * 校验是否具有 @NotNull 注解 如果有则判断参数是否为空,如果为空则抛出异常
     *
     * bug exception : method invoke 方法传入的第一个对象为 当前对象 而非当前字节码对象
     * @param verifyClass
     * @throws Exception
     */
    public static void verifyParam(Class<?> verifyClass, Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Field[] declaredFields = verifyClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            NotNull notNull = declaredField.getAnnotation(NotNull.class);
            //如果具有该注解,代表不允许该字段为空
            if (notNull != null) {
                Method method = verifyClass.getMethod(Config.GET + getMethodName(declaredField.getName()));
                Object invoke = method.invoke(obj);
                if (StringUtils.isEmpty(invoke)) {
                    throw new BaseException(HttpStatus.ACCEPTED, notNull.value());
                }
            }
        }
    }

    public static void verifyParam(Object... obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Object o : obj) {
            verifyString(o);
            //这一行出现错误 
            verifyParam(obj.getClass());
        }
    }

}
    
    
```
    看上述代码 出现了stackOverFlowError
    verifyParam(Class<?> verifyClass, Object obj) 该方法最初是没有Object obj
    因为疏忽出现错误代码没有更新,导致verifyParam调用的方法其实是可变长参数本身,引以为戒


    @Async 
        异步方法最好不要携带返回值
             @Async
             public ApiResult registerUserService(User user)
                该方法ApiResult返回值为空
                
    @Resource
        interface InfoMapper {}
        @Resource
        UserInfoMapper infoMapper;
        会出现注入错误
        @Resource 查询规则,先根据变量名找类名,变量名找不到才会去通过类名寻找


        
    IDEA 2020.3 bug
    1. 代码拉取不到最新版本,拉取下来可能会拉取一些不属于你的东西,发生原因未知,可能时git提交代码端idea版本为2019.3

    2. lombok失效 ， 解决方式 settings compiler选项 Vm options 添加-Djps.track.ap.dependencies=false