package com.example.ufo_fi.global.log;

public class LogTraceContext {
    private static final ThreadLocal<LogTrace> LOG_TRACE_THREAD_LOCAL = new ThreadLocal<>();

    //LogTrace 가져오기
    public static LogTrace get() {
        return LOG_TRACE_THREAD_LOCAL.get();
    }

    // 제거(메모리 효율성을 위해 반드시!!!!!!!! 호출해줍니다.)
    public static void clear() {
        LOG_TRACE_THREAD_LOCAL.remove();
    }
}
