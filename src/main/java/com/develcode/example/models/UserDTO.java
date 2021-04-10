package com.develcode.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Date;

@AllArgsConstructor
public class UserDTO {

    @Getter @Setter private Long code;
    @Getter @Setter private String name;
    @Getter @Setter @JsonFormat(pattern = "yyyy-mm-dd") private Date birthDate;
    @Getter @Setter private String imageBase64;

    public User parseDTO() {
        byte[] decodedPhoto = imageBase64 != null ? Base64.decodeBase64(imageBase64.getBytes()) : null;
        return new User(code, name, birthDate, decodedPhoto);
    }

    public static UserDTO parseOriginal(User user) {
        return new UserDTO(user.getCode(), user.getName(), user.getBirthDate(), Base64.encodeBase64String(user.getPhoto()));
    }

}
