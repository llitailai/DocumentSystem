package com.nxftl.doc.common.auth.aop;

import com.nxftl.doc.common.util.annotation.ValidAny;
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

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 14:52
 * @discription
 */
@Aspect
@Component
public class CheckAllParamAop {


    /**
     * 拦截 切面表达式所有方法,判断参数是否为空或需要校验
     * @param joinPoint
     * @throws NoSuchMethodException 未找到方法异常
     * @throws IllegalAccessException 非法访问私有变量异常
     * @throws InvocationTargetException 原来的异常包装起来所抛出的异常
     * @throws NoSuchFieldException 未找到字段异常
     */
    @Before("execution(public * com.nxftl.doc.*.*.controller..*(..))")
    public void checkAllParam(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        check(joinPoint);
    }

    /**
     *
     * @param joinPoint
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    private void check(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
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
     * @throws InvocationTargetException
     */
    private void checkMethodParameter(Object [] args,Parameter[] parameters) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        for (int i = 0 , let = parameters.length; i < let; i++) {
            Parameter parameter = parameters[i];

            if(checkOther(parameter))
                continue;

            if(isPrimitive(parameter.getType())){
                checkPrimitive(args[i],parameter);
                continue;
            }

            checkEntity(args,parameter);
        }
    }


    private void checkEntity(Object[] args, Parameter parameter) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Class<?> paramClass = parameter.getType();
        Object obj = Arrays.stream(args).filter(arg -> paramClass.isAssignableFrom(arg.getClass())).findFirst().get();
        handleFields(obj,paramClass.getDeclaredFields());
    }

    private void handleFields(Object obj, Field[] declaredFields) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        for (Field declaredField : declaredFields) {
            ValidAny annotation = declaredField.getAnnotation(ValidAny.class);
            if(annotation!=null){
                entityExit(annotation,obj,declaredField);
            }
        }
    }


    private void entityExit(ValidAny annotation,Object obj, Field declaredField) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Object invoke = null;
        if(annotation.exit()){
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
    private void checkPrimitive(Object arg,Parameter parameter) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        ValidAny annotation = parameter.getAnnotation(ValidAny.class);
        if(annotation != null && annotation.exit() && StringUtils.isEmpty(arg)){
            throw new BaseException(HttpStatus.ACCEPTED,annotation.exitError());
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
    private void checkPrimitiveIsWho(ValidAny validAny,Object arg) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        isWho(validAny,arg);
    }

    /**
     * 判断具体是哪个属性需要检验 具体实现
     * @param validAny
     * @param arg
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void isWho(ValidAny validAny,Object arg) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        InvocationHandler h = getProxy(validAny);
        Field memberValues = h.getClass().getDeclaredField("memberValues");
        memberValues.setAccessible(true);
        RegUtil.invokeFuzzyInvokeMethod(result(((LinkedHashMap)memberValues.get(h))),(String) arg);
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
                if(((Boolean)entry.getValue()).booleanValue()){
                    return (String)entry.getKey();
                }
            }
            continue;
        }
        return null;
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
