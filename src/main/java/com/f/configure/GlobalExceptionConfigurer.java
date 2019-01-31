package com.f.configure;

import com.f.vo.ResponseVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author rebysfu@gmail.com
 * @description： 全局异常处理
 * @create 2018-10-11 上午11:25
 **/
@RestControllerAdvice
@Component
@Log4j2
public class GlobalExceptionConfigurer {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVo handleHttpMessageNotReadableException(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message(message).build();
    }

    @ExceptionHandler(BindException.class)
    public ResponseVo handleBindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append(fieldError.getDefaultMessage());
        return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message(sb.toString()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVo handelMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getField()).append(fieldError.getDefaultMessage());
        return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message(sb.toString()).build();
    }


}