package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByEmailAndPwd(String email,String pwd);
    User findByIdun(String idun);

}
