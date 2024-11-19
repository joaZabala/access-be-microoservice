package ar.edu.utn.frc.tup.lc.iv.advice;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Aspect for logging exceptions thrown by
 * methods in the service implementation package.
 */
@Aspect
@Component
public class LoggingAspect {
    /**
     * The LoggerFactory is a utility class
     * producing Loggers for various logging APIs.
     **/
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs an error message when an exception is thrown
     * by any method in the service implementation package.
     *
     * @param exception the exception that was thrown
     */
    @AfterThrowing(
            pointcut = "execution(* ar.edu.utn.frc.tup.lc.iv.services.imp.*.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowingServices(Exception exception) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LOGGER.error("ACCESSES | Services  [{}]: {}", timestamp, exception.getMessage(), exception);
    }

    /**
     * Logs an error message when an exception is thrown
     * by any method in the controller package.
     *
     * @param exception the exception that was thrown
     */
    @AfterThrowing(
            pointcut = "execution(* ar.edu.utn.frc.tup.lc.iv.controllers.*.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowingInControllers(Exception exception) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LOGGER.error("ACCESSES | Controllers [{}]: {}", timestamp, exception.getMessage(), exception);
    }

    /**
     * Logs an error message when an exception is thrown
     * by any method in the application.
     *
     * @param exception the exception that was thrown
     */
    @AfterThrowing(
            pointcut = "execution(* ar.edu.utn.frc.tup.lc.iv..*(..)) "
                    + "&& !execution(* ar.edu.utn.frc.tup.lc.iv.services.imp.*.*(..))"
                    + "&& !execution(* ar.edu.utn.frc.tup.lc.iv.controllers.*.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(Exception exception) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LOGGER.error("ACCESSES [{}]: {}", timestamp, exception.getMessage(), exception);
    }
}
