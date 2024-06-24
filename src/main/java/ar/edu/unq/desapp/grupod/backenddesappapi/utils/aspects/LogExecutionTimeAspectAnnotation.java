package ar.edu.unq.desapp.grupod.backenddesappapi.utils.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
@Slf4j
@Component
public class LogExecutionTimeAspectAnnotation {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()); // Enhanced timestamp format

        Object proceed;
        try {
            proceed = joinPoint.proceed(); // Execute the targeted method
        } catch (Throwable throwable) {
            log.error("Exception occurred during method execution:", throwable);
            throw throwable;
        }

        long executionTime = System.currentTimeMillis() - start;
        String user = getCurrentUser();
        String operation = joinPoint.getSignature().getName();
        String parameters = getMethodParameters(joinPoint.getArgs());

        String logMessage = String.format(
                "\u001B[33m[%s] [%s] [%s] [%s] - Executed in %dms\u001B[0m",
                timestamp, user, operation, parameters, executionTime);
        log.info(logMessage);

        return proceed;
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "anonymous";
    }

    private String getMethodParameters(Object[] args) {
        return args != null ? java.util.Arrays.toString(args) : "";
    }
}
