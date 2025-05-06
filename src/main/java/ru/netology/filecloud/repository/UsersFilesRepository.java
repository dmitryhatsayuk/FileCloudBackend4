package ru.netology.filecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.filecloud.entity.File;

import java.util.List;
import java.util.Optional;

//Интерфейс для пользовательских файлов

public interface UsersFilesRepository extends JpaRepository<File, Integer> {

    List<File> findByUsers_Login(String log);

    Optional<File> findUsersFilesByFileNameAndUsers_Login(String fileName, String usersLogin);

    Optional<File> deleteUsersFilesByFileNameAndUsers_Login(String fileName, String usersLogin);

    boolean existsUsersFilesByFileNameAndUsers_Login(String fileName, String usersLogin);
}
