/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PhotoDAO;
import entity.Photo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 */
public class UpdatePhotoController implements Initializable {

    @FXML
    private TextField tftitre;
    @FXML
    private TextField tftheme;
    @FXML
    private TextField tfcouleur;
    @FXML
    private TextField tfloc;
    @FXML
    private BorderPane bp;
    @FXML
    private ImageView img;
    @FXML
    private Button btnmodif;
    @FXML
    private Button btnsupprim;
    @FXML
    private Button btnprofil;
    public int g;
    public Photo p;
    @FXML
    private Pane pane;

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

        btnprofil.setOnAction(e -> {
            try {

                Parent page1 = FXMLLoader.load(getClass().getResource("/view/ProfileView.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnsupprim.setOnAction(e -> {

            PhotoDAO ps1 = PhotoDAO.getInstance();
            ps1.deleteph(ps1.displayById(g));

            try {

                JOptionPane.showMessageDialog(null, "Photo delete success");
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/ProfileView.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnmodif.setOnAction(e -> {

            PhotoDAO ps1 = PhotoDAO.getInstance();
            Photo updated = new Photo(g, tftitre.getText(), tftheme.getText(), tfcouleur.getText(), tfloc.getText());
            ps1.update(updated);

        });

    }

    public int setIdd(int id) {

        try {
            PhotoDAO ps1 = new PhotoDAO();
            Photo photo1 = new Photo();
            p = photo1;
            photo1 = ps1.displayById(id);
            
            g = id;
            
            Image image = new Image(photo1.geturl());
            tftitre.setText(photo1.gettitre());
            tfcouleur.setText(photo1.getcouleur());
            tftheme.setText(photo1.gettheme());
            tfloc.setText(photo1.getlocalisation());
            img.setImage(image);
            img.setFitHeight(200);
            img.setFitWidth(200);
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
