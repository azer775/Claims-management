package org.example.gestionsinistre.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {
   // @NotEmpty(message = "nom obligatoire")
    private String nom;
    //@NotEmpty(message = "nom obligatoire")
    private String prenom;
    //@NotEmpty(message = "nom obligatoire")
    private String idun;
    //@NotEmpty(message = "nom obligatoire")
    //@Size(min = 4,message = "pwd min 4")
    private String pwd;
    //@NotEmpty(message = "nom obligatoire")
    @Email
    private String email;
    private LocalDate datenais;
}
