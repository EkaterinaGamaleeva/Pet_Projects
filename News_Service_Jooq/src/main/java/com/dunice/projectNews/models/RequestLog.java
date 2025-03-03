package com.dunice.projectNews.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestLog {

    private LocalDateTime timestamp;

    private String method;

    private String url;

    private Integer status;

    private String errors;

    private String user;
}
