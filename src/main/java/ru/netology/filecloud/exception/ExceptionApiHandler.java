package ru.netology.filecloud.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.filecloud.response.ErrorResponse;

import java.io.IOException;

@NoArgsConstructor
@RestControllerAdvice
//Класс для реализации ошибок
public class ExceptionApiHandler {

    //неверные учетные данные
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse notFoundException(@NotNull BadCredentialsException exception) {
        return new ErrorResponse(exception.getMessage(), exception.hashCode());
    }

    //неверные входящие данные
    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse errorInputData(@NotNull IOException exception) {
        return new ErrorResponse("Error input data", exception.hashCode());
    }

    //для внутренних ошибок
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse errorIOData(@NotNull EntityNotFoundException exception) {
        return new ErrorResponse(exception.getMessage(), exception.hashCode());
    }
}
