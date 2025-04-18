package ru.netology.filecloud.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.filecloud.entity.Users;
import ru.netology.filecloud.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public Users findUser(String userLogin) {
        return userRepository.findUserByLogin(userLogin).orElseThrow();
    }

    @Override
    public boolean existUser(String userLogin) {
        return userRepository.existsUserByLogin(userLogin);
    }
}