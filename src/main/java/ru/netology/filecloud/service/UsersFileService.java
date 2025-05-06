package ru.netology.filecloud.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.filecloud.entity.File;
import ru.netology.filecloud.response.ListResponse;

import java.io.IOException;
import java.util.List;

public interface UsersFileService {

    List<File> findUserFilesByUserLogin(String login) throws IOException;

    boolean createNewFile(String fileName, MultipartFile file) throws IOException;

    boolean deleteUserFilesByFileNameAndUser_Login(String fileName) throws IOException;

    File findUserFileByFileNameAndUser_Login(String fileName, String userLogin);

    boolean existsUserFile(String fileName, String userLogin);

    List<ListResponse> getList();

    String readFile(String fileName);

    File changeFileName(String fileName, String newName);
}
