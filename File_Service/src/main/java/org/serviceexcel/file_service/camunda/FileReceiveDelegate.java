package org.serviceexcel.file_service.camunda;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.serviceexcel.file_service.exception.CustomException;
import org.serviceexcel.file_service.util.FileErrorCodesAndMessage;
import org.springframework.stereotype.Component;

@Component
public class FileReceiveDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        byte[] file = (byte[]) execution.getVariable("file");
        if (file == null || file.length == 0) {
            throw new CustomException(FileErrorCodesAndMessage.FILE_IS_EMPTY);
        }
        execution.setVariable("file", file);
    }
}

