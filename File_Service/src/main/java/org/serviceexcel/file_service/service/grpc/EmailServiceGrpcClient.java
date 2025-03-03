package org.serviceexcel.file_service.service.grpc;

import com.example.grpc.EmailServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class EmailServiceGrpcClient {

    private final ManagedChannel channel;
    private final EmailServiceGrpc.EmailServiceBlockingStub blockingStub;


    public EmailServiceGrpcClient(){
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8089)
                .usePlaintext()
                .build();
        this.blockingStub = EmailServiceGrpc.newBlockingStub(channel);
    }

    public String sendEmailWithAttachment(String email, String text, byte[] fileContent, String filename) throws IOException {

        com.example.grpc.SendFileRequest request = com.example.grpc.SendFileRequest.newBuilder()
                .setEmail(email)
                .setText(text)
                .setFile(com.google.protobuf.ByteString.copyFrom(fileContent))
                .setFilename(filename)
                .build();

        com.example.grpc.SendFileResponse response = blockingStub.sendEmailWithAttachment(request);
        return response.getMessage();
    }
}