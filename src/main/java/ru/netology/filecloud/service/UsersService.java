package ru.netology.filecloud.service;

import ru.netology.filecloud.entity.Users;


public interface UsersService {
    Users findUser(String userLog);

    boolean existUser(String userLogin);
}
