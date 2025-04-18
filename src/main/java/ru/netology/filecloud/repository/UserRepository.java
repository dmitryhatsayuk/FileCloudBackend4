package ru.netology.filecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.filecloud.entity.Users;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findUserByLogin(String login);

    Users findByLogin(String login);

    boolean existsUserByLogin(String userLogin);
}
