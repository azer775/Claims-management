package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.Contrat;
import org.example.gestionsinistre.entities.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert,Integer> {
}
