package com.example.ufo_fi.domain.report.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedUsersReadRes {

    @Schema(description = "신고된 유저 아이디입니다.")
    private List<ReportedUserReadRes> reportedUsersReadRes;

    public static ReportedUsersReadRes from(List<User> reportedUsers) {
        return ReportedUsersReadRes.builder()
            .reportedUsersReadRes(reportedUsers.stream()
                .map(ReportedUserReadRes::from).toList())
            .build();
    }
}
