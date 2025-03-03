package com.dunice.projectNews.services;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);

    UrlResource giveFile(String fileName);
}
