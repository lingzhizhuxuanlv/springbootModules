package com.lingzhizhuxuanlv.springbootModules.web.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
    String methodName() default "";
}
