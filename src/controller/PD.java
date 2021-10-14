/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Course;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class PD {

    public void pdf(Course c) throws SQLException, FileNotFoundException, DocumentException, BadElementException, IOException {
        try {

            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Public\\Desktop" + randomNum + ".pdf"));
            document.open();
            Paragraph p = new Paragraph(new Phrase("Url  : " + c.getUrl()));
            document.add(p);

            document.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
