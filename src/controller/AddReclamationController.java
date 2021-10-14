/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PhotoDAO;
import dao.ReclamationDAO;
import dao.UserDAO;
import entity.Photo;
import entity.Reclamation;
import utils.EmailService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class AddReclamationController implements Initializable {

    private Text photoT;
    @FXML
    private ComboBox<String> sujetCB;
    @FXML
    private TextField sujetTF;
    @FXML
    private TextArea descriptionTA;
    @FXML
    private Button envoyerBT;
    @FXML
    private Button annulerBT;
    @FXML
    private Label kk;
    @FXML
    private Pane pane;

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

        List<String> sujetoption = new ArrayList<>();

        sujetoption.add("option1");
        sujetoption.add("option0");
        sujetoption.add("autre");

        sujetCB.setItems(FXCollections.observableArrayList(sujetoption));
    }

    @FXML
    private void sujetCBClick(ActionEvent event) {
        if (sujetCB.getValue() == "autre") {
            sujetTF.setVisible(true);
        } else {
            sujetTF.setVisible(false);

        }
    }

    @FXML
    private void annulerClick(ActionEvent event) {

        try {
            photoT.getScene().setRoot(FXMLLoader.load(getClass().getResource("/view/MesReclamations.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    int setIdd(int id) {
        try {
            PhotoDAO ps1 = new PhotoDAO();
            Photo photo1 = new Photo();
            photo1 = ps1.displayById(id);//recuperer la photo avec son id

            kk.setText(photo1.gettitre());
            envoyerBT.setOnAction(e -> {

                try {

                    ReclamationDAO rdao = new ReclamationDAO();
                    String sujett;
                    if (sujetCB.getValue() == "autre") {
                        sujett = sujetTF.getText();
                    } else {
                        sujett = sujetCB.getValue();
                    }

                    rdao.insert(new Reclamation(sujett, descriptionTA.getText(), "EN_ATTENTE", "", UserDAO.connectedUser.getUserId(), id, null, null));

                    EmailService.sendMailFunc("jlassi.med.yacine@gmail.com", "Reclamation: " + sujett, "Votre Reclamation est en attente " + descriptionTA.getText());

                    try {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/view/MesReclamations.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            
        } catch (SQLException ex) {
            Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
