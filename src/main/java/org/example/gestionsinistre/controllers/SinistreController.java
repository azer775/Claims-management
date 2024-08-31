package org.example.gestionsinistre.controllers;

import org.example.gestionsinistre.entities.Expert;
import org.example.gestionsinistre.entities.Sinistre;
import org.example.gestionsinistre.entities.SinistreRequest;
import org.example.gestionsinistre.services.SinistreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("sinistre")
@CrossOrigin("*")
public class SinistreController {
    @Autowired
    SinistreService ss;
    @PostMapping("add")
    public Sinistre addSinistre(
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "observation", required = true) String observation,
            @RequestParam(value = "contrat", required = true) int contratId,
            @RequestParam(value = "lieu", required = true) String lieu,
            @RequestParam(value = "vehicule", required = false) MultipartFile vehicule,
            @RequestParam(value = "constat", required = false) MultipartFile constat) {
        SinistreRequest sinistreRequest =new SinistreRequest();
        sinistreRequest.setObservation(observation);
        sinistreRequest.setContrat(contratId);
        sinistreRequest.setDate(LocalDate.now());
        sinistreRequest.setLieu(lieu);
        try {
            // Process file upload
            if (vehicule != null) {
                sinistreRequest.setVehicule(saveFile(vehicule, "vehicule"));
                System.out.println("file is not null");
            }
            if (constat != null) {
                sinistreRequest.setConstat(saveFile(constat, "constat"));
                System.out.println("file is not null");
            }



            return ss.add(sinistreRequest);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
            return null; // or throw an exception, depending on your error handling strategy
        }
    }
    @GetMapping("byu")
    public List<Sinistre> findbyuser(@RequestHeader(value = "Authorization") String jwt){
        return this.ss.findbyuser(jwt);
    }
    @GetMapping("all")
    public List<Sinistre> findall(){
        return this.ss.getall();
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
    @GetMapping("byid/{id}")
    public Sinistre getbyid(@PathVariable("id") int id){
        return this.ss.getbyid(id);
    }

    private String generateUniqueFileName(String prefix, String extension) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);
        return prefix + "_" + formattedDateTime + extension;
    }
    @PutMapping("rejeter/{id}")
    public Sinistre Rejeter(@PathVariable("id")int id){

        return ss.rejetersin(id);
    }
    @GetMapping("allex")
    public List<Expert> getallex(){
        return ss.getAllEx();
    }
    @PutMapping("affecter/{id}/{ide}")
    public Sinistre affecter(@PathVariable("id") int id,@PathVariable("ide")int ide){
        return ss.affecterexp(id,ide);

    }
    @PutMapping("addexpertise/{id}")
    public Sinistre ajouterexpertise(@RequestParam("file") MultipartFile file,@PathVariable("id")int id) throws IOException {
            String s="";
        if (file != null) {
            s=saveFile(file, "expertise");
            System.out.println("file is not null");

        }
        return ss.ajouterexpertise(id,s);
    }
    @PutMapping("reglementation/{id}")
    public Sinistre reglementation(@PathVariable("id")int id){
        return ss.reglementation(id);
    }
    @GetMapping("bydel")
    public List<Sinistre> getbydel(@RequestHeader(value = "Authorization") String jwt){
        return ss.getbydegradation(jwt);
    }
    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf() throws IOException {
        ByteArrayInputStream bis = ss.generatePdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recu.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
