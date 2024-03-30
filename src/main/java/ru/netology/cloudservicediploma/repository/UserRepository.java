package ru.netology.cloudservicediploma.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudservicediploma.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByLogin(String login);

}
