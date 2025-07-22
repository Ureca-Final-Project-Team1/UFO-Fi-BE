package com.example.ufo_fi.domain.report.controller;

import com.example.ufo_fi.domain.report.controller.api.ReportUserApiSpec;
import com.example.ufo_fi.domain.report.service.ReportService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportUserController implements ReportUserApiSpec {
    private final ReportService reportService;


    @Override
    public ResponseEntity<ResponseBody<Void>> registerRollBackReported(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return null;
    }
}
