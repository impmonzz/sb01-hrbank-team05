package com.codeit.demo.storage;

import com.codeit.demo.entity.BinaryContent;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class LocalBinaryContentStorage implements BinaryContentStorage{
    private Path root = Paths.get(System.getProperty("user.dir"), "hrBank05");
    private final Path fileDir = root.resolve("file");
    private final Path backupDir = root.resolve("backup");

    private Path reosolveFilePath(Long id) {
        return fileDir.resolve(id.toString());
    }


    @PostConstruct
    public void init() {
        if(!Files.exists(root)) {
            try{
                Files.createDirectories(root);
            }catch (IOException e){
                throw new RuntimeException("폴더 초기화 실패",e);
            }
        }
    }

    @Override
    public Long put(Long id, byte[] content) {
        Path filePath = reosolveFilePath(id);
        try {
            Files.write(filePath, content, StandardOpenOption.CREATE_NEW);
            log.info("저장: {}", filePath.toAbsolutePath());
            return id;
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("파일이 이미 존재합니다"+ id, e);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    @Override
    public InputStream get(Long id) {
        Path filePath = reosolveFilePath(id);
        try{
            return Files.newInputStream(filePath);
        }catch (IOException e){
            throw new RuntimeException("파일을 찾을 수 없습니다.",e);
        }
    }

    @Override
    public ResponseEntity<?> download(BinaryContent binaryContent) {
        Path filePath = reosolveFilePath(binaryContent.getId());
        File file = filePath.toFile();
        if(!file.exists()){
            return ResponseEntity.notFound().build();
        }
        try {
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(filePath));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(binaryContent.getFileFormat()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+binaryContent.getFileName()+"\"")
                    .body(resource);
        }catch (IOException e){
            throw new RuntimeException("파일 다운로드를 실패하였습니다.",e);
        }
    }
}
