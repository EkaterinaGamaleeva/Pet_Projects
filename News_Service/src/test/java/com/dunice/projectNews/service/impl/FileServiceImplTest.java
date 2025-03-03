package com.dunice.projectNews.service.impl;

import com.dunice.projectNews.constants.TestConstants;
import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.services.impl.FileServiceImpl;
import com.dunice.projectNews.util.ServerErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private FileServiceImpl fileService;

    private MockMultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockFile = new MockMultipartFile(TestConstants.FILE,
                TestConstants.FILE_NAME,
                TestConstants.CONTENT_TYPE,
                TestConstants.CONTENT.getBytes());
        ReflectionTestUtils.setField(fileService,
                TestConstants.UPLOAD_DIR,
                TestConstants.UPLOAD_DIR_VALUE);
        ReflectionTestUtils.setField(fileService,
                TestConstants.SITE_PATH,
                TestConstants.SITE_PATH_VALUE);
    }

    @Test
    void uploadFile_CustomSuccessResponse_uploadFileSuccess() throws Exception {
        File mockDirectory = new File(TestConstants.UPLOAD_DIR_VALUE);
        if (!mockDirectory.exists()) {
            mockDirectory.mkdir();
        }
        String response = fileService.uploadFile(mockFile);
        String filename = response.replace(TestConstants.SITE_PATH_VALUE, "");
        String request = TestConstants.SITE_PATH_VALUE + filename;
        assertNotNull(response);
        assertEquals(request, response);
    }

    @Test
    void uploadFile_CustomExceptionExceptionHandlerNotProvider_uploadFileNoSuccess() throws Exception {
        when(multipartFile.isEmpty()).thenReturn(true);
        CustomException exception = assertThrows(CustomException.class,
                () -> fileService.uploadFile(multipartFile));
        assertEquals(ServerErrorCodes
                        .EXCEPTION_HANDLER_NOT_PROVIDED
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    void uploadFile_CustomExceptionUnknown_uploadFileNoSuccess() throws Exception {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn(TestConstants.FILE_NAME);
        doThrow(IOException.class).when(multipartFile).transferTo(any(Path.class));
        CustomException exception = assertThrows(CustomException.class,
                () -> fileService.uploadFile(multipartFile));
        assertEquals(ServerErrorCodes
                        .UNKNOWN
                        .getMessage(),
                exception.getMessage());
    }

    @Test
    public void giveFile_UrlResource_giveFileSuccess() throws MalformedURLException {
        Path mockPath = Paths.get(TestConstants.MOCK_PATH).resolve(TestConstants.FILE_NAME_UUID).normalize();
        UrlResource mockResource = mock(UrlResource.class);
        UrlResource result = fileService.giveFile(TestConstants.FILE_NAME_UUID);
        Path filePath = Paths.get(TestConstants.UPLOAD_DIR_VALUE + File.separator).resolve(TestConstants.FILE_NAME_UUID).normalize();
        UrlResource request = new UrlResource(filePath.toUri());
        assertNotNull(result);
        assertTrue(result.exists());
        assertTrue(result.isReadable());
        assertEquals(request, result);
    }

    @Test
    public void giveFile_CustomExceptionExceptionHandlerNotProvider_giveFileNoSuccess() {
        CustomException exception = assertThrows(CustomException.class,
                () -> fileService.giveFile(TestConstants.FILE_NAME));
        assertEquals(ServerErrorCodes
                        .EXCEPTION_HANDLER_NOT_PROVIDED
                        .getMessage(),
                exception.getMessage());
    }
}
