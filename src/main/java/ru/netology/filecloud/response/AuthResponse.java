package ru.netology.filecloud.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    //Сущность ответов на запросы аутентификации
    @JsonProperty("auth-token")
    private String jwtToken;
}