/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.DocumentException;
import entity.Participant;
import entity.Event;
import dao.EvenementDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import dao.ParticiperDAO;
import dao.UserDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 */
public class DisplayEventController implements Initializable {

    @FXML
    private VBox actualitecontainer;

    private Connection con;
    @FXML
    private TextField search;
    @FXML
    private ScrollPane scroll;
    int n = 1;
    @FXML
    private Button trier;
    @FXML
    private Pane pane;
    String cLayout = "-fx-text-fill:white;\n";

    String cssLayout = "-fx-background-color: #1f1f22;\n"
            + "-fx-border-color: #fabe2e;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill:white;\n"
            + "-fx-border-radius: 10;\n";

    
    private Statement ste;
    private PreparedStatement pre;
    String s1 = "";

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

        actualitecontainer.setSpacing(5);
        try {
            displayActualite();
        } catch (SQLException ex) {
            Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void displayActualite() throws SQLException {
        EvenementDAO pa = new EvenementDAO();
        ParticiperDAO oo = new ParticiperDAO();

        String req = "select * from evenement  ";
        List<VBox> list = new ArrayList<>();
        try {
            ste = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            java.sql.Date d1 = new java.sql.Date(rs.getDate(5).getTime());
            java.sql.Date d2 = new java.sql.Date(rs.getDate(6).getTime());

            Event a1 = new Event(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), d1, d2);
            ImageView va = new ImageView(new Image("http://127.0.0.1/doc/" + rs.getString(4)));
            va.setFitHeight(170);
            va.setFitWidth(200);
            Label nom = new Label("Titre : " + a1.getTitre());
            Label contenu = new Label("Le contenu : " + a1.getContenu());
            Label dateajout = new Label("Cette evenement est ajouté le : " + a1.getDateajout());
            Label datemodif = new Label("Cette evenement est modifé le : " + a1.getDatemodif());
            nom.setStyle(cLayout);
            contenu.setStyle(cLayout);
            dateajout.setStyle(cLayout);
            datemodif.setStyle(cLayout);
            HBox h1 = new HBox();
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(nom);
            HBox h2 = new HBox();
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(contenu);
            HBox h3 = new HBox();
            h3.setSpacing(10);
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(dateajout);
            HBox h4 = new HBox();
            h4.setSpacing(10);
            h4.setAlignment(Pos.CENTER);
            h4.getChildren().addAll(datemodif);
            Button bt2 = new Button("Participer");
            bt2.setStyle(cssLayout);
            java.util.Date dd = new java.util.Date();
            if (oo.chercher_ajout(new Participant(a1.getId(), UserDAO.connectedUser.getUserId(), dd))) {

                bt2.setDisable(true);

            }
            bt2.setOnAction((ActionEvent event) -> {
                //bitha heki chas
                if (!oo.chercher_ajout(new Participant(a1.getId(), UserDAO.connectedUser.getUserId(), d1))) {
                    try {
                        oo.ajouter(new Participant(a1.getId(), UserDAO.connectedUser.getUserId(), d1));
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getButtonTypes().setAll(ButtonType.OK);
                        alert.setContentText("Participation effectuée avec succés");
                        alert.show();
                        String tit = "Participation effectuée avec succés";
                        String message = "Profitez votre evennement ";
                        NotificationType notification = NotificationType.SUCCESS;
                        TrayNotification tray = new TrayNotification(tit, message, notification);
                        tray.setAnimationType(AnimationType.POPUP);
                        tray.showAndDismiss(javafx.util.Duration.seconds(2));
                        
                        String SQL1 = "UPDATE library.evenement SET  nb_participer=nb_participer+1 WHERE id ='" + a1.getId() + "'";
                        int rs1 = ste.executeUpdate(SQL1);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    bt2.setDisable(true);
                } else {
                    System.out.println("evenement DEJA participer");
                }
            });
            Button bt3 = new Button("Imprimer");
            bt3.setStyle(cssLayout);
            bt3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    PDF pdf = new PDF();

                    try {
                        pdf.pdf(a1);
                    } catch (SQLException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    bt2.setDisable(true);

                }
            });
            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.setSpacing(10);
            v.getChildren().addAll(h1, h2, h3, h4, bt2, bt3);

            VBox vv = new VBox();
            vv.setAlignment(Pos.CENTER);
            vv.setSpacing(10);
            vv.getChildren().addAll(va);
            HBox No = new HBox();
            No.setSpacing(10);
            No.setAlignment(Pos.CENTER);
            No.getChildren().addAll(vv, v);

            VBox v1 = new VBox();
            v1.setAlignment(Pos.CENTER);
            v1.setSpacing(10);
            v1.getChildren().addAll(No);
            list.add(v1);

        }
        actualitecontainer.getChildren().addAll(list);

    }

    private void displayActualiteAvancee(String req) throws SQLException {
        EvenementDAO pa = new EvenementDAO();
        ParticiperDAO oo = new ParticiperDAO();
        List<VBox> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            java.sql.Date d1 = new java.sql.Date(rs.getDate(5).getTime());
            java.sql.Date d2 = new java.sql.Date(rs.getDate(6).getTime());

            Event a1 = new Event(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), d1, d2);
            ImageView va = new ImageView(new Image("http://127.0.0.1/doc/" + rs.getString(4)));
            va.setFitHeight(170);
            va.setFitWidth(200);
            Label nom = new Label("Titre : " + a1.getTitre());
            Label contenu = new Label("Le contenu : " + a1.getContenu());
            Label dateajout = new Label("Cette annonce est ajouté le : " + a1.getDateajout());
            Label datemodif = new Label("Cette annonce est modifé le : " + a1.getDatemodif());
            nom.setStyle(cLayout);
            contenu.setStyle(cLayout);
            dateajout.setStyle(cLayout);
            datemodif.setStyle(cLayout);
            HBox h1 = new HBox();
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(nom);
            HBox h2 = new HBox();
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(contenu);
            HBox h3 = new HBox();
            h3.setSpacing(10);
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(dateajout);
            HBox h4 = new HBox();
            h4.setSpacing(10);
            h4.setAlignment(Pos.CENTER);
            h4.getChildren().addAll(datemodif);
            Button bt2 = new Button("Participer");
            bt2.setStyle(cssLayout);
            java.util.Date dd = new java.util.Date();
            if (oo.chercher_ajout(new Participant(a1.getId(), UserDAO.connectedUser.getUserId(), dd))) {
                bt2.setDisable(true);
            }
            bt2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) { //bitha heki chas
                    if (!oo.chercher_ajout(new Participant(a1.getId(), UserDAO.connectedUser.getUserId(), d1))) {
                        try {
                            oo.ajouter(new Participant(a1.getId(), UserDAO.connectedUser.getUserId(), d1));
                            
                            String SQL1 = "UPDATE library.evenement SET  nb_participer=nb_participer+1 WHERE id ='" + a1.getId() + "'";
                            int rs1 = ste.executeUpdate(SQL1);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        bt2.setDisable(true);
                    } else {
                        System.out.println("evenement DEJA participer");
                    }

                }
            });
            Button bt3 = new Button("Imprimer");
            bt3.setStyle(cssLayout);
            bt3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    PDF pdf = new PDF();

                    try {
                        pdf.pdf(a1);
                    } catch (SQLException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(DisplayEventController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    bt2.setDisable(true);

                }
            });
            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.setSpacing(10);
            v.getChildren().addAll(h1, h2, h3, h4, bt2, bt3);

            VBox vv = new VBox();
            vv.setAlignment(Pos.CENTER);
            vv.setSpacing(10);
            vv.getChildren().addAll(va);
            HBox No = new HBox();
            No.setSpacing(10);
            No.setAlignment(Pos.CENTER);
            No.getChildren().addAll(vv, v);

            VBox v1 = new VBox();
            v1.setAlignment(Pos.CENTER);
            v1.setSpacing(10);
            v1.getChildren().addAll(No);
            list.add(v1);

        }
        actualitecontainer.getChildren().addAll(list);
    }

    @FXML
    private void recherche(ActionEvent event) throws SQLException {
        actualitecontainer.getChildren().removeAll(actualitecontainer.getChildren());
        String search11 = search.getText();
        String req = "select * from evenement where titre = '" + search11 + "' or contenu = '" + search11 + "'";
        displayActualiteAvancee(req);
        if (search11.equals("")) {
            req = "select * from evenement";
            displayActualiteAvancee(req);

        }

    }

    @FXML
    private void trierrd(ActionEvent event) throws SQLException {
        actualitecontainer.getChildren().removeAll(actualitecontainer.getChildren());
        String req = "select * from evenement ORDER BY titre  ";
        displayActualiteAvancee(req);
    }

}
