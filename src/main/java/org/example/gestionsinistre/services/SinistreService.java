package org.example.gestionsinistre.services;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.example.gestionsinistre.entities.*;
import org.example.gestionsinistre.repositories.ContratRepository;
import org.example.gestionsinistre.repositories.ExpertRepository;
import org.example.gestionsinistre.repositories.SinistreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class SinistreService {
    @Autowired
    SinistreRepository sr;
    @Autowired
    ContratService cs;
    @Autowired
    AuthentificationService au;
    @Autowired
    ExpertRepository er;

    public Sinistre add(SinistreRequest sinistreRequest){
        Sinistre sinistre;
        Contrat contrat =this.cs.getById(sinistreRequest.getContrat());
        sinistre= Sinistre
                .builder()
                .contrat(contrat)
                .code(generateCode(sinistreRequest.getDate(),contrat.getDegradation()))
                .date(sinistreRequest.getDate())
                .lieu(sinistreRequest.getLieu())
                .vehicule(sinistreRequest.getVehicule())
                .constat(sinistreRequest.getConstat())
                .observation(sinistreRequest.getObservation())
                .etat(Etat.Declare)
                .build();
        return sr.save(sinistre);
    }
    public String generateCode(LocalDate date, Degradation degradation){
        String code;
        code= "S-"+date.getYear()+(degradation.ordinal()+1);
        String lastcode=sr.getlastcode(code);
        if (lastcode==null){
           return code=code+"00000000";
        }
        lastcode=lastcode.replace(code,"");
        lastcode=lastcode.replaceFirst("^0+(?!$)","");
        int n=Integer.parseInt(lastcode)+1;
        String c2=""+n;
        while (c2.length()<8){
            c2="0"+c2;
        }
        return code+c2;


    }

    public List<Sinistre> findbyuser(String jwt) {
        User user= au.getcurrentuser(jwt.replace("Bearer ", ""));
        return this.sr.findbyuser(user);
    }

    public List<Sinistre> getall() {
        return this.sr.findAll();
    }

    public Sinistre getbyid(int id) {
        return this.sr.findById(id).orElse(null);
    }

    public Sinistre rejetersin(int id) {
        Sinistre s = sr.findById(id).orElse(null);
        s.setEtat(Etat.Rejete);
        return sr.save(s);
    }
    public List<Expert> getAllEx(){
        return er.findAll();
    }
    public Sinistre affecterexp(int ids,int ide){
        Sinistre s=sr.findById(ids).orElse(null);
        Expert e=er.findById(ide).orElse(null);
        s.setExpert(e);
        s.setEtat(Etat.Expertise);
        return sr.save(s);
    }

    public Sinistre ajouterexpertise(int id, String s) {
        Sinistre sinistre=sr.findById(id).orElse(null);
        sinistre.setExpertise(s);
        sr.save(sinistre);
        return sinistre;
    }

    public Sinistre reglementation(int id) {
        Sinistre s=sr.findById(id).orElse(null);
        s.setEtat(Etat.Reglementation);
        return sr.save(s);
    }
    public List<Sinistre> getbydegradation(String token){
        User user= au.getcurrentuser(token.replace("Bearer ", ""));
        return sr.findbysin(user.getDegradation());
    }
    public ByteArrayInputStream generatePdf() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        // Title
        Paragraph title = new Paragraph("Reçu du frais huissier notaire")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph("Reçu émis par : AMI ASSURANCES")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(subtitle);

        document.add(new Paragraph("\n"));

        // Table
        float[] columnWidths = {1, 3};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        table.addHeaderCell(new Cell().add(new Paragraph("Assuré/Souscripteur :")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("TOUATI FAOUZI")));

        table.addCell(new Cell().add(new Paragraph("Numéro du contrat :")));
        table.addCell(new Cell().add(new Paragraph("20175000092635")));

        table.addCell(new Cell().add(new Paragraph("AGENCE")));
        table.addCell(new Cell().add(new Paragraph("626")));

        table.addCell(new Cell().add(new Paragraph("Quittance")));
        table.addCell(new Cell().add(new Paragraph("7008939")));

        table.addCell(new Cell().add(new Paragraph("Date Règlement :")));
        table.addCell(new Cell().add(new Paragraph("30/01/2023")));

        table.addCell(new Cell().add(new Paragraph("Échéance/Terme :")));
        table.addCell(new Cell().add(new Paragraph("21/11/2021")));

        document.add(table);

        document.add(new Paragraph("\n"));

        Paragraph footer = new Paragraph("On reconnaît avoir reçu de TOUATI FAOUZI le montant suivant : 120 DT : Frais de la sommation huissier notaire.")
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(12);
        document.add(footer);

        document.add(new Paragraph("\n\n"));

        Paragraph signature = new Paragraph("P/ASSURANCES « AMI »")
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(12);
        document.add(signature);
//        Image image;
//        image.s
//        document.add()
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
