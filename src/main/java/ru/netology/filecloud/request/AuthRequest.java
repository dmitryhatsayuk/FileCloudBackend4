package ru.netology.filecloud.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Сущность для запросов на аутентификацию
public class AuthRequest {
    private String login;
    private String password;

}