package com.codeit.demo.repository;

import com.codeit.demo.entity.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, Long> {

    List<BinaryContent> findAllByIdIn(List<Long> ids);
}
