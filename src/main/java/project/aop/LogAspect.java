package project.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import project.service.LogService;

@Aspect
@Component
public class LogAspect {

    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* project.web.ItemController.details(..))")
    public void track(){ };

    @Pointcut("execution(* project.web.ItemController.detailsFrontPage(..))")
    public void track2(){ };

    @After("track()")
    public void afterAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long itemId=(Long) args[0];
        String action=joinPoint.getSignature().getName();
        this.logService.createLog(action,itemId);
    };

    @After("track2()")
    public void afterAdviceFor2ndDetails(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        Long itemId=(Long) args[0];
        String action=joinPoint.getSignature().getName();

        this.logService.createLog(action,itemId);
    };



}
