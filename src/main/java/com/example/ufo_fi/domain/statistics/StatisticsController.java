package com.example.ufo_fi.domain.statistics;

import com.example.ufo_fi.domain.statistics.api.StatisticsApiSpec;
import com.example.ufo_fi.domain.statistics.dto.response.StatisticsReportsRes;
import com.example.ufo_fi.domain.statistics.dto.response.StatisticsRes;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticsController implements StatisticsApiSpec {
    private final StatisticsService statisticsService;

    @Override
    public ResponseEntity<ResponseBody<StatisticsRes>> readStatistics() {
        return ResponseEntity.ok(
            ResponseBody.success(
                statisticsService.readStatistics()));
    }

    @Override
    public ResponseEntity<ResponseBody<StatisticsReportsRes>> readStatisticsReports() {
        return ResponseEntity.ok(
            ResponseBody.success(
                statisticsService.readReportsStatistics()));
    }
}
