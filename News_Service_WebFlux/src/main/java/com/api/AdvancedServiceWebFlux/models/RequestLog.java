package com.api.AdvancedServiceWebFlux.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table("news_schema.request_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {

    @Id
    private LocalDateTime timestamp;

    private String method;

    private String url;

    private Integer status;

    private String errors;

    private String user;
}
