package org.example.gestionsinistre.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Sinistre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDate date;
    String observation;
    String code;
    @ManyToOne
    Contrat contrat;
    String lieu;
    String vehicule;
    String expertise;
    String constat;
    String vreparer;
    String devis;
    @Enumerated(EnumType.STRING)
    Etat etat;
    @ManyToOne
    Expert expert;


}
