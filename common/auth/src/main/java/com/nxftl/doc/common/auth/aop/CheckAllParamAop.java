package com.nxftl.doc.common.auth.aop;

import com.nxftl.doc.common.util.annotation.ValidAny;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.http.HttpStatus;
import com.nxftl.doc.common.util.util.BaseException;
import com.nxftl.doc.common.util.util.RegUtil;
import com.nxftl.doc.common.util.util.StringUtils;
import com.nxftl.doc.config.setting.Config;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 14:52
 * @discription
 *  这是一个非常好玩的AOP ,
 *    这个AOP主要作用是对方法进行拦截,对参数进行校验
 *     它可以根据 ValidAny 注解去根据校验规则,自动找寻校验方法
 */
@Aspect
@Component
public class CheckAllParamAop {



    /**
     * 拦截 切面表达式所有方法,判断参数是否为空或需要校验
     * @param joinPoint
     * @throws NoSuchMethodException 未找到方法异常
     * @throws IllegalAccessException 非法访问私有变量异常
     * @throws NoSuchFieldException 未找到字段异常
     */
    @Before("execution(public * com.nxftl.doc.*.*.controller..*(..))")
    public void checkAllParam(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        check(joinPoint);
    }

    /**
     *
     * @param joinPoint
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private void check(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        Object[] args = joinPoint.getArgs();
        if(args == null)
            return;
        checkMethodParameter(args,((MethodSignature)joinPoint.getSignature()).getMethod().getParameters());
    }


    /**
     *
     * @param args 参数值
     * @param parameters 参数名称
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private void checkMethodParameter(Object [] args,Parameter[] parameters) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        for (int i = 0 , let = parameters.length; i < let; i++) {
            Parameter parameter = parameters[i];

            if(checkOther(parameter))
                continue;

            if(isPrimitive(parameter.getType())){
                transferPrimitive(args,parameters,args[i],parameter);
                continue;
            }

            checkEntity(args,parameter);
        }
    }

    private void transferPrimitive(Object[] args, Parameter[] parameters, Object arg, Parameter parameter) throws NoSuchFieldException, IllegalAccessException {
        if(!parameter.getAnnotation(ValidAny.class).groupExist()){
            checkPrimitive(arg,parameter);
            return;
        }
        checkGroupPrimitive(args,parameters);
    }

    private void checkGroupPrimitive(Object[] args, Parameter[] parameters) throws NoSuchFieldException, IllegalAccessException {
        ValidAny annotation = null;
        for (int i = 0,let = parameters.length; i < let; i++) {
             annotation = parameters[i].getAnnotation(ValidAny.class);
            if(annotation != null && annotation.groupExist() && StringUtils.isNotEmpty(args[i])){
                checkPrimitive(args[i],parameters[i]);
            }
        }
        throw new BaseException(HttpStatus.ACCEPTED,annotation.existError());
    }


    private void checkEntity(Object[] args, Parameter parameter) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        Class<?> paramClass = parameter.getType();
        Object obj = Arrays.stream(args).filter(arg -> paramClass.isAssignableFrom(arg.getClass())).findFirst().get();
        handleFields(obj,paramClass.getDeclaredFields());
    }

    private void handleFields(Object obj, Field[] declaredFields) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        for (Field declaredField : declaredFields) {
            ValidAny annotation = declaredField.getAnnotation(ValidAny.class);
            if(annotation!=null){
                transfer(annotation,obj,declaredField,declaredFields);
            }
        }
    }

    private void transfer(ValidAny annotation, Object obj, Field declaredField,Field[] declaredFields) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        if(!annotation.groupExist()){
            entityExist(annotation,obj,declaredField);
        }
        handleGroupExist(obj,declaredFields);
    }

    private void handleGroupExist(Object obj,Field[] declaredFields) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        List<Field> fields = Arrays.stream(declaredFields).filter(field -> field.getAnnotation(ValidAny.class)!=null && field.getAnnotation(ValidAny.class).groupExist()).collect(Collectors.toList());
        checkGroupFieldsIsNull(obj,fields);
    }

    private void checkGroupFieldsIsNull(Object obj,List<Field> fields) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        try {
            for (Field field : fields) {
                Object invoke = obj.getClass().getMethod(Config.GET + getMethodName(field.getName())).invoke(obj);
                if(StringUtils.isNotEmpty(invoke)){
                    ValidAny annotation1 = field.getAnnotation(ValidAny.class);
                    isWho(annotation1,invoke);
                    return;
                }
            }
            throw new BaseException(ApiCode.PARAM_IS_NULL);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private void entityExist(ValidAny annotation,Object obj, Field declaredField)  {
        try {
            Object invoke = null;
            if(annotation.exist()){
                invoke = obj.getClass().getMethod(Config.GET+getMethodName(declaredField.getName())).invoke(obj);
                if(StringUtils.isEmpty(invoke)){
                    throw new BaseException(HttpStatus.ACCEPTED,annotation.value());
                }
            }else{
                invoke = obj.getClass().getMethod(Config.GET+getMethodName(declaredField.getName())).invoke(obj);
            }
            if(StringUtils.isEmpty(invoke)){
                return;
            }
            isWho(annotation,invoke);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private static String getMethodName(String filedName){
        String first = filedName.substring(0, 1);
        return filedName.replaceFirst(first,first.toUpperCase());
    }
    /**
     * Request,Session,Response,未标注注解一律放行
     * @param parameter
     * @return
     */
    private boolean checkOther(Parameter parameter) {
        if (parameter.getType().isAssignableFrom(HttpServletRequest.class)
                || parameter.getType().isAssignableFrom(HttpSession.class)
                || parameter.getType().isAssignableFrom(HttpServletResponse.class)
                || parameter.getAnnotation(ValidAny.class) == null) {
            return true;
        }
        return false;
    }

