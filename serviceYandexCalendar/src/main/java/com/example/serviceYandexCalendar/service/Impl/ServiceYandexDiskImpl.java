package com.example.serviceYandexCalendar.service.Impl;

import com.example.serviceYandexCalendar.dto.FileRabbitRequest;
import com.example.serviceYandexCalendar.exception.CustomException;
import com.example.serviceYandexCalendar.service.ServiceYandexCalendar;
import com.example.serviceYandexCalendar.service.ServiceYandexDisk;
import com.example.serviceYandexCalendar.util.AppConstant;
import com.example.serviceYandexCalendar.util.FileErrorCodesAndMessage;
import com.github.caldav4j.exceptions.CalDAV4JException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServiceYandexDiskImpl implements ServiceYandexDisk {


    private final ServiceYandexCalendar serviceYandexCalendar;

    @Value("${yandex.token}")
    private String OAUTH_TOKEN;

    @Value("${yandex.disk.url.resourse}")
    private String URL_RESOURSE;

    @Value("${yandex.disk.url.upload}")
    private String URL_UPLOAD;

    @Value("${yandex.calendar.username}")
    private String username;

    @Value("${yandex.disk.url.resourse.pub}")
    private String URL_RESOURSE_PUB;

    private String createFolder(String folderPath) {
        if (folderPath.matches(AppConstant.PATTERN_FOLDER)) {
            try {
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_RESOURSE)
                        .queryParam(AppConstant.PATH, folderPath);
                executeRestRequest(builder.toUriString(), HttpMethod.PUT, null, String.class);
                return folderPath;
            } catch (HttpClientErrorException.Conflict e1) {
                return folderPath;
            } catch (HttpClientErrorException e) {
                return AppConstant.DEFAULT_FOLDER;
            }
        }
        throw new CustomException(FileErrorCodesAndMessage.FOLDER_NAME_NOT_MATCHER_PATTERN);
    }

    private String getUploadLink(String uploadPath) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_UPLOAD)
                .queryParam(AppConstant.PATH, uploadPath);
        ResponseEntity<String> response = executeRestRequest(
                builder.toUriString(), HttpMethod.GET, null, String.class);

        String responseBody = response.getBody();
        int startIndex = responseBody.indexOf(AppConstant.PATTERN_INDEXOF) + 8;
        int endIndex = responseBody.indexOf(AppConstant.BACKSLASH, startIndex);
        return responseBody.substring(startIndex, endIndex);
    }

    @RabbitListener(queues = "fileQueue")
    public void handleMessage(FileRabbitRequest request) {
        try {
            uploadFile(request.getFileContent(), request.getFileName(), null);
        } catch (CustomException e) {
            System.out.println(FileErrorCodesAndMessage.mapErrors.get(e.getFileErrorCodesAndMessage()));
        }
    }

    public String uploadFile(byte[] bytes, String filename, String folder) {
        if (folder == null) {
            folder = AppConstant.DEFAULT_FOLDER;
        } else {
            folder = createFolder(folder);
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String uploadLink = getUploadLink(folder + AppConstant.SLASH + filename);

            executeRestRequest(uploadLink, HttpMethod.PUT, bytes, String.class);

            String jsonResponse = getPublicUrl(folder + AppConstant.SLASH + filename);

            JSONObject jsonObject = new JSONObject(jsonResponse);

            String fileUrl = jsonObject.getString("href");

            LocalDateTime start = LocalDateTime.of(2025, 1, 25, 17, 00);
            LocalDateTime end = LocalDateTime.of(2025, 1, 25, 18, 00);
            serviceYandexCalendar.sendServiceYandexCalendar(start, end, "Очень важная встреча", fileUrl);
            stringBuilder.append(AppConstant.FILE_SEND_DISK_OK);
            stringBuilder.append(folder);
            stringBuilder.append(AppConstant.EVENT_SEND_OK);
            stringBuilder.append(AppConstant.START);
            stringBuilder.append(start);
            stringBuilder.append(AppConstant.END);
            stringBuilder.append(end);
            stringBuilder.append(AppConstant.DESCRIPTION);
            stringBuilder.append(fileUrl);
        } catch (CalDAV4JException e) {
            throw new CustomException(FileErrorCodesAndMessage.FAILED_TO_CREATE_AN_MEETING);
        } catch (HttpClientErrorException.Conflict e1) {
            throw new CustomException(FileErrorCodesAndMessage.FILE_NAME_ALREADY_EXIST);
        } catch (Exception e2) {
            throw new CustomException(FileErrorCodesAndMessage.FILE_LOADING_ERROR_BY_DISK);
        }
        return stringBuilder.toString();
    }

    private ResponseEntity<String> executeRestRequest(String url, HttpMethod method, byte[] bytes, Class<String> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(AppConstant.AUTHORIZED_HEAD, AppConstant.TOKEN_HEADER + OAUTH_TOKEN);
        HttpEntity<?> entity;
        if (bytes != null) {
            entity = new HttpEntity<byte[]>(bytes, headers);
        } else {
            entity = new HttpEntity<>(headers);
        }
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                method,
                entity,
                responseType
        );
        return response;
    }

    private String getPublicUrl(String filePath) throws IOException, InterruptedException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_RESOURSE_PUB)
                .queryParam(AppConstant.PATH, filePath);
        ResponseEntity<String> getResponse = executeRestRequest(builder.toUriString(), HttpMethod.PUT, null, String.class);
        return getResponse.getBody().toString();
    }
}