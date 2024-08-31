package org.example.gestionsinistre;

import org.example.gestionsinistre.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.example.gestionsinistre.repositories.RoleRepository;
@SpringBootApplication
@EnableAsync
public class GestionsinistreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionsinistreApplication.class, args);}
        @Bean
        public CommandLineRunner runner(RoleRepository roleRepository) {
            return args -> {
                if (roleRepository.findByNom("client")==null) {
                    roleRepository.save(Role.builder().nom("client").build());
                }
                if (roleRepository.findByNom("agent")==null){
                    roleRepository.save(Role.builder().nom("agent").build());
                }
                if (roleRepository.findByNom("admin")==null){
                    roleRepository.save(Role.builder().nom("admin").build());
                }
            };
        }
    }


