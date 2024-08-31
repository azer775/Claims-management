package org.example.gestionsinistre.controllers;

import org.example.gestionsinistre.entities.ChatMessage;
import org.example.gestionsinistre.entities.Sinistre;
import org.example.gestionsinistre.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Ensure all endpoints are under /api to avoid conflicts
@CrossOrigin(origins = "http://localhost:4200")
public class ChatMessageController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // WebSocket endpoint to receive and send messages
    /*@MessageMapping("/chat")
    @SendTo("/topic/messages")
    public void sendMessage( ChatMessage message) {
        System.out.println("Handling send message: " + message);
        // Save the message to the database before broadcasting
        //message.getSinistre().setId(to);
        chatMessageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }*/
    @MessageMapping("/chat/{sinisterId}")
    public void sendMessage(@DestinationVariable int sinisterId, ChatMessage message) {
        System.out.println("Handling send message: " + message + " to sinister: " + sinisterId);
        // Save the message and associate it with the correct sinister
        Sinistre sinistre =new Sinistre();
        sinistre.setId(sinisterId);
        message.setSinistre(sinistre); // Assuming you have a field for this in ChatMessage
        chatMessageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + sinisterId, message);
    }
    // HTTP GET endpoint to fetch old messages
    @GetMapping("/messages")
    public List<ChatMessage> getOldMessages() {
        // Fetch all messages from the database
        return chatMessageRepository.findAll();
    }
    @GetMapping("/mesbysin/{to}")
    public List<ChatMessage> getOldMessagesBysin(@PathVariable int to) {
        // Fetch all messages from the database
        Sinistre s = new Sinistre();
        s.setId(to);
        return chatMessageRepository.findBySinistre(s);
    }
}
