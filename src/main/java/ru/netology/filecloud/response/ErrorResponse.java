package ru.netology.filecloud.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Сущность для возврата ошибок

public class ErrorResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("id")
    private int id;
}
