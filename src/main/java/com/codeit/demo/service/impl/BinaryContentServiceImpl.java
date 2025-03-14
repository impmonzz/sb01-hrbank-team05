package com.codeit.demo.service.impl;

import com.codeit.demo.entity.BinaryContent;
import com.codeit.demo.repository.BinaryContentRepository;
import com.codeit.demo.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BinaryContentServiceImpl {
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;

    @Transactional
    public BinaryContent create(BinaryContent binaryContent,byte[] data) {
        BinaryContent savedContent = binaryContentRepository.save(binaryContent);
        binaryContentStorage.put(savedContent.getId(), data);
        return savedContent;
    }

    public BinaryContent findById(Long id) {
        return binaryContentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다."));
    }

    public List<BinaryContent> findAllByIdIn(List<Long> ids) {
        return binaryContentRepository.findAllByIdIn(ids);
    }

    @Transactional
    public void delete(Long id) {
        binaryContentRepository.deleteById(id);
    }
}
