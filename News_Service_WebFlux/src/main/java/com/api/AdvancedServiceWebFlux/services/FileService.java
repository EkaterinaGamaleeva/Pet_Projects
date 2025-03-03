package com.api.AdvancedServiceWebFlux.services;

import org.springframework.core.io.UrlResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface FileService {
    Mono<String> uploadFile(Mono<FilePart> file);

    Mono<UrlResource> giveFile(Mono<String> fileName);
}
