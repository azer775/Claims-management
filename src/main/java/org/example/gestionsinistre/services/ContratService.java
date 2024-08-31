package org.example.gestionsinistre.services;

import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import org.example.gestionsinistre.entities.Contrat;
import org.example.gestionsinistre.entities.ContratRequest;
import org.example.gestionsinistre.entities.User;
import org.example.gestionsinistre.entities.Vehicule;
import org.example.gestionsinistre.repositories.ContratRepository;
import org.example.gestionsinistre.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratService {
    @Autowired
    ContratRepository cr;
    @Autowired
    UserRepository ur;
    @Autowired
    AuthentificationService au;
    public Contrat add(ContratRequest contratRequest){
        Contrat contrat = new Contrat();
        Vehicule vehicule = new Vehicule();
        System.out.println(contratRequest.getPhoto());
        vehicule=Vehicule.builder()
                .photo(contratRequest.getPhoto())
               // .usage(contratRequest.getUsage())
                .typev(contratRequest.getTypev())
                .datecirculation(contratRequest.getDatecirculation())
                .marque(contratRequest.getMarque())
                .matricule(contratRequest.getMatricule())
                .nbrdeplace(contratRequest.getNbrdeplace())
                .puissance(contratRequest.getPuissance())
                .valrvenale(contratRequest.getValrvenale())
                .build();
        contrat=Contrat.builder()
                .datecreation(LocalDate.now())
                .montant(contratRequest.getMontant())
                .vehicule(vehicule)
                .user(ur.findByIdun(contratRequest.getIdun()))
                .degradation(ur.findByIdun(contratRequest.getIdun()).getDegradation())
                .build();
        return cr.save(contrat);
    }
    public List<Contrat> getall(){
        return cr.findAll();
    }
    public List<Contrat> getbyuser(String token){
        User user= au.getcurrentuser(token.replace("Bearer ", ""));
        return cr.findByUser(user);

    }
    public Contrat getById(int id){
        return cr.findById(id).orElse(null);
    }
    public List<Contrat> getbydelegation(String jwt){
        User user= au.getcurrentuser(jwt.replace("Bearer ", ""));
        return cr.findbydelegation(user.getDegradation());
    }
}
