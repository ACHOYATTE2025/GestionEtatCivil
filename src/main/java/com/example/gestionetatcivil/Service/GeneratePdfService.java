package com.example.gestionetatcivil.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@AllArgsConstructor
@Getter
public class GeneratePdfService {
    private ExtraitService extraitService;
    @Autowired
    private GlobalConfig globalConfig;

    public byte[] generatepdf() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
      // ExtraitNaissance extraitX = (ExtraitNaissance) this.extraitRepository.findByNumeroExtrait("ac");
       // System.out.println(this.globalConfig.getDocumentLu().get());
        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Extrait Professionnel  "+ this.globalConfig.getDocumentLu().get().getNumeroExtrait()));
            document.add(new Paragraph("Nom: " +  this.globalConfig.getDocumentLu().get().getNom()) );
           
            

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }


}

