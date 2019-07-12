package com.lingzhizhuxuanlv.springbootModules.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.lingzhizhuxuanlv.springbootModules.web..*.*(..))")
    private void webAspect(){}

    //运行前
    @Before("webAspect()")
    public void before(JoinPoint jp) {
        System.out.println("******************************");
        // 接收请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 拦截的实体类名
        String entityName = jp.getSignature().getDeclaringTypeName();
        // 拦截的方法名称
        String methodName = jp.getSignature().getName();
        // 自定义的注解内容
        if (((MethodSignature)jp.getSignature()).getMethod().getAnnotation(WebLog.class) != null) {
            WebLog webLog = ((MethodSignature)jp.getSignature()).getMethod().getAnnotation(WebLog.class);
            System.out.println("WILL_RUN : " + webLog.methodName());
        }
        // 可操作的内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("REQUEST_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_ENTITY : " + entityName);
        System.out.println("CLASS_METHOD : " + methodName);
    }

    //运行后
    @After("webAspect()")
    public void after(JoinPoint jp){
        if (((MethodSignature)jp.getSignature()).getMethod().getAnnotation(WebLog.class) != null) {
            WebLog webLog = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(WebLog.class);
            System.out.println("RUN_END : " + webLog.methodName());
        }
        System.out.println("******************************");
    }

}
