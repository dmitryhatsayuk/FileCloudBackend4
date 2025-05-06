package ru.netology.filecloud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Users {
    //Сущность для пользователя.
// Название во множественном числе поскольку я не смог победить нелюбовь Postgres к 'user'
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String login;

    private String password;

    private String authority;
}
