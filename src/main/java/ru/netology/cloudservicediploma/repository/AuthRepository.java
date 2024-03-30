package ru.netology.cloudservicediploma.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class AuthRepository {

    private final Map<String, String> authUsers = new ConcurrentHashMap<>();

    public UUID createToken(String login) {
        return UUID.nameUUIDFromBytes((login + System.currentTimeMillis()).getBytes());
    }

    public Optional<String> getLoginByToken(String authToken) {
        return Optional.ofNullable(authUsers.get(authToken));
    }

    public void deleteToken(String authToken) {
        authUsers.remove(authToken);
    }
}