    /**
     * 检查Java自带类型
     * 是否需要用过注解进行校验
     * @param arg
     * @param parameter
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private void checkPrimitive(Object arg,Parameter parameter) throws NoSuchFieldException, IllegalAccessException {
        ValidAny annotation = parameter.getAnnotation(ValidAny.class);
        if(annotation != null && annotation.exist() && StringUtils.isEmpty(arg)){
            throw new BaseException(HttpStatus.ACCEPTED,annotation.existError());
        }
        checkPrimitiveIsWho(annotation,arg);
    }

    /**
     * 查看是需要什么校验 如 email password tel 等
     * @param validAny
     * @param arg
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private void checkPrimitiveIsWho(ValidAny validAny,Object arg) throws IllegalAccessException, NoSuchFieldException {
        isWho(validAny,arg);
    }

    /**
     * 判断具体是哪个属性需要检验 具体实现
     * @param validAny
     * @param arg
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void isWho(ValidAny validAny,Object arg) throws NoSuchFieldException, IllegalAccessException {
        InvocationHandler h = getProxy(validAny);
        Field memberValues = h.getClass().getDeclaredField("memberValues");
        memberValues.setAccessible(true);
        String result = result(((LinkedHashMap) memberValues.get(h)));
        if(result == null) return;
        RegUtil.invokeFuzzyInvokeMethod(result,(String) arg);
    }

    /**
     * 通过遍历集合返回对应的注解属性名
     * 返回条件 (不是多个单词组成,且属性为Boolean类型,并且属性为true)
     * @param memberMap
     * @return
     */
    private String result(LinkedHashMap memberMap) {
        Iterator iterator = memberMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            if(!StringUtils.isNotHaveUpperCase((String)entry.getKey()))
                continue;
            if(entry.getValue() instanceof Boolean){
                if(((Boolean)entry.getValue()).booleanValue() && !excludeExist((String) entry.getKey())){
                    return (String)entry.getKey();
                }
            }
            continue;
        }
        return null;
    }

    private boolean excludeExist(String key) {
        if("exist".equals(key.toLowerCase().trim()))
            return true;
        return false;
    }


    /**
     * 获取注解代理对象
     * @param validAny
     * @return
     */
    private InvocationHandler getProxy(ValidAny validAny) {
        return Proxy.getInvocationHandler(validAny);
    }




    private boolean isNotObjectMethod(String name){
        for (Method method : Object.class.getMethods()) {
            if(method.getName().equals(name)){
                return false;
            }
        }
        return true;
    }


    /**
     * 检验是否是Java自带的对象类型
     * @param type
     * @return
     */
    private boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == String.class;
    }
}
