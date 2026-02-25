package ecom.ecom_app.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(LogAspect.class);

    //before the method
    @Before("execution(* ecom.ecom_app.Service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Method Started: {} | Arguments: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    //after successfully execution of the method
    @AfterReturning(
            pointcut = "execution(* ecom.ecom_app.Service.*.*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method Completed: {} | Returned: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    // after part
    @AfterThrowing(
            pointcut = "execution(* ecom.ecom_app.Service.*.*(..))",
            throwing = "exception"
    )
    public void logAfterException(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in: {} | Message: {}",
                joinPoint.getSignature().toShortString(),
                exception.getMessage());
    }
}