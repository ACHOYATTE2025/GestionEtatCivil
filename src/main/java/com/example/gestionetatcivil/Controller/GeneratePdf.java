package com.example.birthadvance.Controller;
/*
import com.example.birthadvance.Service.GeneratePdfService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class GeneratePdf {
    private GeneratePdfService generatePdfService;


    // generer pdf

    @GetMapping(path = "genertepdf")
    public ResponseEntity<byte[]> generatePdf() throws IOException {
        // Appeler le service pour générer le PDF
        byte[] pdfContent = generatePdfService.generatePdf();

        // Configurer les en-têtes de la réponse pour indiquer qu'il s'agit d'un fichier PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "document.pdf");

        // Retourner le fichier PDF en tant que réponse
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }


}
*/