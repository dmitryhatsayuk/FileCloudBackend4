package ru.netology.filecloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.filecloud.entity.File;
import ru.netology.filecloud.entity.Users;
import ru.netology.filecloud.request.PutRequest;
import ru.netology.filecloud.response.ListResponse;
import ru.netology.filecloud.security.JWTUtil;
import ru.netology.filecloud.service.CustomUserDetailsService;
import ru.netology.filecloud.service.UsersFileService;
import ru.netology.filecloud.service.UsersService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(Controller.class)
public class ControllerMockMvcUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersFileService usersFileService;

    @MockBean
    private UsersService usersService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private Controller controller;


    @Test
    @DisplayName("Post /file сохраняет файл в базе и возвращает HTTP статус 200 ОК")
    void postNewFileThenGivenOkStatus() throws Exception {
        String fieldName = "filename";
        String fileName = "TestFile5.jpg";
        byte[] content = new byte[]{(byte) 12345};

        MockMultipartFile file = new MockMultipartFile(
                fileName,
                fileName + "TestFile3.jpg",
                "multipart/form-data/plain",
                content);

        when(usersFileService.createNewFile(fileName, file))
                .thenReturn(true);


        mockMvc.perform(post("/file")
                        .param(fieldName, fileName)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .content(String.valueOf(file)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get /list возвращает список имен файлов и их размер")
    void getListOfFileNamesAndSize() throws Exception {
        byte[] content1 = new byte[]{(byte) 54321};
        byte[] content2 = new byte[]{(byte) 12345};
        List<ListResponse> listResponse = new ArrayList<>();
        listResponse.add(new ListResponse("TestFile1.jpg", content1.length));
        listResponse.add(new ListResponse("estFile2.jpg", content2.length));
        when(usersFileService.getList()).thenReturn(listResponse);
        mockMvc.perform(get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listResponse)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].filename", containsInAnyOrder("TestFile1.jpg", "TestFile2.jpg")));
    }

    @Test
    @DisplayName("DELETE /file удаляет файл и возвращает HTTP статус 200 ОК")
    void deleteByName() throws Exception {


        Users client = new Users(1, "user", "user", "USER");
        byte[] content = new byte[]{(byte) 12345};
        File clientFile = new File(
                1,
                "TestFile2.jpg",
                content,
                client);

        when(usersFileService
                .deleteUserFilesByFileNameAndUser_Login("TestFile2.jpg"))
                .thenReturn(true);

        mockMvc.perform(delete("/file")
                        .param("filename", "TestFile2.jpg")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /file возвращает имя файла и содержимое по имени")
    void getFileNameAndContentByFileName() throws Exception {
        byte[] content = new byte[]{(byte) 12345};
        when(usersFileService
                .readFile(anyString()))
                .thenReturn(Arrays.toString(content));
        mockMvc.perform(get("/file")
                        .param("filename", "TestFile1.jpg")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Arrays.toString(content)));
    }


    @Test
    @DisplayName("PUT /file меняет имя файла и возвращает HTTP статус 200 ОК")
    void putByName() throws Exception {
        String fileName = "TestFile1.jpg";
        Users client = new Users(1, "user", "user", "USER");
        byte[] content = new byte[]{(byte) 12345};
        PutRequest body = new PutRequest("NewTestFileName1.jpg");
        File clientFile = new File(
                1,
                "TestFile1.jpg",
                content,
                client);
        when(usersFileService.changeFileName(fileName, body.getFilename()))
                .thenReturn(clientFile);
        mockMvc.perform(put("/file")
                        .param("filename", fileName)
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void test() {
        assertThat(controller).isNotNull();
    }

    @Test
    void accessDenied() throws Exception {
        mockMvc.perform(post("/login"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void accessGranted() throws Exception {
        mockMvc.perform(get("/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
