package ru.netology.cloudservicediploma.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Slf4j
public class AuthRepository {

    private final Map<String, String> authUsers = new ConcurrentHashMap<>();

    public UUID createToken(String login) {
        UUID authToken = UUID.randomUUID();
        authUsers.put(authToken.toString(), login);
        log.debug("Для пользователь <{}> сформирован токен {}", login, authToken);
        return authToken;
    }

    public Optional<String> getLoginByToken(String authToken) {
        return Optional.ofNullable(authUsers.get(authToken));
    }

    public void deleteToken(String authToken) {
        authUsers.remove(authToken);
    }
}
