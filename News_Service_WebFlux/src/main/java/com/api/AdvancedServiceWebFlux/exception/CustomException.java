package com.api.AdvancedServiceWebFlux.exception;

import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ServerErrorCodes serverErrorCodes;

    private final String message;

    public CustomException(ServerErrorCodes serverErrorCodes) {
        this.serverErrorCodes = serverErrorCodes;
        message = serverErrorCodes.getMessage();
    }
}
