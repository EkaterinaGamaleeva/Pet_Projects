package org.serviceexcel.file_service.camunda;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.serviceexcel.file_service.response.request.FileRabbitRequest;
import org.serviceexcel.file_service.util.AppConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileSendRabbitDelegate implements JavaDelegate {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        byte[] file = (byte[]) execution.getVariable("file");
        String fileName = (String) execution.getVariable("fileName");
        FileRabbitRequest fileMessage = new FileRabbitRequest(fileName, file);
        rabbitTemplate.convertAndSend(AppConstants.EXCHANGE_NAME, AppConstants.ROUTING_KEY, fileMessage);
    }
}