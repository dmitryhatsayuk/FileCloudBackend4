package ru.netology.filecloud.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.filecloud.entity.File;
import ru.netology.filecloud.repository.UsersFilesRepository;
import ru.netology.filecloud.response.ListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsersFileServiceImpl implements UsersFileService {

    private final UsersFilesRepository userFilesRepository;
    private final UsersService userService;
    private final UsersFileServiceImpl userFileService;

    public UsersFileServiceImpl(UsersFilesRepository userFilesRepository,
                                UsersService userService,
                                @Lazy UsersFileServiceImpl userFileService) {
        this.userFilesRepository = userFilesRepository;
        this.userService = userService;
        this.userFileService = userFileService;
    }

    @Override
    @Transactional
    public List<File> findUserFilesByUserLogin(String login) {
        return userFilesRepository.findByUsers_Login(login);
    }

    @Override
    public boolean createNewFile(String fileName, MultipartFile file) throws IOException {

        var login = myAuthenticationLogin();
        if (userFileService.existsUserFile(fileName, login)) {
            throw new IOException();
        }
        var dbUser = userService.findUser(login);
        Optional<File> userFile = Optional.of(new File());
        userFile.get().setUsers(dbUser);
        userFile.get().setFileName(fileName);
        var fileContent = file.getBytes();
        userFile.get().setFileContent(fileContent);
        userFilesRepository.save(userFile.get());
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUserFilesByFileNameAndUser_Login(String fileName) throws IOException {
        var login = myAuthenticationLogin();
        if (!userFileService.existsUserFile(fileName, login)) {
            throw new EntityNotFoundException();
        }
        userFilesRepository.deleteUsersFilesByFileNameAndUsers_Login(fileName, login)
                .orElseThrow(IOException::new);
        return true;
    }

    @Override
    @Transactional
    public File findUserFileByFileNameAndUser_Login(String fileName, String userLogin) {
        return userFilesRepository
                .findUsersFilesByFileNameAndUsers_Login(fileName, userLogin)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean existsUserFile(String fileName, String userLogin) {
        return userFilesRepository
                .existsUsersFilesByFileNameAndUsers_Login(fileName, userLogin);
    }

    @Override
    public List<ListResponse> getList() {

        var login = myAuthenticationLogin();
        var dbUserFiles = userFileService.findUserFilesByUserLogin(login);
        List<ListResponse> nameSize = new ArrayList<>();
        dbUserFiles.forEach(db -> nameSize.add
                (new ListResponse(
                        db.getFileName(),
                        db.getFileContent().length
                )));
        return nameSize;
    }

    @Override
    public String readFile(String fileName) {
        var login = myAuthenticationLogin();
        var dbUserFile = userFileService
                .findUserFileByFileNameAndUser_Login(fileName, login);

        return Arrays.toString(dbUserFile.getFileContent());
    }

    @Override
    public File changeFileName(String fileName, String newName) {

        var login = myAuthenticationLogin();
        var dbUserFile = userFileService
                .findUserFileByFileNameAndUser_Login(fileName, login);
        dbUserFile.setFileName(newName);
        return userFilesRepository.save(dbUserFile);
    }

    public String myAuthenticationLogin() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
