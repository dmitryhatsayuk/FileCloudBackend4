package ru.netology.filecloud.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.filecloud.entity.Users;
import ru.netology.filecloud.repository.UsersRepository;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UsersService {

    private UsersRepository userRepository;

    @Override
    @Transactional
    public Users findUser(String userLogin) {
        return userRepository.findUsersByLogin(userLogin).orElseThrow();
    }

    @Override
    public boolean existUser(String userLogin) {
        return userRepository.existsUsersByLogin(userLogin);
    }
}