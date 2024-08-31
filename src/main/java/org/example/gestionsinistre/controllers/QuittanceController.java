package org.example.gestionsinistre.controllers;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.example.gestionsinistre.entities.Quittance;
import org.example.gestionsinistre.services.QuittanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("quittance")
public class QuittanceController {
    @Autowired
    QuittanceService qs;

    @PostMapping("add/{id}")
    public Quittance add(@PathVariable int id, @RequestBody  Quittance q){
        System.out.println("q1=  "+q);
        return qs.add(q,id);

    }
    @GetMapping("all")
    public List<Quittance> getall(){
        return qs.getall();
    }
    @GetMapping("bysin/{id}")
    public List<Quittance> getall(@PathVariable("id") int id){
        return qs.getbysin(id);
    }

    @GetMapping(value = "generate/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable int id) throws IOException {
        ByteArrayInputStream bis = qs.generatePdf(id);

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
