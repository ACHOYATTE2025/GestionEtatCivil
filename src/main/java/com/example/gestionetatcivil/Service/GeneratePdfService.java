package com.example.birthadvance.Service;
/*
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD;
import static com.itextpdf.kernel.colors.DeviceRgb.RED;

@Service
@AllArgsConstructor
public class GeneratePdfService {

    //
    public byte[] generatePdf() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        // Ajouter une image
        String imagePath = "src/main/resources/sample-image.png";
        Image img = new Image(ImageDataFactory.create(imagePath));
        document.add(img);

        // Ajouter un texte stylisé
        Font font = Font.getFont(HELVETICA_BOLD);

        Paragraph paragraph = new Paragraph();
        paragraph.add("Ce document contient une image, une table et du texte stylisé.");
        paragraph.setFontColor(RED);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);

        // Ajouter une table
        float[] pointColumnWidths = {150F, 150F, 150F};
        Table table = new Table(pointColumnWidths);
        table.addCell(new Cell().add(new Paragraph("Colonne 1")));
        table.addCell(new Cell().add(new Paragraph("Colonne 2")));
        table.addCell(new Cell().add(new Paragraph("Colonne 3")));
        table.addCell(new Cell().add(new Paragraph("Donnée 1")));
        table.addCell(new Cell().add(new Paragraph("Donnée 2")));
        table.addCell(new Cell().add(new Paragraph("Donnée 3")));
        document.add(table);

        // Fermer le document
        document.close();

        return byteArrayOutputStream.toByteArray();
    }


}
*/