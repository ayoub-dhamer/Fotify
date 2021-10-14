/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Reclamation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 */
public class ReclamationItemController implements Initializable {

    @FXML
    private Label sujetLB;
    @FXML
    private Label dateLB;
    @FXML
    private Label descriptionLB;
    @FXML
    private Label etatLB;
    @FXML
    private Button detailBTN;
    @FXML
    private Label photoLB;

    private Reclamation r;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(Reclamation r) {

        this.r = r;

        sujetLB.setText(r.getSujet());
        dateLB.setText(r.getDate_creation());
        descriptionLB.setText(r.getDescription());
        etatLB.setText(r.getEtat().toString());
        photoLB.setText(r.getPhoto().gettitre());

    }

    @FXML
    private void detailClick(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/view/DetailReclamation.fxml"
                )
        );
        try {
            loader.load();
            DetailReclamationController dialogController = loader.getController();
            dialogController.initData(this.r);
            sujetLB.getScene().setRoot(loader.getRoot());

        } catch (IOException ex) {
            Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
