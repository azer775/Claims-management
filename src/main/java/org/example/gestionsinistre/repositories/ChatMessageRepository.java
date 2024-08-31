package org.example.gestionsinistre.repositories;

import org.example.gestionsinistre.entities.ChatMessage;
import org.example.gestionsinistre.entities.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Integer> {
     List<ChatMessage> findBySinistre(Sinistre sinistre);
}
