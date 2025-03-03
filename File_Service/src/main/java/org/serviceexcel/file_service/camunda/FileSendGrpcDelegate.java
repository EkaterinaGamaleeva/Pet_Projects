package org.serviceexcel.file_service.camunda;


import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.serviceexcel.file_service.service.grpc.EmailServiceGrpcClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileSendGrpcDelegate implements JavaDelegate {

    private final EmailServiceGrpcClient emailServiceGrpcClient;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        byte[] file = (byte[]) execution.getVariable("file");
        String email = (String) execution.getVariable("email");
        String message = (String) execution.getVariable("text");
        String fileName = (String) execution.getVariable("fileName");
        emailServiceGrpcClient.sendEmailWithAttachment(email, message, file, fileName);
    }
}