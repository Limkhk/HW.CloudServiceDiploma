package ru.netology.cloudservicediploma.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.entity.dto.FileDto;
import ru.netology.cloudservicediploma.repository.FileRepository;
import ru.netology.cloudservicediploma.service.AuthService;
import ru.netology.cloudservicediploma.service.FileService;
import ru.netology.cloudservicediploma.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class FileController {
    private final FileService fileService;
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName,
            @RequestBody MultipartFile file) throws IOException, AuthException {
        fileService.uploadFile(
                authService.getLoginByToken(authToken),
                fileName,
                file.getSize(),
                file.getBytes());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/file")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName) throws AuthException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ByteArrayResource(fileService.downloadFile(authService.getLoginByToken(authToken), fileName)));
    }

    @PutMapping("/file")
    public ResponseEntity<?> renameFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName,
            @RequestBody @Validated @JsonProperty("filename") String newFileName) throws AuthException {
        fileService.renameFile(authService.getLoginByToken(authToken), fileName, newFileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String fileName) throws AuthException {
        fileService.deleteFile(authService.getLoginByToken(authToken), fileName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDto>> getFileList(
            @RequestHeader("auth-token") String authToken,
            @RequestParam int limit) throws AuthException {
        return ResponseEntity.ok(getFileListDto(authService.getLoginByToken(authToken), limit));
    }

    public List<FileDto> getFileListDto (String login, int limit) {
        User userEntity = userService.getUserByLogin(login);
        return fileRepository.findAllByUser(userEntity)
                .stream()
                .limit(limit)
                .map(file -> new FileDto(file.getFileName(), file.getSize()))
                .collect(Collectors.toList());
    }
}

