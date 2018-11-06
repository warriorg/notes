package me.warriorg.spring.aop.example;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author: warrior
 * @date: 2018/11/6
 */
@Aspect
public class NotVeryUsefulAspect {

    @Pointcut("execution(* transfer(..))")
    private void anyOldTransfer() {}
}
