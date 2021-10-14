/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PhotoDAO;
import dao.UserDAO;
import entity.Maptet;
import entity.Photo;
import utils.Upload;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 */
public class AjouterController implements Initializable {

    @FXML
    private ImageView phv;
    @FXML
    private TextField tfTitre;
    @FXML
    private TextField tfTheme;
    @FXML
    private TextField tfCouleur;
    @FXML
    private TextField tfLocalisation;
    @FXML
    private Button addAll;
    @FXML
    private Button addPH;
    @FXML
    private Button btnProfil;

    private FileChooser filechooser = new FileChooser();
    public String url;
    public String path;
    public int Id_membre = 1;

    private File file;
    String pic;
    @FXML
    private Pane pane;
    @FXML
    private Label fotify;
    @FXML
    private Pane mp;
    @FXML
    private Button loc;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
            pane.getChildren().add(n);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }



        loc.setOnAction(e -> {

            String s = tfLocalisation.getText();
            String[] spString = s.split(",");
            Double a = Double.parseDouble(spString[0]);
            Double b = Double.parseDouble(spString[1]);
            Maptet mp = new Maptet(a, b);
        });
        //fonction du bouton d'ajout des photos     
        addAll.setOnAction(e -> {
            if (tfTitre.getText().isEmpty() || tfCouleur.getText().isEmpty() || tfLocalisation.getText().isEmpty() || tfTheme.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.NONE, "Erreur de champs", ButtonType.OK);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez remplir les champs vides!");
                alert.showAndWait();
            } else if (phv.getImage() == null) {
                Alert alert = new Alert(AlertType.NONE, "Erreur d'image", ButtonType.OK);
                alert.setTitle("Erreur d'image");
                alert.setContentText("Veuillez choisir une image!");
                alert.showAndWait();
            } else {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    PhotoDAO ps1 = new PhotoDAO();
                    Photo p1 = new Photo(this.url, tfTitre.getText(), tfTheme.getText(), date.toString(), tfCouleur.getText(), tfLocalisation.getText(), UserDAO.connectedUser.getUserId());
                    ps1.insert(p1);
                    String tit = "Ajout effectuée avec succés";
                    String message = "Photo ajouté dans votre gallerie";
                    NotificationType notification = NotificationType.SUCCESS;
                    TrayNotification tray = new TrayNotification(tit, message, notification);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(javafx.util.Duration.seconds(2));
                    try {
                        
                        Parent type = FXMLLoader.load(getClass().getResource("/view/ProfileView.fxml"));
                        Scene scene = new Scene(type);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("Fotify");
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Fonction du bouton de selection de la photo a inserr
        addPH.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            file = fileChooser.showOpenDialog(null);
            //FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            //FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            //fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            pic = (file.toURI().toString());
            phv.setImage(new Image(pic));
            try {
                //  pic=new Upload().upload(file,"uimg");
                pic = new Upload().upload(file, "");
            } catch (IOException ex) {
                Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
            }

            //   image= new Image("http://localhost/uimg/"+pic);
            this.url = "http://localhost/image/" + pic;
        }
        );
    }

    

}
