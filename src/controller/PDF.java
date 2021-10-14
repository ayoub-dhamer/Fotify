/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Event;
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
public class PDF {

    public void pdf(Event p) throws SQLException, FileNotFoundException, DocumentException, BadElementException, IOException {
        try {
            
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Public\\Desktop" + randomNum + ".pdf"));
            document.open();
            Image img = Image.getInstance("http://127.0.0.1/doc/" + p.getImage());
            img.setWidthPercentage(20);
            Paragraph adrr = new Paragraph(new Phrase("l Titre  : " + p.getTitre(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
            Paragraph adrr1 = new Paragraph(new Phrase("l Contenu  : " + p.getContenu(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
            Paragraph par = new Paragraph(" Votre Evenement  ", FontFactory.getFont(FontFactory.TIMES));
            par.setAlignment(Element.ALIGN_CENTER);
            document.add(par);

            document.add(img);

            document.add(adrr);
            document.add(adrr1);
            document.add(new Paragraph("Date dajout de l'evenement  : " + p.getDateajout(), FontFactory.getFont(FontFactory.TIMES)));
            document.add(new Paragraph("Date modification de l l'evenement : " + p.getDatemodif(), FontFactory.getFont(FontFactory.TIMES)));

            document.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
