package ru.netology.filecloud.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.filecloud.request.PutRequest;
import ru.netology.filecloud.response.ListResponse;
import ru.netology.filecloud.service.UsersFileService;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
//Основной контроллер эндпоинтов для взаимодействия с фронтом
public class Controller {

    private UsersFileService userFileService;

    //загрузка файла
    @PostMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void post(@RequestParam(name = "filename") String fileName,
                     @RequestBody MultipartFile file) throws IOException {

        userFileService.createNewFile(fileName, file);
    }

    //получение писка файлов
    @GetMapping("/list")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ListResponse> get() {

        return userFileService.getList();
    }

    //получение файла
    @GetMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public String read(@RequestParam(name = "filename") String fileName) {

        return userFileService.readFile(fileName);
    }

    //переименовать файл
    @PutMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void put(@RequestParam(name = "filename") String fileName,
                    @RequestBody PutRequest newName) {

        userFileService.changeFileName(fileName, newName.getFilename());
    }

    //удалить файл
    @DeleteMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam(name = "filename") String fileName) throws IOException {

        userFileService.deleteUserFilesByFileNameAndUser_Login(fileName);
    }
}
