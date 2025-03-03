package com.api.serviceYandexMail.exception;

import com.api.serviceYandexMail.util.ErrorsCodesAndMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorsCodesAndMessage errorsCodesAndMessage;

    public CustomException(ErrorsCodesAndMessage errorsCodesAndMessage) {
        this.errorsCodesAndMessage = errorsCodesAndMessage;
    }
}
