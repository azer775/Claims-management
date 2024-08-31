package org.example.gestionsinistre.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthentificationResponse {
    String token;
    String message;

    public AuthentificationResponse(String message) {
        this.message = message;
    }
}
