package org.serviceexcel.file_service.response.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class FileRabbitRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String fileName;
    private byte[] fileContent;
}
