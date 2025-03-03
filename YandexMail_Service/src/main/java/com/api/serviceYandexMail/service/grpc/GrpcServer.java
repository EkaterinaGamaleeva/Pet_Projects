package com.api.serviceYandexMail.service.grpc;

import com.api.serviceYandexMail.service.EmailService;
import com.api.serviceYandexMail.service.grpc.EmailServiceImplGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcServer implements CommandLineRunner {
private final EmailService emailService;
    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(8082)
                .addService(new EmailServiceImplGrpc(emailService))
                .build();
        server.start();
        server.awaitTermination();
    }
}
