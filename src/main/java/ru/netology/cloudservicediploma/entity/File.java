package ru.netology.cloudservicediploma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(FileId.class)
@Table(name = "FILES")
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    private String fileName;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @Id
    @ManyToOne(optional = false)
    private User user;
}