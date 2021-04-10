package com.develcode.example;

import com.develcode.example.models.UserDTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleApplicationTests {

    @Autowired
    private TestRestTemplate testTemplate;


    @Test
    public void testPost() throws Exception {
        var imagePath = Paths.get(this.getClass().getClassLoader().getResource("suporte-ao-cliente.png").toURI());
        var image = Files.readAllBytes(imagePath);
        var userDTO = new UserDTO(234L, "Tom", Date.valueOf("1995-04-21"), Base64.encodeBase64String(image));

        var resEntity =  testTemplate.postForEntity("/api/users", userDTO, UserDTO.class);
        Assertions.assertSame(resEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test @Sql({"/import.sql"})
    public void testGet() throws Exception {
        var userEntity = testTemplate.getForEntity("/api/users/123", UserDTO.class);
        Assertions.assertSame(userEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(userEntity.getBody());
    }

}

