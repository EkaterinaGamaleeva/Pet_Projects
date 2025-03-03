package com.api.serviceYandexMail.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum ErrorsCodesAndMessage {
    FILE_IS_EMPTY(204, ExceptionConstants.FILE_IS_EMPTY),

    FILE_MAX_SIZE(413, ExceptionConstants.FILE_MAX_SIZE),

    FILE_LOADING_ERROR(503, ExceptionConstants.FILE_LOADING_ERROR),

    FILE_SEND_ERROR(2, ExceptionConstants.FILE_SEND_ERROR),

    USER_EMAIL_NOT_VALID(1, ExceptionConstants.USER_EMAIL_NOT_VALID);


    public static final Map<String, Integer> map = new HashMap<>();

    static {
        for (ErrorsCodesAndMessage c : ErrorsCodesAndMessage.values()) {
            map.put(c.message, c.statusCode);
        }
    }

    public static final Map<ErrorsCodesAndMessage, String> mapErrors;

    static {
        mapErrors = Arrays.stream(ErrorsCodesAndMessage.values())
                .collect(Collectors.toMap(
                        c -> c,
                        c -> String.format(AppConstants.STRING_FORMAT_ERROR, c, c.statusCode, c.message)
                ));
    }

    private final int statusCode;
    private final String message;

    ErrorsCodesAndMessage(int statusCode, String fileExceptionConstants) {
        this.statusCode = statusCode;
        this.message = fileExceptionConstants;
    }
}
