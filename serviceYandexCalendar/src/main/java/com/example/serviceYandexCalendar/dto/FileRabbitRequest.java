package com.example.serviceYandexCalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class FileRabbitRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private byte[] fileContent;
}
