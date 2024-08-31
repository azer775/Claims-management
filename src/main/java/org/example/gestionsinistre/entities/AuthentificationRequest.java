package org.example.gestionsinistre.entities;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthentificationRequest {
    //@Size(min = 4,message = "pwd min 4")
    private String pwd;
    //@NotEmpty(message = "nom obligatoire")
    @Email
    private String email;
}
