package com.tjy.logging.aop;

import com.tjy.logging.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class SysLogAspect {

    @Pointcut("@annotation(com.tjy.logging.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        //类名
        String className = pjp.getTarget().getClass().getName();
        //方法名
        String methodName = signature.getName();
      
        SysLog syslog = method.getAnnotation(SysLog.class);
        //操作
        String operator =syslog.value();

        long beginTime = System.currentTimeMillis();

        Object returnValue = null;
        Exception ex = null;
        try {
            returnValue = pjp.proceed();
            return returnValue;
        } catch (Exception e) {
            ex = e;
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - beginTime;
            if (ex != null) {
                log.error("[class: {}][method: {}][operator: {}][cost: {}ms][args: {}][发生异常]",
                        className, methodName, operator, pjp.getArgs(), ex);
            } else {
                log.info("[class: {}][method: {}][operator: {}][cost: {}ms][args: {}][return: {}]",
                        className, methodName, operator, cost, pjp.getArgs(), returnValue);
            }
        }

    }
}
