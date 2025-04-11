package ru.netology.filecloud.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.filecloud.customMultipart.CustomMultipart;
import ru.netology.filecloud.entity.File;
import ru.netology.filecloud.repository.UserFilesRepository;
import ru.netology.filecloud.repository.UserRepository;
import ru.netology.filecloud.response.ListResponse;
import ru.netology.filecloud.security.JWTUtil;

import java.io.IOException;
import java.util.List;


@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {ClientFileServiceIntegrationTest.Initializer.class})
public class ClientFileServiceIntegrationTest {


    @Autowired
    private UserFilesRepository repository;

    @Autowired
    private UserFileService clientFileService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository clientRepository;


    @Autowired
    private UserService clientService;


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.2")
            .withDatabaseName("serg")
            .withUsername("serg")
            .withPassword("serg")
            .withInitScript("db.sql");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void existClient() {
        String clientLogin = "log";
        boolean isClientExists = clientService.existUser(clientLogin);
        Assertions.assertTrue(isClientExists);
    }

    @Test
    @WithMockUser(username = "log", password = "pass", authorities = "USER1")
    void createNewFile() throws IOException {
        String fileName = "Serg.png";
        byte[] content = new byte[]{(byte) 12345};

//        MockMultipartFile file = new MockMultipartFile(
        CustomMultipart file = new CustomMultipart(
                content,
                fileName);

        boolean isClientFileCreated = clientFileService.createNewFile(fileName, file);
        Assertions.assertTrue(isClientFileCreated);
    }

    @Test
    @WithMockUser(username = "log", password = "pass", authorities = "USER1")
    void existClientFile() {
        String clientLogin = "log";
        String clientFileName = "1.png";
        boolean isExistClientFile = clientFileService.existsUserFile(clientFileName, clientLogin);
        Assertions.assertTrue(isExistClientFile);
    }

    @Test
    @WithMockUser(username = "log", password = "pass", authorities = "USER1")
    void getList() {
        List<ListResponse> getList = clientFileService.getList();
        Assertions.assertFalse(getList.isEmpty());
    }

    @Test
    @WithMockUser(username = "log", password = "pass", authorities = "USER1")
    void readFile() {
        String fileName = "1.png";
        String result = clientFileService.readFile(fileName);
        Assertions.assertNotNull(result);
    }

    @Test
    @WithMockUser(username = "log", password = "pass", authorities = "USER1")
    void changeFileName() {
        String fileName = "2.png";
        String newFileName = "Olga.png";
        File clientFile = clientFileService.changeFileName(fileName, newFileName);
        boolean result = clientFile.getFileName().equals("Olga.png");
        Assertions.assertTrue(result);
    }

    @Test
    @WithMockUser(username = "log", password = "pass", authorities = "USER1")
    void deleteClientFile() throws IOException {
        String clientFileName = "3.png";
        boolean isClientFileDeleted = clientFileService.deleteUserFilesByFileNameAndUser_Login(clientFileName);
        Assertions.assertTrue(isClientFileDeleted);
    }
}
