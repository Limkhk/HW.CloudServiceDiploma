package ru.netology.cloudservicediploma.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class AuthRepository {

    private Map<String, String> authUsers = new ConcurrentHashMap<>();

    public UUID createToken(String login) {
        UUID authToken = UUID.randomUUID();
        authUsers.put(authToken.toString(), login);
        System.out.println(authToken);
        return authToken;
    }

    public Optional<String> getLoginByToken(String authToken) {
        return Optional.ofNullable(authUsers.get(authToken));
    }

    public void deleteToken(String authToken) {
        authUsers.remove(authToken);
    }
}
