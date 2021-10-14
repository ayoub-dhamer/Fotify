/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Course;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class WebviewController implements Initializable {

    @FXML
    private WebView wb;
    @FXML
    private Button back;
    @FXML
    private Pane pane;
    @FXML
    private Button tele;
    Course c = new Course();

    @FXML
    void back() {
        back.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
                Scene scene = new Scene(type);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fotify");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
            pane.getChildren().add(n);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tele.setOnMouseClicked(event -> {

            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/test.fxml"));
                Scene scene = new Scene(type);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fotify");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
            }

            /* FileChooser filechooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File (*.html)", "*.html");
            FileChooser.ExtensionFilter eextFilte = new FileChooser.ExtensionFilter("File (*.txt)", "*.txt");

            filechooser.getExtensionFilters().add(extFilter);
            filechooser.getExtensionFilters().add(eextFilte);
            File file = filechooser.showSaveDialog(null);

            String stringhtml = c.getUrl();

            if (file != null) {
                saveFile(stringhtml, file);
                String tit = "Cours a été téléchargé avec succés";
                String message = "Profitez votre cours ";
                NotificationType notification = NotificationType.SUCCESS;
                TrayNotification tray = new TrayNotification(tit, message, notification);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(javafx.util.Duration.seconds(4));
            }*/
        });

    }

    void setIdd(String x) {
        WebEngine engine = wb.getEngine();
        engine.load(x);
    }
}
