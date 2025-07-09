package com.example.ufo_fi.global.security.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 예외를 직접 HttpServletResponse에 세팅하는 객체이다.
 */
@Component
public class SecurityResponseSetter {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String STATUS_CODE = "statusCode";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";
    private static final String IDENTITY = "security ";

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Security 단에서는 ResponseEntity를 사용할 수 없기 때문에, response에 담아 전달해야한다.
     * 이 메서드는 security에서 나올 수 있는 예외를 response에 담아 전달하는 놈이다.
     */
    public void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(CONTENT_TYPE);                  //헤더 세팅
        response.setStatus(errorCode.getStatus().value());

        Map<String, Object> body = new HashMap<>();
        body.put(STATUS_CODE, errorCode.getStatus().value());  //바디 세팅
        body.put(MESSAGE, IDENTITY + errorCode.getMessage());

        objectMapper.writeValue(response.getWriter(), body);    //response에 body를 직렬화
    }

    /**
     * Security 단에서는 ResponseEntity를 사용할 수 없기 때문에, response에 담아 전달해야한다.
     * 이 메서드는 security에서 응답 바디를 세팅해주기 위한 놈이다.
     */
    public <T> void setResponse(HttpServletResponse response, T data) throws IOException {
        response.setContentType(CONTENT_TYPE);                  //헤더 세팅
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> body = new HashMap<>();             //바디 세팅
        body.put(STATUS_CODE, HttpStatus.OK.value());
        body.put(MESSAGE, "나이스~나이스~");
        body.put(DATA, data);

        objectMapper.writeValue(response.getWriter(), body);    //response에 body를 직렬화
    }
}
