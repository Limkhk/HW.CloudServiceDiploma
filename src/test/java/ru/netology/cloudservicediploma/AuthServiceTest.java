package ru.netology.cloudservicediploma;

import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.entity.dto.AuthRequestDto;
import ru.netology.cloudservicediploma.repository.AuthRepository;
import ru.netology.cloudservicediploma.repository.UserRepository;
import ru.netology.cloudservicediploma.service.AuthService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthService authService;
    private static AuthRequestDto authRequestDto;
    private static User userEntity;
    private static String authToken;

    @BeforeAll
    static void init() {
        authRequestDto = new AuthRequestDto(
                "user",
                "password"
        );

        userEntity = new User(
                "user",
                "password"
        );

        authToken = String.valueOf(UUID.randomUUID());
    }

    @Test
    void loginUser() {
        when(userRepository.getUserByLogin(authRequestDto.login()))
                .thenReturn(Optional.ofNullable(userEntity));
        when(authRepository.createToken(userEntity.getLogin()))
                .thenReturn(UUID.randomUUID());

        assertNotNull(authService.loginUser(authRequestDto));
        verify(authRepository, times(1)).createToken(userEntity.getLogin());
    }

    @Test
    void getLoginByToken_error() {
        when(authRepository.getLoginByToken(authToken))
                .thenReturn(Optional.empty());

        assertThrows(AuthException.class,
                () -> authService.getLoginByToken(authToken));
        verify(authRepository, times(1)).getLoginByToken(authToken);
    }

    @Test
    void getLoginByToken_success() {
        when(authRepository.getLoginByToken(authToken))
                .thenReturn(Optional.of("Random String"));

        assertNotNull(authService.getLoginByToken(authToken));
        verify(authRepository, times(1)).getLoginByToken(authToken);
    }

    @Test
    void logoutUser() {
        authRepository.deleteToken(authToken);
        verify(authRepository, times(1)).deleteToken(authToken);
    }
}
