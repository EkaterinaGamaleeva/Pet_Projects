package com.dunice.projectNews.controllers;

import com.dunice.projectNews.dto.common.CustomSuccessResponse;
import com.dunice.projectNews.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<CustomSuccessResponse<String>> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(fileService.uploadFile(file)));
    }

    @GetMapping("{fileName}")
    public ResponseEntity<UrlResource> getFile(
            @PathVariable(value = "fileName") String fileName) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(fileService.giveFile(fileName));

    }
}
