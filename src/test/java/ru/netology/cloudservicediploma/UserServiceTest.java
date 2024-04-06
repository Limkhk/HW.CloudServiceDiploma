package ru.netology.cloudservicediploma;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.repository.UserRepository;
import ru.netology.cloudservicediploma.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User userEntity;

    private static String login;

    @BeforeAll
    static void init() {
        userEntity = new User(
                "user",
                "password"
        );

        login = "Random String";
    }

    @Test
    void getUserByLogin_success() {
        when(userRepository.getUserByLogin(login))
                .thenReturn(Optional.of(userEntity));

        assertEquals(userEntity, userService.getUserByLogin(login));
        verify(userRepository, times(1)).getUserByLogin(login);
    }
}
