package org.example.gestionsinistre.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Quittance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDate DateReg;
    float montant;
    String but;
    LocalDate echeance;
    @Enumerated(EnumType.STRING)
    Statue status;
    @ManyToOne
    Sinistre sinistre;
}
