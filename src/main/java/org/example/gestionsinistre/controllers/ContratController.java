package org.example.gestionsinistre.controllers;

import jakarta.websocket.server.PathParam;
import org.example.gestionsinistre.entities.*;
import org.example.gestionsinistre.services.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("contrat")
public class ContratController {
    @Autowired
    ContratService cs;
    @PostMapping("save")
    public Contrat save(@RequestBody ContratRequest contratRequest){
        return this.cs.add(contratRequest);
    }
    @PostMapping("/upload")
    public Contrat uploadFiles(
            @RequestParam(value = "type", required = true) Type type,
            @RequestParam(value = "montant", required = true) double montant,
            @RequestParam(value = "photo", required = true) MultipartFile photo,
            @RequestParam(value = "typev", required = true) typev typev,
            @RequestParam(value = "nbrdeplace", required = true) int nbrdeplace,
            @RequestParam(value = "puissance", required = true) int puissance,
            @RequestParam(value = "valrvenale", required = true) int valrvenale,
            @RequestParam(value = "datecirculation", required = true) LocalDate datecirculation,
            @RequestParam(value = "matricule", required = true) String matricule,
            @RequestParam(value = "marque", required = true) String marque,
            @RequestParam(value = "idun", required = true) String idun
    ) {
        ContratRequest contratRequest = new ContratRequest();
        contratRequest.setType(type);
        contratRequest.setMontant(montant);
        contratRequest.setTypev(typev);
        contratRequest.setNbrdeplace(nbrdeplace);
        contratRequest.setPuissance(puissance);
        contratRequest.setValrvenale(valrvenale);
        contratRequest.setDatecirculation(datecirculation);
        contratRequest.setMatricule(matricule);
        contratRequest.setMarque(marque);
        contratRequest.setIdun(idun);

        try {
            // Process file upload
            if (photo != null) {
                contratRequest.setPhoto(saveFile(photo, "photo"));
                System.out.println("file is not null");
            }

            // Save or process the contratRequest object as needed
            // Example service method call:
            // contratRequestService.saveContratRequest(contratRequest);

            return cs.add(contratRequest);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
            return null; // or throw an exception, depending on your error handling strategy
        }
    }
    public String saveFile(MultipartFile file, String prefix) throws IOException {
        String destinationDirectory="C:/xampp/htdocs/data/";
        String savedpath="http://localhost/data/";
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = generateUniqueFileName(prefix, extension);
        File destFile = new File(destinationDirectory + File.separator + newFileName);
        file.transferTo(destFile);
        return savedpath+newFileName;
    }

    private String generateUniqueFileName(String prefix, String extension) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);
        return prefix + "_" + formattedDateTime + extension;
    }
    @GetMapping("all")
    private List<Contrat> getAll(){
        return cs.getall();
    }
    @GetMapping("byu")
    private List<Contrat> getByuser(@RequestHeader(value = "Authorization") String auth){
        return cs.getbyuser(auth);
    }
    @GetMapping("byid/{id}")
    private Contrat getbyid(@PathVariable("id") int id){
        return cs.getById(id);
    }
    @GetMapping("bydel")
    private List<Contrat> bydel(@RequestHeader(value = "Authorization") String auth){
        return cs.getbydelegation(auth);
    }
    @PostMapping("add/{id}")
    public Quittance add(@PathVariable int id, @RequestBody Quittance q){
        System.out.println("q1=  "+q);
        return q;

    }
}
