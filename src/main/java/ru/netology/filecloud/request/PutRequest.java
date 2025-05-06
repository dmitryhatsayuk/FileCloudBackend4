package ru.netology.filecloud.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Сущность для запросов на переименование
public class PutRequest {
    private String filename;
}
