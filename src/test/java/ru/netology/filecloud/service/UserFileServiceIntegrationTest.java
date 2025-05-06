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
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.filecloud.entity.File;
import ru.netology.filecloud.repository.UsersFilesRepository;
import ru.netology.filecloud.repository.UsersRepository;
import ru.netology.filecloud.response.ListResponse;
import ru.netology.filecloud.security.JWTUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {UserFileServiceIntegrationTest.Initializer.class})
public class UserFileServiceIntegrationTest {


    @Autowired
    private UsersFilesRepository repository;

    @Autowired
    private UsersFileService userFileService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UsersRepository userRepository;


    @Autowired
    private UsersService userService;


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.2")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
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
        String clientLogin = "user";
        boolean isClientExists = userService.existUser(clientLogin);
        Assertions.assertTrue(isClientExists);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    void createNewFile() throws IOException {
        String fileName = "TestTestTest.jpg";
        byte[] content = new byte[]{(byte) 12345};


        boolean isClientFileCreated = userFileService.createNewFile(fileName, new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return content.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return content;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(java.io.File dest) throws IOException, IllegalStateException {

            }
        });
        Assertions.assertTrue(isClientFileCreated);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    void existUserFile() {
        String userLogin = "user";
        String userFileName = "TestFile1.jpg";
        boolean isExistUserFile = userFileService.existsUserFile(userFileName, userLogin);
        Assertions.assertTrue(isExistUserFile);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    void getList() {
        List<ListResponse> getList = userFileService.getList();
        Assertions.assertFalse(getList.isEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    void readFile() {
        String fileName = "TestFile1.jpg";
        String result = userFileService.readFile(fileName);
        Assertions.assertNotNull(result);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    void changeFileName() {
        String fileName = "TestFile1.jpg";
        String newFileName = "NewTestFile2.jpg";
        File userFile = userFileService.changeFileName(fileName, newFileName);
        boolean result = userFile.getFileName().equals("NewTestFile1.jpg");
        Assertions.assertTrue(result);
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = "USER")
    void deleteClientFile() throws IOException {
        String clientFileName = "TestTestTest.jpg";
        boolean isClientFileDeleted = userFileService.deleteUserFilesByFileNameAndUser_Login(clientFileName);
        Assertions.assertTrue(isClientFileDeleted);
    }
}
