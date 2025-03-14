package com.codeit.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"fileName","fileSize","fileFormat"})
@Table(name = "file")
public class BinaryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Instant createdAt;

    private String fileName;
    private Long fileSize;
    private String fileFormat;

    public BinaryContent(String fileName, Long fileSize, String fileFormat) {
        //this.createdAt = Instant.now();
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileFormat = fileFormat;
    }
}
