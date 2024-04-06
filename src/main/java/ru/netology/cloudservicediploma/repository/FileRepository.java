package ru.netology.cloudservicediploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.entity.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository <File, String> {

    @Transactional
    List<File> findAllByUser(User userEntity);

    @Modifying
    @jakarta.transaction.Transactional
    @Query("update File f set f.fileName = :newFileName where f.user = :userEntity and  f.fileName = :fileName")
    void renameFile(String fileName, String newFileName, User userEntity);

    @jakarta.transaction.Transactional
    void deleteByFileNameAndUser(String fileName, User userEntity);

    @jakarta.transaction.Transactional
    Optional<File> findByFileNameAndUser(String fileName, User userEntity);

}
