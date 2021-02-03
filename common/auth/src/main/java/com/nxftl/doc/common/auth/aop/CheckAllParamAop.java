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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/3 14:52
 * @discription
 */
@Aspect
@Component
public class CheckAllParamAop {


    @Before("execution(public * com.nxftl.doc.*.*.controller..*(..))")
    public void checkAllParam(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        check(joinPoint);
    }

    private void check(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Object[] args = joinPoint.getArgs();
        if(args == null)
            return;
        checkMethodParameter(args,((MethodSignature)joinPoint.getSignature()).getMethod().getParameters());
    }


    private void checkMethodParameter(Object [] args,Parameter[] parameters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
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


    private void checkEntity(Object[] args, Parameter parameter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class<?> paramClass = parameter.getType();
        Object obj = Arrays.stream(args).filter(arg -> paramClass.isAssignableFrom(arg.getClass())).findFirst().get();
        handleFields(obj,paramClass.getDeclaredFields());
    }

    private void handleFields(Object obj, Field[] declaredFields) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        for (Field declaredField : declaredFields) {
            ValidAny annotation = declaredField.getAnnotation(ValidAny.class);
            if(annotation!=null){
                entityExit(annotation,obj,declaredField);
            }
        }
    }


    private void entityExit(ValidAny annotation,Object obj, Field declaredField) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
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

    private void checkPrimitive(Object arg,Parameter parameter) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        ValidAny annotation = parameter.getAnnotation(ValidAny.class);
        if(annotation != null && annotation.exit() && StringUtils.isEmpty(arg)){
            throw new BaseException(HttpStatus.ACCEPTED,annotation.exitError());
        }
        checkPrimitiveIsWho(annotation,arg);
    }

    private void checkPrimitiveIsWho(ValidAny validAny,Object arg) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        isWho(validAny,arg);
    }

    private void isWho(ValidAny validAny,Object arg) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        InvocationHandler h = getProxy(validAny);
        Field memberValues = h.getClass().getDeclaredField("memberValues");
        LinkedHashMap memberMap = (LinkedHashMap)memberValues.get(h);

//
//            if(!isNotHaveUpperCase(field.getName()) && !isNotObjectMethod(field.getName()))
//            //执行注解内部方法如果返回值为true,则获取方法名,去工具类根据方法名执行对应工具方法判断是否正确
//            if(field.getBoolean(validAny)){
//                RegUtil.invokeFuzzyInvokeMethod(field.getName(),arg.toString());
//            }
    }

    private InvocationHandler getProxy(ValidAny validAny) {
        return Proxy.getInvocationHandler(validAny);
    }

    private boolean isNotHaveUpperCase(String name) {
        String newName = name.toLowerCase();
        byte[] bytes = name.getBytes();
        byte[] newNameBytes = newName.getBytes();
        for(int i=0,let=bytes.length;i<let;i++){
            if((int)bytes[i] != (int)newNameBytes[i])
                return false;
        }
        return true;
    }

    private boolean isNotObjectMethod(String name){
        for (Method method : Object.class.getMethods()) {
            if(method.getName().equals(name)){
                return false;
            }
        }
        return true;
    }




    private boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == String.class;
    }
}
