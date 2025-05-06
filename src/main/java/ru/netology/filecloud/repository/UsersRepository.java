package ru.netology.filecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.filecloud.entity.Users;

import java.util.Optional;

//Интерфейс для пользовательских учеток
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findUsersByLogin(String login);

    Users findByLogin(String login);

    boolean existsUsersByLogin(String userLogin);
}
