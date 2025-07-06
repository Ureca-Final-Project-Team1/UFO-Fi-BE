package com.example.ufo_fi.global.response;

import com.example.ufo_fi.global.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseBody<T> {
    private final HttpStatus statusCode;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    //예외가 없을 시 사용되는 생성자
    public ResponseBody(T data) {
        this.statusCode = HttpStatus.OK;
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    //에러 코드를 반환하는 생성자
    public ResponseBody(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    //성공 시 컨트롤러에서 DTO(data)를 ResponseEntity<ResponseBody>로 변환해 반환한다.
    public static <T> ResponseBody<T> success(T data) {
        return new ResponseBody<>(data);
    }

    //에러 코드를 GlobalExceptionHandler에서 받아 ResponseEntity<ResponseBody>로 변환해 반환한다.
    public static <T> ResponseBody<T> error(ErrorCode errorCode) {
        return new ResponseBody<>(errorCode);
    }
}
