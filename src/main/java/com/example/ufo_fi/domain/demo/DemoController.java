package com.example.ufo_fi.domain.demo;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.response.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public ResponseEntity<ResponseBody<URes>> read(){
        return ResponseEntity.ok(ResponseBody.success(URes.from(1L, "정지호")));
    }

    @GetMapping("/demo1")
    public ResponseEntity<ResponseBody<URes>> read1(){
        throw new GlobalException(DemoErrorCode.DEMO_ERROR_CODE);
    }
}
