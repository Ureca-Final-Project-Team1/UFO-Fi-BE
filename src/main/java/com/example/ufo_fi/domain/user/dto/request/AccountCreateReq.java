package com.example.ufo_fi.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCreateReq {

    @Schema(description = "은행(총 9개)")
    @NotBlank(message = "은행을 선택해주세요.")
    private String bank;

    @Schema(description = "숫자만으로 이뤄진 은행계좌(String타입이긴 함)")
    @NotBlank(message = "은행 계좌를 입력해주세요.")
    @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력 가능합니다.")
    private String bankAccount;

    @Schema(description = "6자리 비밀번호")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{6}$", message = "숫자만 6자리 입력 가능합니다.")
    private String password;
}
