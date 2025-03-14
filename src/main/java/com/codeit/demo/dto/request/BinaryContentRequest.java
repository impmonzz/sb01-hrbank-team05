package com.codeit.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BinaryContentRequest {
    private String fileName;
    private Long size;
    private String contentType;
    private byte[] bytes;
}
