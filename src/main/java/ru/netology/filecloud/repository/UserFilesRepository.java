package ru.netology.filecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.filecloud.entity.File;

import java.util.List;
import java.util.Optional;

public interface UserFilesRepository extends JpaRepository<File, Integer> {

    List<File> findByUser_Login(String log);

    Optional<File> findUserFilesByFileNameAndUser_Login(String fileName, String userLogin);

    Optional<File> deleteUserFilesByFileNameAndUser_Login(String fileName, String userLogin);

    boolean existsUserFilesByFileNameAndUser_Login(String fileName, String userLogin);
}
