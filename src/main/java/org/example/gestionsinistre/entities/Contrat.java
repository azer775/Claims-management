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
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Enumerated(EnumType.STRING)
    Type type;
    LocalDate datecreation;
    @ManyToOne
    User user;
    @OneToOne(cascade = CascadeType.ALL)
    Vehicule vehicule;
    double montant;
    @Enumerated(EnumType.STRING)
    Degradation degradation;
    int Agence;

}
