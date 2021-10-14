/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AbonnementDAO;
import entity.Subscriber;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 */
public class DisplaySubscriberController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public ArrayList<Label> abbs = new ArrayList<>();
    public ArrayList<Label> noms = new ArrayList<>();
    public ArrayList<Integer> ids = new ArrayList<>();
    @FXML
    private ScrollPane sp1;
    @FXML
    private ScrollPane sp2;
    @FXML
    private Label com1;
    @FXML
    private Label com2;
    @FXML
    private Pane pane;
    String cssLayout = "-fx-background-color: #1f1f22;\n"
            + "-fx-border-insets: 2;\n"
            + "-fx-border-color: #fabe2e;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill:white;\n"
            + "-fx-font-weight: bold;\n"
            + "-fx-min-height:50;\n"
            + "-fx-border-radius: 10;\n";
    String cLayout = "-fx-background-color:#1f1f22;\n";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            
            try {
                Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
                pane.getChildren().add(n);
            } catch (IOException ex) {
                Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            AbonnementDAO abd = new AbonnementDAO();
            
            for (Subscriber a : abd.felowers()) {
                
                Label l = new Label();
                l.setText(a.getnomA());
                l.setAlignment(Pos.CENTER);
                abbs.add(l);
                VBox vB = new VBox();
                vB.setMinWidth(269);
                
                vB.setMinHeight(300);
                vB.setSpacing(8);//jdid
                vB.setStyle(cLayout);
                abbs.forEach(e -> vB.getChildren().add(e));
                abbs.forEach(e -> e.setMinHeight(50));
                abbs.forEach(e -> e.setMaxWidth(250));
                abbs.forEach(e -> e.setStyle(cssLayout));
                //abbs.forEach(e->e.setMaxWidth(Double.MAX_VALUE));
                sp1.setContent(vB);
                com1.setText("" + abd.felowers().size());
                
            }
            for (Subscriber a : abd.felowing()) {
                
                Label l = new Label();
                l.setText(a.getAnom());
                l.setAlignment(Pos.CENTER);
                noms.add(l);
                VBox vB = new VBox();
                vB.setStyle(cLayout);
                
                vB.setMinWidth(269);
                vB.setSpacing(8);//jdid
                vB.setMinHeight(301);
                noms.forEach(e -> vB.getChildren().add(e));
                noms.forEach(e -> e.setMinHeight(50));
                noms.forEach(e -> e.setMaxWidth(250));
                noms.forEach(e -> e.setStyle(cssLayout));
                // noms.forEach(e->e.setMaxWidth(Double.MAX_VALUE));
                sp2.setContent(vB);
                com2.setText("" + abd.felowing().size());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplaySubscriberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
