package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.Quittance;
import org.example.gestionsinistre.entities.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuittanceRepository  extends JpaRepository<Quittance,Integer> {
    List<Quittance> findBySinistre(Sinistre s);
}
