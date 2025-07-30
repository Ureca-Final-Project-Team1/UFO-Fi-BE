package com.example.ufo_fi.domain.statistics.api;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.statistics.dto.response.StatisticsReportsRes;
import com.example.ufo_fi.domain.statistics.dto.response.StatisticsRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Statistics API", description = "사용자 통계 API")
public interface StatisticsApiSpec {

    @Operation(summary = "기본 통계 API", description = "기본 통계를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/statistics")
    ResponseEntity<ResponseBody<StatisticsRes>> readStatistics();

    @Operation(summary = "비활성화 통계 API", description = "비활성화 관련 통계를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/statistics/reports")
    ResponseEntity<ResponseBody<StatisticsReportsRes>> readStatisticsReports();
}
