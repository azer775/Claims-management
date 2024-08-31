package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.Degradation;
import org.example.gestionsinistre.entities.Sinistre;
import org.example.gestionsinistre.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinistreRepository extends JpaRepository<Sinistre,Integer> {
    @Query("select s.code from Sinistre s where s.code like %:code% order by s.id DESC LIMIT 1")
    String getlastcode(@Param("code") String code);
    @Query("select s from Sinistre s where s.contrat.user=:user")
    List<Sinistre> findbyuser(@Param("user") User user);
    @Query("select s from Sinistre s where s.contrat.degradation= :d")
    List<Sinistre> findbysin(@Param("d") Degradation d);
}
