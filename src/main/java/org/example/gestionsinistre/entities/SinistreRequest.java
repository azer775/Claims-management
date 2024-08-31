package org.example.gestionsinistre.entities;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class SinistreRequest {
    LocalDate date;
    String observation;
    int contrat;
    String lieu;
    String vehicule;
    String constat;
}
