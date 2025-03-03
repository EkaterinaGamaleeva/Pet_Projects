package org.serviceexcel.file_service.exception;


import lombok.Getter;
import org.serviceexcel.file_service.util.FileErrorCodesAndMessage;

@Getter
public class CustomException extends RuntimeException {

    private final FileErrorCodesAndMessage fileErrorCodesAndMessage;

    public CustomException(FileErrorCodesAndMessage fileErrorCodesAndMessage) {
        this.fileErrorCodesAndMessage=fileErrorCodesAndMessage;
    }
}
