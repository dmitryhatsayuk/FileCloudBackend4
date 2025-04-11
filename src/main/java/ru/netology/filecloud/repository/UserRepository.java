package ru.netology.filecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.filecloud.entity.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByLogin(String login);

    User findByLogin(String login);

    boolean existsUserByLogin(String userLogin);
}
