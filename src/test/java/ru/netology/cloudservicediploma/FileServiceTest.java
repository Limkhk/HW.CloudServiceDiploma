package ru.netology.cloudservicediploma;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservicediploma.entity.File;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.repository.FileRepository;
import ru.netology.cloudservicediploma.service.FileService;
import ru.netology.cloudservicediploma.service.UserService;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private FileRepository fileRepository;
    @InjectMocks
    private FileService fileService;
    @Mock
    private UserService userService;
    private static String login;
    private static String fileName;
    private static String newFileName;
    private static MultipartFile file;
    private static User userEntity;
    private static File fileEntity;
    private static long size;
    private static byte[] fileContent;

    @BeforeAll
    static void init() throws IOException {
        login = "Random login";

        fileName = "Random fileName";

        newFileName = "Random newFileName";

        file = mock(MockMultipartFile.class);

        size = file.getSize();

        fileContent = file.getBytes();

        userEntity = new User(
                "user",
                "password"
        );

        fileEntity = new File(
                fileName,
                size,
                fileContent,
                userEntity
        );

    }

    @Test
    void uploadFile() throws IOException {
        when(userService.getUserByLogin(login))
                .thenReturn(userEntity);

        fileService.uploadFile(login, fileName, size, fileContent);

        verify(fileRepository, times(1))
                .save(fileEntity);
    }

    @Test
    void downloadFile() {
        when(userService.getUserByLogin(login))
                .thenReturn(userEntity);
        when(fileRepository.findByFileNameAndUser(fileName, userEntity))
                .thenReturn(Optional.of(fileEntity));

        fileService.downloadFile(login, fileName);

        verify(fileRepository, times(1))
                .findByFileNameAndUser(fileName, userEntity);
    }

    @Test
    void renameFile() {
        when(userService.getUserByLogin(login))
                .thenReturn(userEntity);

        fileService.renameFile(login, fileName, newFileName);

        verify(fileRepository, times(1))
                .renameFile(fileName, newFileName, userEntity);
    }

    @Test
    void deleteFile() {
        when(userService.getUserByLogin(login))
                .thenReturn(userEntity);

        fileService.deleteFile(login, fileName);

        verify(fileRepository, times(1))
                .deleteByFileNameAndUser(fileName, userEntity);
    }

    @Test
    void getFileList() {
        when(userService.getUserByLogin(login))
                .thenReturn(userEntity);

        fileService.getFileList(login, 3);

        verify(fileRepository, times(1))
                .findAllByUser(userEntity);
    }

    @Test
    void checkUniqueFileName_success() {
        when(fileRepository.findByFileNameAndUser(fileName, userEntity))
                .thenReturn(Optional.empty());

        fileService.checkUniqueFileName(fileName, userEntity);

        verify(fileRepository, times(1))
                .findByFileNameAndUser(fileName, userEntity);
    }
}
