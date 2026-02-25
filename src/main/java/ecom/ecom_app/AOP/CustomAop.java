package ecom.ecom_app.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class CustomAop {
    private static final Logger logger= LoggerFactory.getLogger(CustomAop.class);
    @Around("@annotation(customAnnotation)")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint,
            CustomAnnotationAop customAnnotation) throws Throwable {

        long start = System.currentTimeMillis();

        logger.info("Started: {} - {}",
                joinPoint.getSignature().toShortString(),
                customAnnotation.value());

        Object result = joinPoint.proceed();   // execute method

        long executionTime = System.currentTimeMillis() - start;

        logger.info("Completed: {} in {} ms",
                joinPoint.getSignature().toShortString(),
                executionTime);

        return result;
    }
}
