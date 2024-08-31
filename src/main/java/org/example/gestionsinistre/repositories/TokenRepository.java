package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.Role;
import org.example.gestionsinistre.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    Token findByToken(String token);
}
