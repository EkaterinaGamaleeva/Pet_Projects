package com.api.serviceYandexMail.service.grpc;

import com.api.serviceYandexMail.service.EmailService;
import com.example.grpc.SendFileRequest;
import com.example.grpc.SendFileResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImplGrpc extends com.example.grpc.EmailServiceGrpc

        .EmailServiceImplBase {
    private final EmailService emailService;


    @Override
    public void sendEmailWithAttachment(SendFileRequest request, StreamObserver<SendFileResponse> responseObserver) {

        String responseMessage = emailService.sendEmailWithAttachment(request.getEmail(), request.getText(), request.getFile().toByteArray(),request.getFilename());

        SendFileResponse response = SendFileResponse.newBuilder()
                .setMessage(responseMessage)
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
