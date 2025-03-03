package com.example.serviceYandexCalendar.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum FileErrorCodesAndMessage {
    FILE_IS_EMPTY(204, FileExceptionConstants.FILE_IS_EMPTY),

    ERROR_LINKS_UPLOAD_FILE(409, FileExceptionConstants.ERROR_LINKS_UPLOAD_FILE),

    FILE_MAX_SIZE(413, FileExceptionConstants.FILE_MAX_SIZE),

    FILE_LOADING_ERROR(503, FileExceptionConstants.FILE_LOADING_ERROR),

    FILE_NAME_ALREADY_EXIST(3,FileExceptionConstants.FILE_NAME_ALREADY_EXIST),

    FILE_LOADING_ERROR_BY_DISK(4,FileExceptionConstants.FILE_LOADING_ERROR_BY_DISK),

    SERVER_ERROR(500,FileExceptionConstants.SERVER_ERROR),

    FOLDER_NAME_NOT_MATCHER_PATTERN(5,FileExceptionConstants.FOLDER_NAME_NOT_MATCHER_PATTERN),

    FAILED_TO_CREATE_AN_MEETING(6,FileExceptionConstants.FAILED_TO_CREATE_AN_MEETING);

    public static final Map<String, Integer> map = new HashMap<>();

    static {
        for (FileErrorCodesAndMessage c : FileErrorCodesAndMessage.values()) {
            map.put(c.message, c.statusCode);
        }
    }

    public static final Map<FileErrorCodesAndMessage, String> mapErrors;

    static {
        mapErrors = Arrays.stream(FileErrorCodesAndMessage.values())
                .collect(Collectors.toMap(
                        c -> c,
                        c -> String.format(AppConstant.STRING_FORMAT_ERROR, c, c.statusCode, c.message)
                ));
    }

    private final int statusCode;
    private final String message;

    FileErrorCodesAndMessage(int statusCode, String fileExceptionConstants) {
        this.statusCode = statusCode;
        this.message = fileExceptionConstants;
    }
}
