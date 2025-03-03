package com.api.AdvancedServiceWebFlux.services.impl;

import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.services.FileService;
import com.api.AdvancedServiceWebFlux.util.AppConstants;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<String> uploadFile(Mono<FilePart> file) {
       return file
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED)))
                        .flatMap(e->{
                            File directoryForUploadFile = new File(UPLOAD_DIR);
                            if (!directoryForUploadFile.exists()) {
                                directoryForUploadFile.mkdir();
                            }
                            String fileExtension = getFileExtension(e.filename());
                            String uuidFileName = UUID.randomUUID() + fileExtension;
                            Path path = Paths.get(UPLOAD_DIR + File.separator + uuidFileName);
                            return e.transferTo(path).then(Mono.just(SITE_PATH + uuidFileName));
                        });
    }

    @Override
    public Mono<UrlResource> giveFile(Mono<String> fileName) {
       return fileName.handle((e, sink) -> {
            try {
                Path filePath = Paths.get(UPLOAD_DIR + File.separator).resolve(e).normalize();
                UrlResource resource = new UrlResource(filePath.toUri());
                if (resource.exists() && resource.isReadable()) {
                    sink.next(resource);
                } else {
                    sink.error(new CustomException(ServerErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED));
                }
            } catch (MalformedURLException eror) {
                sink.error(new CustomException(ServerErrorCodes.UNKNOWN));
            }
        });
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
