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
public class ListResponse {

    @JsonProperty("filename")
    private String fileName;

    @JsonProperty("size")
    private int fileSize;
}
