package com.dunice.projectNews.services.impl;

import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.services.FileService;
import com.dunice.projectNews.util.AppConstants;
import com.dunice.projectNews.util.ServerErrorCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${uploadDir}")
    private String UPLOAD_DIR;

    @Value("${sitePath}")
    private String SITE_PATH;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CustomException(ServerErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED);
        }
        try {
            File directoryForUploadFile = new File(UPLOAD_DIR);
            if (!directoryForUploadFile.exists()) {
                directoryForUploadFile.mkdir();
            }
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String uuidFileName = UUID.randomUUID() + fileExtension;
            Path path = Paths.get(UPLOAD_DIR + File.separator + uuidFileName);
            file.transferTo(path);
            return SITE_PATH + uuidFileName;
        } catch (IOException e) {
            throw new CustomException(ServerErrorCodes.UNKNOWN);
        }
    }

    @Override
    public UrlResource giveFile(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + File.separator).resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new CustomException(ServerErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED);
            }
        } catch (MalformedURLException e) {
            throw new CustomException(ServerErrorCodes.UNKNOWN);
        }
    }

    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return AppConstants.NO_SPACE;
        }
        int dotIndex = originalFileName.lastIndexOf(AppConstants.DOT);
        if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
            return originalFileName.substring(dotIndex);
        }
        return AppConstants.NO_SPACE;
    }
}