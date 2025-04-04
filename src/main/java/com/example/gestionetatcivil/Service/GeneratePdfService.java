package com.example.gestionetatcivil.Service;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.gestionetatcivil.Entities.Account;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@Setter
@Getter
public class GeneratePdfService {
  
    private final ExtraitService extraitService;
    private final GlobalConfig globalConfig;
   
    Account subscriber;

    public GeneratePdfService(ExtraitService extraitService,GlobalConfig globalConfig){
        this.extraitService=extraitService;
        this.globalConfig = globalConfig;
        
    }

    public byte[] generatepdf() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.out.println(this.globalConfig.getDocumentLu().get().getDateNaissance());
        try {;
            Account subscriber= (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long ux = subscriber.getId();
            ByteArrayOutputStream Out = new ByteArrayOutputStream(); 
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Ajout de l'armoirie
            try {
                ClassPathResource armoirieResource = new ClassPathResource("static/armoirie.png");
                byte[] imageBytes = armoirieResource.getInputStream().readAllBytes();
                ImageData imageData = ImageDataFactory.create(imageBytes);
                Image armoirie = new Image(imageData).setWidth(80).setHeight(80);
                document.add(armoirie);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Devise nationale
            document.add(new Paragraph("UNION - DISCIPLINE - TRAVAIL")
            .simulateBold().setFontSize(10)
            .setTextAlignment(TextAlignment.RIGHT))
            
            ;

             // Devise nationale
             document.add(new Paragraph("DISTRICT-ABIDJAN")
             .simulateBold().setFontSize(10)
             .setTextAlignment(TextAlignment.LEFT))
             .setBottomMargin(0);



            // EN-TÊTE
            document.add(new Paragraph("RÉPUBLIQUE DE CÔTE D’IVOIRE").simulateBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("EXTRAIT DE NAISSANCE").simulateBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));
           
           


            document.add(new Paragraph("Numero Extrait "+ this.globalConfig.getDocumentLu().get().getNumeroExtrait()))
            .setTextAlignment(TextAlignment.CENTER);
           
            // TABLEAU STRUCTURÉ
            Table table = new Table(2).setTextAlignment(TextAlignment.LEFT);
            table.setWidth(500);
            
            table.addCell("Nom :");
            table.addCell(this.globalConfig.getDocumentLu().get().getNom());

            table.addCell("Prénom :");
            table.addCell(this.globalConfig.getDocumentLu().get().getPrenoms());

            table.addCell("Lieu de Naissance :");
            table.addCell(this.globalConfig.getDocumentLu().get().getLieuNaissance());


            table.addCell("Situation Matrimoniale :");
            table.addCell(this.globalConfig.getDocumentLu().get().getSituationMatrimoniale());

           /* table.addCell("Nom de la Mère :");
            table.addCell(this.globalConfig.getDocumentLu().get().getNomMere());
            document.add(new Paragraph("\n"));*/

            document.add(table);

           

            

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }


}

