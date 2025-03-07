package com.example.gestionetatcivil.Service;

import org.springframework.stereotype.Service;

import com.example.gestionetatcivil.Entities.ExtraitNaissance;
import com.example.gestionetatcivil.Repositories.ExtraitRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class GeneratePdfService {
    private ExtraitService extraitService;
    private ExtraitRepository extraitRepository;
  
    

    //
    public byte[] generatepdf() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
       ExtraitNaissance extraitX = (ExtraitNaissance) this.extraitRepository.findByNumeroExtrait("ac");

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Extrait Professionnel"));
            document.add(new Paragraph("Nom: " + ""));
           
            

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }


}

