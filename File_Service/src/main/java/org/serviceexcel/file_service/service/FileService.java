package org.serviceexcel.file_service.service;

import org.serviceexcel.file_service.response.BaseSuccessResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    BaseSuccessResponse<String> fileRouting(MultipartFile file, String email, String text) throws IOException;
}
