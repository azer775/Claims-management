package org.example.gestionsinistre.TestMock;

import org.example.gestionsinistre.entities.Role;
import org.example.gestionsinistre.repositories.RoleRepository;
import org.example.gestionsinistre.services.AuthentificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SinTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthentificationService authentificationService;

    @Test
    public void test(){
        Role testrole = Role.builder()
                .id(1)
                .nom("client1")
                .build();
        
    }
}
