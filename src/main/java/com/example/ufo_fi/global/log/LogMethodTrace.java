package com.example.ufo_fi.global.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 *  1) main [UserService.loadUser]-2025:07:24:10:59:.....
 *     → args: user123
 *  2) main [PaymentService.confirm]-2025:07:24:11:00:.....
 *     → args: 1000, true
 *
 *  이런 형식으로 나올 듯 싶슴다.
 */
public class LogMethodTrace {
    private static final DateTimeFormatter TIME_TEMPLATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String METHOD_TEMPLATE =
    """
    %2d) %s [%s]-%s
    => args(
    %s
    )
    """;

    private final StringBuilder methodSequence = new StringBuilder();
    private int methodIndex = 1;

    //메서드 호출 시 호출한다.
    public void appendMethod(String methodName, Object... args){
        methodSequence.append(
                METHOD_TEMPLATE.formatted(
                        methodIndex++,
                        Thread.currentThread().getName(),
                        methodName,
                        LocalDateTime.now().format(TIME_TEMPLATE),
                        formatArgs(args)
                )
        ).append("\n");
    }

    //모인 로그를 가져온다.
    public String getTracedMethods(){
        return methodSequence.toString();
    }

    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) return "(없음)";
        return Arrays.stream(args)
                .map(arg -> arg == null ? "null" : arg.toString())
                .reduce((a, b) -> a + ",\n        " + b)
                .orElse("(없음)");
    }
}
