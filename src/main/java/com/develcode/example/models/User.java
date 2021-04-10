package com.develcode.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user_")
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @Getter @Setter private Long code;

    @Getter @Setter private String name;

    @Column(name = "birth_date")
    @Getter @Setter private Date birthDate;

    @Lob
    @Getter @Setter private byte[] photo;

    public void mergeAnother(final User other) {
        if (other.getCode() != null) {
            this.code = other.getCode();
        }

        if (other.getName() != null) {
            this.name = other.getName();
        }

        if (other.getBirthDate() != null) {
            this.birthDate = other.getBirthDate();
        }

        if (other.getPhoto() != null) {
            this.photo = other.getPhoto();
        }
    }

}
