/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ReclamationDAO;
import entity.Reclamation;
import dao.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class DisplayReclamationController implements Initializable {

    String ccLayout = "-fx-background-color:#1f1f22;\n" + "-fx-text-fill: red;\n";
    String cLayout = "-fx-background-color:#1f1f22;\n";
    String cssLayout = "-fx-background-color:#1f1f22;\n"
            + "-fx-border-insets: 2;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill: white;\n"
            + "-fx-font-weight: bold;\n"
            + "-fx-border-radius: 10;\n"
            + "-fx-border-color: #fabe2e;\n"
            + "-fx-background-radius: 11;\n";
    public ArrayList<Label> comms = new ArrayList<>();
    @FXML
    private ScrollPane sp;
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
            
            try {
                Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
                pane.getChildren().add(n);
            } catch (IOException ex) {
                Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ReclamationDAO rd = new ReclamationDAO();
            for (Reclamation r : rd.isplayById(UserDAO.connectedUser.getUserId())) {
                Label l = new Label();
                l.setText("sujet : " + r.getSujet() + "        description : " + r.getDescription() + "          Etat :  " + r.getEtat());
                l.setAlignment(Pos.CENTER);
                comms.add(l);
                //  comms.forEach(e->e.setStyle(cLayout));
                
                VBox vB = new VBox();
                vB.setMinWidth(300);
                // vB.setStyle(ccLayout);
                vB.setSpacing(8);//jdid
                vB.setStyle(cLayout);
                comms.forEach(e -> vB.getChildren().add(e));
                comms.forEach(e -> e.setMinHeight(50));
                
                comms.forEach(e -> e.setMaxWidth(Double.MAX_VALUE));
                comms.forEach(e -> e.setStyle(cssLayout));
                
                //comms.forEach(e->e.setStyle(cLayout));
                sp.setContent(vB);
                
                l.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailReclamation.fxml"));
                        Region root = (Region) loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        DetailReclamationController dialogController = loader.getController();
                        dialogController.initData(r);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplayReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
