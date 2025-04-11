package ru.netology.filecloud.service;

import ru.netology.filecloud.entity.User;


public interface UserService {
    User findUser(String userLog);

    boolean existUser(String userLogin);
}
