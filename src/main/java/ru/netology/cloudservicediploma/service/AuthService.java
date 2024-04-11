package ru.netology.cloudservicediploma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.entity.dto.AuthRequestDto;
import ru.netology.cloudservicediploma.entity.dto.AuthResponseDto;
import ru.netology.cloudservicediploma.repository.AuthRepository;
import ru.netology.cloudservicediploma.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public AuthResponseDto loginUser(AuthRequestDto authRequestDto) {
        Optional<User> user = userRepository.getUserByLogin(authRequestDto.login());
        return new AuthResponseDto(authRepository.createToken(user.get().getLogin()));
    }

    public String getLoginByToken(String authToken) {
        if (authToken.startsWith("Bearer "))
            authToken = authToken.substring(7);

        Optional<String> login = authRepository.getLoginByToken(authToken);

        if (login.isEmpty()) {
            log.error("Для переданного значения auth-token {} не найден пользователь в БД", authToken);
        }
        return login.get();
    }

    public void logoutUser(String authToken) {
        authRepository.deleteToken(authToken);
    }
}


