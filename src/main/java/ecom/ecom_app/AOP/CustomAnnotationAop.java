package ecom.ecom_app.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Aspect
@Component
@Target(ElementType.METHOD) //used on method .module for whole module
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomAnnotationAop {
    String value() default "";
}
