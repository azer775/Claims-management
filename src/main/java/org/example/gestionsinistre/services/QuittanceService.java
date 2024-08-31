package org.example.gestionsinistre.services;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.example.gestionsinistre.entities.Quittance;
import org.example.gestionsinistre.entities.Sinistre;
import org.example.gestionsinistre.repositories.QuittanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class QuittanceService {
    @Autowired
    QuittanceRepository qr;
    @Autowired
    SinistreService sr;
    public Quittance add(Quittance q,int id){
        System.out.println(q);
        Sinistre s = sr.getbyid(id);
        q.setSinistre(s);
        return qr.save(q);
    }
    public List<Quittance> getall(){
        return qr.findAll();
    }
    public List<Quittance> getbysin(int id){
        Sinistre s = sr.getbyid(id);
        return qr.findBySinistre(s);
    }
    public ByteArrayInputStream generatePdf(int id) throws IOException {
        Quittance q = qr.findById(id).orElse(null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 20, 20, 20);

        // Title
        Paragraph title = new Paragraph("Reçu du frais "+q.getBut())
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
        table.addHeaderCell(new Cell().add(new Paragraph(q.getSinistre().getContrat().getUser().getNom()+" "+q.getSinistre().getContrat().getUser().getPrenom())));

        table.addCell(new Cell().add(new Paragraph("Numéro du contrat :")));
        table.addCell(new Cell().add(new Paragraph(""+q.getSinistre().getContrat().getId())));

        table.addCell(new Cell().add(new Paragraph("AGENCE")));
        table.addCell(new Cell().add(new Paragraph("626")));

        table.addCell(new Cell().add(new Paragraph("Quittance")));
        table.addCell(new Cell().add(new Paragraph(""+q.getId())));

        table.addCell(new Cell().add(new Paragraph("Date Règlement :")));
        table.addCell(new Cell().add(new Paragraph(q.getDateReg().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))));

        table.addCell(new Cell().add(new Paragraph("Échéance/Terme :")));
        if (q.getEcheance()!=null) {
            table.addCell(new Cell().add(new Paragraph(q.getEcheance().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))));
        }else {
            table.addCell(new Cell().add(new Paragraph("non payée")));
        }
        document.add(table);

        document.add(new Paragraph("\n"));

        Paragraph footer = new Paragraph("On reconnaît avoir reçu de" +q.getSinistre().getContrat().getUser().getNom()+" "+q.getSinistre().getContrat().getUser().getPrenom()+ " le montant suivant : "+q.getMontant() + " : Frais de la sommation" +q.getBut())
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
