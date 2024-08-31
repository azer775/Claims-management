package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.Contrat;
import org.example.gestionsinistre.entities.Degradation;
import org.example.gestionsinistre.entities.Role;
import org.example.gestionsinistre.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat,Integer> {
List<Contrat> findByUser(User user);
@Query("select c from Contrat c where c.user.degradation = :d")
List<Contrat> findbydelegation(@Param("d") Degradation d);


}
