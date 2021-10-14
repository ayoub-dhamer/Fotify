/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ReclamationDAO;
import entity.Reclamation;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 */
public class DetailReclamationController implements Initializable {

    @FXML
    private TextArea descriptionTA;
    @FXML
    private Text photoT;
    @FXML
    private Text sujetT;
    @FXML
    private Button modifierBT;
    @FXML
    private Button retourBT;
    @FXML
    private Text etatT;
    @FXML
    private Text dateT;
    @FXML
    private TextField sujetTF;
    @FXML
    private ComboBox<String> sujetCB;
    private Reclamation r;
    private boolean canEdit = false;
    @FXML
    private Label titleLB;
    @FXML
    private HBox editOptionHB;
    @FXML
    private HBox showEditHB;
    @FXML
    private VBox sujetVB;
    List<String> sujetoption = new ArrayList<>();
    @FXML
    private Button saugarderBT;
    @FXML
    private Button annulerBT;
    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
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

        sujetoption.add("option1");
        sujetoption.add("option0");
        sujetoption.add("autre");

        sujetCB.setItems(FXCollections.observableArrayList(sujetoption));
    }

    public void initData(Reclamation r) {
        this.r = r;

        descriptionTA.setText(r.getDescription());
        sujetT.setText(r.getSujet());
        if (sujetoption.contains(r.getSujet())) {
            sujetCB.setValue(r.getSujet());
        } else {
            sujetCB.setValue("autre");
            sujetTF.setVisible(true);
            sujetTF.setText(r.getSujet());
        }
        etatT.setText(r.getEtat().toString());
        dateT.setText(r.getDate_creation());

        if (r.getEtat().equals("EN_ATTENTE")) {
            modifierBT.setDisable(false);
        }

    }

    @FXML
    private void modifierClick(ActionEvent event) {

        setVisibleEdit();

    }

    @FXML
    private void annulerClick(ActionEvent event) {

        setInvisibleEdit();

    }

    @FXML
    private void retourClick(ActionEvent event) {
        try {
            photoT.getScene().setRoot(FXMLLoader.load(getClass().getResource("/view/MesReclamations.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void sauvgarderClick(ActionEvent event) {

        try {
            
            ReclamationDAO rdao = new ReclamationDAO();
            String sujett;
            if (sujetCB.getValue() == "autre") {
                r.setSujet(sujetTF.getText());
            } else {
                r.setSujet(sujett = sujetCB.getValue());
            }
            r.setDescription(descriptionTA.getText());
            
            rdao.update(r);
            try {
                photoT.getScene().setRoot(FXMLLoader.load(getClass().getResource("/view/MesReclamations.fxml")));
            } catch (IOException ex) {
                Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void sucjetCBClick(ActionEvent event) {
        if (sujetCB.getValue() == "autre") {
            sujetTF.setVisible(true);
        } else {
            sujetTF.setVisible(false);

        }
    }

    private void setVisibleEdit() {
        showEditHB.setVisible(false);
        editOptionHB.setVisible(true);

        sujetVB.setVisible(true);
        sujetT.setVisible(false);

        descriptionTA.setEditable(true);

    }

    private void setInvisibleEdit() {
        showEditHB.setVisible(true);
        editOptionHB.setVisible(false);
        sujetVB.setVisible(false);
        sujetT.setVisible(true);
        descriptionTA.setEditable(false);

    }
}
