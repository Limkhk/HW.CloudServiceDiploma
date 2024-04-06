package ru.netology.cloudservicediploma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.cloudservicediploma.entity.User;
import ru.netology.cloudservicediploma.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getUserByLogin(String login) {
        Optional<User> userEntity = userRepository.getUserByLogin(login);
        if (userEntity.isEmpty()) {
            log.error("Для переданного значения login {} не найден пользователь в БД", login);
        }
        return userEntity.get();
    }
}
