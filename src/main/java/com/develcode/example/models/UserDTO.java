package com.develcode.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class UserDTO {

    @Getter @Setter 
    @NotNull
    private Long code;

    @Getter @Setter
    @NotNull @NotEmpty
    private String name;

    @Getter @Setter @JsonFormat(pattern = "yyyy-mm-dd") 
    private Date birthDate;

    @Getter @Setter 
    private String imageBase64;

    public User parseDTO() {
        // convert the base64 encoded image that comes from the POST request to a byte array
        byte[] decodedPhoto = imageBase64 != null ? Base64.decodeBase64(imageBase64.getBytes()) : null;
        return new User(code, name, birthDate, decodedPhoto);
    }

    public static UserDTO parseOriginal(User user) {
        return new UserDTO(user.getCode(), user.getName(), user.getBirthDate(), Base64.encodeBase64String(user.getPhoto()));
    }

}
