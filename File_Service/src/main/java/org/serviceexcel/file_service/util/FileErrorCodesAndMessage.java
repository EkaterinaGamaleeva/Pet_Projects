package org.serviceexcel.file_service.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum FileErrorCodesAndMessage {
    FILE_IS_EMPTY(204, FileExceptionConstants.FILE_IS_EMPTY),

    FILE_MAX_SIZE(413, FileExceptionConstants.FILE_MAX_SIZE),

    FILE_LOADING_ERROR(503, FileExceptionConstants.FILE_LOADING_ERROR),

    EMAIL_NOT_VALID(1,FileExceptionConstants.EMAIL_NOT_VALID);

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
                        c -> String.format(AppConstants.STRING_FORMAT_ERROR, c, c.statusCode, c.message)
                ));
    }

    private final int statusCode;
    private final String message;

    FileErrorCodesAndMessage(int statusCode, String fileExceptionConstants) {
        this.statusCode = statusCode;
        this.message = fileExceptionConstants;
    }
}
