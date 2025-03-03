package com.dunice.projectNews.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_logs", schema = "news_schema")
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

    @Column(name = "users_email")
    private String user;
}
