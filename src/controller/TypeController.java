/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class TypeController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private Button btnsave;

    @FXML
    private HTMLEditor htmleditor;

    @FXML
    private TextArea txthtml;

    @FXML
    private Button btnsave1;

    @FXML
    private Button btnback;

    @FXML
    private Button btnload;

    private SimpleStringProperty s;

    @FXML
    void back() {
        btnback.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/FXML.fxml"));
                Scene scene = new Scene(type);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    @FXML
    void html(ActionEvent event) {
        txthtml.setText(htmleditor.getHtmlText());

    }

    @FXML
    void save() {
        String stringhtml = htmleditor.getHtmlText();
        txthtml.setText(stringhtml);
        FileChooser filechooser = new FileChooser();

        FileChooser.ExtensionFilter extFilte = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");

        filechooser.getExtensionFilters().add(extFilte);
        File file = filechooser.showSaveDialog(null);
        if (file != null) {
            saveFile(stringhtml, file);
        }

    }

    private void pdf() {

        FileChooser r = new FileChooser();
        r.setTitle("Open PDF file...");
        r.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        btnsave.setOnMouseClicked(event -> {
            File h = r.showSaveDialog(null);
            if (h != null) {
                String filename = h.getAbsolutePath();
                try {
                    FileOutputStream fos = new FileOutputStream(filename);
                    PDF pdf = new PDF(fos);
                    Page page = new Page(pdf, Letter.LANDSCAPE);
                    pdf.close();
                    fos.flush();

                } catch (FileNotFoundException ex) {

                } catch (Exception ex) {
                    Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }

    @FXML
    void save1(ActionEvent event) {
        String stringhtml = htmleditor.getHtmlText();
        txthtml.setText(stringhtml);
        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");

        filechooser.getExtensionFilters().add(extFilter);

        File file = filechooser.showSaveDialog(null);
        if (file != null) {
            saveFile(stringhtml, file);
        }

    }

    private void saveFile(String content, File file) {
        FileWriter filewriter = null;
        try {

            filewriter = new FileWriter(file);
            filewriter.write(content);
            filewriter.close();

        } catch (IOException ex) {

        }
    }

    @FXML
    void load(ActionEvent event) {
        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        FileChooser.ExtensionFilter extFilte = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        filechooser.getExtensionFilters().add(extFilter);
        filechooser.getExtensionFilters().add(extFilte);
        File file = filechooser.showOpenDialog(null);
        if (file != null) {
            String textread = readFile(file);
            htmleditor.setHtmlText(textread);
            txthtml.setText(textread);
        }

    }

    private String readFile(File file) {
        StringBuilder stribuilder = new StringBuilder();
        BufferedReader bufferedreader = null;
        try {
            bufferedreader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedreader.readLine()) != null) {
                stribuilder.append(text);
            }

        } catch (Exception ex) {

        } finally {
            try {
                bufferedreader.close();
            } catch (Exception e) {

            }
        }

        return stribuilder.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s = new SimpleStringProperty();
        s.set("Write your text here");
        htmleditor.setHtmlText(s.get());

    }

}
