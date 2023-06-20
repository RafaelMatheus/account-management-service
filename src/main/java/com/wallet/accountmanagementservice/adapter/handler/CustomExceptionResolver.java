package com.wallet.accountmanagementservice.adapter.handler;

import com.wallet.accountmanagementservice.adapter.handler.dto.CustomErrorResponse;
import com.wallet.accountmanagementservice.adapter.handler.dto.FieldErrorResponse;
import com.wallet.accountmanagementservice.core.exception.AccountNotFoundException;
import com.wallet.accountmanagementservice.core.exception.InsufficientBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionResolver extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var bindingResult = ex.getBindingResult();
        var fieldErrors = bindingResult.getFieldErrors();
        var path = ((ServletWebRequest) request).getRequest().getServletPath();

        var fieldErrorResponse = fieldErrors.stream()
                .map(fieldError -> new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        var errorResponse = new CustomErrorResponse(path, "Validation error", HttpStatus.BAD_REQUEST, fieldErrorResponse);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("m=handleExceptionInternal", ex);

        var path = ((ServletWebRequest) request).getRequest().getServletPath();

        return ResponseEntity.internalServerError().body(new CustomErrorResponse(path, ex.getMessage(), status, null));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> accountNotFoundException(AccountNotFoundException ex, ServletWebRequest request) {
        log.error("m=accountNotFoundException", ex);
        var path = request.getRequest().getServletPath();

        return ResponseEntity.badRequest().body(new CustomErrorResponse(path, ex.getMessage(), HttpStatus.BAD_REQUEST, null));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    protected ResponseEntity<CustomErrorResponse> insufficientBalanceException(InsufficientBalanceException ex, ServletWebRequest request) {
        log.error("m=insufficientBalanceException", ex);

        var path = request.getRequest().getServletPath();

        return ResponseEntity.badRequest().body(new CustomErrorResponse(path, ex.getMessage(), HttpStatus.BAD_REQUEST, null));
    }
}

