package org.serviceexcel.file_service.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.serviceexcel.file_service.response.BaseSuccessResponse;
import org.serviceexcel.file_service.service.FileService;
import org.serviceexcel.file_service.util.AppConstants;
import org.serviceexcel.file_service.util.FileExceptionConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Validated
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<BaseSuccessResponse<String>> routingFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "email", required = false)
    @Pattern(regexp = AppConstants.PATTERN_FORMAT_EMAIL, message = FileExceptionConstants.EMAIL_NOT_VALID) String email, @RequestParam(value = "text", required = false) String message) throws IOException {

        return ResponseEntity
                .ok()
                .body(fileService.fileRouting(file, email, message));
    }
}
