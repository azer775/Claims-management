package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByNom(String nom);
}
