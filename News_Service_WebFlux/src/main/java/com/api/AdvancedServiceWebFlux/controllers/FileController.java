package com.api.AdvancedServiceWebFlux.controllers;

import com.api.AdvancedServiceWebFlux.dto.common.CustomSuccessResponse;
import com.api.AdvancedServiceWebFlux.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/uploadFile")
    public Mono<CustomSuccessResponse<String>> uploadFile(@RequestPart(value = "file") Mono<FilePart> file) {
        return fileService.uploadFile(file).map(CustomSuccessResponse::new);
    }

    @GetMapping("{fileName}")
    public Mono<ResponseEntity<UrlResource>> getFile(
            @PathVariable(value = "fileName") String fileName) {
        return fileService.giveFile(Mono.just(fileName)).map(e-> ResponseEntity
                    .ok()
                    .contentType(MediaType.MULTIPART_FORM_DATA).body(e));
    }
}
