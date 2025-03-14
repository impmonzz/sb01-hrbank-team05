package com.codeit.demo.storage;

import com.codeit.demo.entity.BinaryContent;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

public interface BinaryContentStorage {
    Long put(Long id,byte[] content);
    InputStream get(Long id);
    ResponseEntity<?> download(BinaryContent binaryContent);
}
