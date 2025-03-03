package com.example.serviceYandexCalendar.exception;

import lombok.Getter;
import com.example.serviceYandexCalendar.util.FileErrorCodesAndMessage;

@Getter
public class CustomException extends RuntimeException {

    private final FileErrorCodesAndMessage fileErrorCodesAndMessage;

    public CustomException(FileErrorCodesAndMessage fileErrorCodesAndMessage) {
        this.fileErrorCodesAndMessage=fileErrorCodesAndMessage;
    }
}
