package org.example.gestionsinistre.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Expert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String nom;
    String prenom;
    String adresse;
}
