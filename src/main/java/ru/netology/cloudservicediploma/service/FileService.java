package ru.netology.cloudservicediploma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.cloudservicediploma.entity.File;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.entity.dto.FileDto;
import ru.netology.cloudservicediploma.repository.FileRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final UserService userService;
    private final FileRepository fileRepository;

    public void uploadFile(String login, String fileName, long size, byte[] fileContent) throws IOException {
        User userEntity = userService.getUserByLogin(login);
        File fileEntity = new File(fileName, size, fileContent, userEntity);
        fileRepository.save(fileEntity);
    }

    public byte[] downloadFile(String login, String fileName) {
        User userEntity = userService.getUserByLogin(login);
        return fileRepository.findByFileNameAndUser(fileName, userEntity).get().getFileContent();
    }

    public void renameFile(String login, String fileName, String newFileName) {
        User userEntity = userService.getUserByLogin(login);
        checkUniqueFileName(fileName, userEntity);
        fileRepository.renameFile(fileName, newFileName, userEntity);
    }

    public void deleteFile(String login, String fileName) {
        User userEntity = userService.getUserByLogin(login);
        fileRepository.deleteByFileNameAndUser(fileName, userEntity);
    }

    public List<FileDto> getFileList(String login, int limit) {
        User userEntity = userService.getUserByLogin(login);
        return fileRepository.findAllByUser(userEntity)
                .stream()
                .limit(limit)
                .map(file -> new FileDto(file.getFileName(), file.getSize()))
                .collect(Collectors.toList());
    }

    public void checkUniqueFileName(String fileName, User userEntity) {
        if (fileRepository.findByFileNameAndUser(fileName, userEntity).isPresent()) {
            log.error("Не пройдена проверка уникальности имени файла {} для пользователем {}", fileName, userEntity.getLogin());
        }
    }
}