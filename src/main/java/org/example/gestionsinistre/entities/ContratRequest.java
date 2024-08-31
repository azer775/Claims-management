package org.example.gestionsinistre.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ContratRequest {
    @Enumerated(EnumType.STRING)
    Type type;
    double montant;
    String photo;
    @Enumerated(EnumType.STRING)
    typev typev;
    int nbrdeplace;
    int puissance;
    int valrvenale;
    LocalDate datecirculation;
    String matricule;
    String marque;
    String idun;
}
