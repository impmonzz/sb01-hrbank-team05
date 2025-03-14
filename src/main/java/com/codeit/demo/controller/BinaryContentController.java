package com.codeit.demo.controller;

import com.codeit.demo.controller.api.BinaryContentApi;
import com.codeit.demo.entity.BinaryContent;
import com.codeit.demo.service.impl.BinaryContentServiceImpl;
import com.codeit.demo.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class BinaryContentController implements BinaryContentApi {
    private final BinaryContentServiceImpl binaryContentService;
    private final BinaryContentStorage binaryContentStorage;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<List<BinaryContent>> saveBinaryContent(@RequestParam("files") List<MultipartFile> files) {
        List<BinaryContent> savedFiles=new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                BinaryContent binaryContent = new BinaryContent(
                        file.getOriginalFilename(),
                        file.getSize(),
                        file.getContentType()
                );
                binaryContentService.create(binaryContent, file.getBytes());
                savedFiles.add(binaryContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(savedFiles);
    }

    @GetMapping("/{id}/download")
    @Override
    public ResponseEntity<?> download(@PathVariable Long id) {
        BinaryContent findFile = binaryContentService.findById(id);
        return binaryContentStorage.download(findFile);
    }

}
