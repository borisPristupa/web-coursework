package com.ifmo.web.coursework.log;

import com.ifmo.web.coursework.data.utils.HumanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {
    private final HumanUtils humanUtils;

    @Pointcut("@annotation(Log)")
    public void byAnnotation() {
    }

    @Pointcut("@within(Log)")
    public void byAnnotatedClass() {
    }

    @Pointcut("@annotation(Log.Exclude)")
    public void byExclude() {
    }

    @Around("!byExclude() && (byAnnotation() || byAnnotatedClass())")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        Logger logger = LoggerFactory.getLogger(point.getTarget().getClass());
        MethodSignature signature = (MethodSignature) point.getSignature();

        String methodSignature = String.format("%s %s(%s)",
                signature.getReturnType().getName(),
                signature.getName(),
                Arrays.stream(signature.getParameterTypes())
                        .map(Class::getName)
                        .collect(Collectors.joining(", "))
        );

        logger.info("User #" + humanUtils.getCurrentId() + " called method " + methodSignature);

        Object returnValue;

        try {
            returnValue = point.proceed();
        } catch (Throwable t) {
            logger.error("Exception occurred for user #" + humanUtils.getCurrentId() +
                    " at method " + methodSignature, t);
            throw t;
        }

        logger.info("User #" + humanUtils.getCurrentId() + " returned from method " + methodSignature);

        return returnValue;
    }

    @Autowired
    public LoggingAspect(HumanUtils humanUtils) {
        this.humanUtils = humanUtils;
    }
}
