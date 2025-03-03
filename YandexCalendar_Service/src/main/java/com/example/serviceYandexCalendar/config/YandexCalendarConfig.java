package com.example.serviceYandexCalendar.config;

import com.example.serviceYandexCalendar.util.AppConstant;
import com.github.caldav4j.CalDAVCollection;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.List;

@Configuration
public class YandexCalendarConfig {

    @Value("${yandex.calendar.username}")
    private String username;

    @Value("${yandex.calendar.password}")
    private String password;

    @Value("${yandex.calendar.url}")
    private String calDavUrl;

    @Bean
    public HttpClient getHttpClient() {
        String credit = Base64.getEncoder().encodeToString((username + AppConstant.COLON + password).getBytes());
        Header header = new BasicHeader(AppConstant.AUTHORIZED_HEAD, AppConstant.BASIC_HEADER + credit);
        return HttpClientBuilder.create().setDefaultHeaders(List.of(header)).build();
    }

    @Bean
    public CalDAVCollection getCalDAVCollection() {
        return new CalDAVCollection(calDavUrl);
    }
}
