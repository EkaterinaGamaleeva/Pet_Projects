package org.serviceexcel.file_service.service.Impl;


import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.serviceexcel.file_service.exception.CustomException;
import org.serviceexcel.file_service.response.BaseSuccessResponse;
import org.serviceexcel.file_service.response.request.FileRabbitRequest;
import org.serviceexcel.file_service.service.FileService;
import org.serviceexcel.file_service.service.grpc.EmailServiceGrpcClient;
import org.serviceexcel.file_service.util.AppConstants;
import org.serviceexcel.file_service.util.FileErrorCodesAndMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final EmailServiceGrpcClient grpcClient;

    private final RabbitTemplate rabbitTemplate;

    private final RuntimeService runtimeService;

    private final Map<String, Object> variables = new HashMap<>();

    @Value("${uploadDir}")
    private String UPLOAD_DIR;

    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return AppConstants.NO_SPACE;
        }
        int dotIndex = originalFileName.lastIndexOf(AppConstants.DOT);
        if (dotIndex > AppConstants.ZERO && dotIndex < originalFileName.length() - AppConstants.ONE) {
            return originalFileName.substring(dotIndex);
        }
        return AppConstants.NO_SPACE;
    }

    public Boolean checkFileExtensionPdf(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return fileExtension.equals(AppConstants.EXTENSION_FILE_PDF);
    }

    @Override
    public BaseSuccessResponse<String> fileRouting(MultipartFile file, String email, String message) throws IOException {
        if (file.isEmpty()) {
            throw new CustomException(FileErrorCodesAndMessage.FILE_IS_EMPTY);
        }
        if (email == null) {
            email = AppConstants.DEFAULT_EMAIL;
        }
        if (message == null) {
            message = AppConstants.DEFAULT_TEXT_EMAIL;
        }
        variables.put("file", file.getBytes());
        variables.put("fileName", file.getOriginalFilename());
        variables.put("email", email);
        variables.put("text", message);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("fileRoutingProcess", variables);

        if (checkFileExtensionPdf(file.getOriginalFilename())) {
            return new BaseSuccessResponse<>(grpcClient.sendEmailWithAttachment(email, message, file.getBytes(), file.getOriginalFilename()));
        } else {
            try {
                FileRabbitRequest fileMessage = new FileRabbitRequest(file.getOriginalFilename(), file.getBytes());
                rabbitTemplate.convertAndSend(AppConstants.EXCHANGE_NAME, AppConstants.ROUTING_KEY, fileMessage);
            } catch (Exception e) {
                throw new CustomException(FileErrorCodesAndMessage.FILE_LOADING_ERROR);
            }
            return new BaseSuccessResponse<>(AppConstants.SEND_YANDEX_CALENDAR_OK);
        }
    }
}
