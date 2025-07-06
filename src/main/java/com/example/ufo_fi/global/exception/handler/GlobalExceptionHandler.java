package com.example.ufo_fi.global.exception.handler;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.response.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ResponseBody<Void>> handleException(GlobalException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ResponseBody.error(e.getErrorCode()));
    }
}
