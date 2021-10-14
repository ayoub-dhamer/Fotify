/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CommentaireDao;
import dao.UserDAO;
import entity.Comment;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 */
public class GererCommController implements Initializable {

    @FXML
    private ScrollPane sp;
    @FXML
    private TextArea tfcomm;
    public ArrayList<Label> comms = new ArrayList<>();
    public int idcommentaire = 0;
    @FXML
    private Button btnmodif;
    @FXML
    private Button btnsupp;
    String cssLayout = "-fx-background-color: #2d424a;\n"
            + "-fx-border-insets: 2;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill:white;\n"
            + "-fx-border-radius: 10;\n";
    String cLayout = "-fx-background-color:#1f1f22;\n";
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
    }

    void comms(int id, int idU) {
        comms.clear();
        CommentaireDao ccc = CommentaireDao.getInstance();
        for (Comment c : ccc.owndisplaycomms(id, UserDAO.connectedUser.getUserId())) {/////jdiiiiid
            Label l = new Label();
            l.setText("  " + c.getcomm());
            comms.add(l);
            l.setOnMouseClicked(e -> {
                tfcomm.setText(c.getcomm());
                idcommentaire = c.getid_comm();
            });

            VBox vB = new VBox();
            vB.getChildren().clear();
            vB.setSpacing(8);
            comms.forEach(e -> vB.getChildren().add(e));
            comms.forEach(e -> e.setMinHeight(50));

            comms.forEach(e -> e.setMaxWidth(Double.MAX_VALUE));
            comms.forEach(e -> e.setStyle(cssLayout));
            vB.setStyle(cLayout);
            sp.setContent(vB);

        }
    }

    void setIdd(int id, int idU) {
        comms(id, idU);

        btnmodif.setOnAction(e -> {
            if (idcommentaire == 0) {
                Alert alert = new Alert(AlertType.NONE, "Erreur de champs", ButtonType.OK);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez sélectionner un commentaire à supprimer");
                alert.showAndWait();
            } else {
                try {
                    CommentaireDao cc = new CommentaireDao();
                    Comment cNew = new Comment(idcommentaire, tfcomm.getText());
                    cc.update(cNew);
                    sp.setContent(null);
                    comms(id, idU);
                } catch (SQLException ex) {
                    Logger.getLogger(GererCommController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        btnsupp.setOnAction(e -> {
            if (idcommentaire == 0) {
                Alert alert = new Alert(AlertType.NONE, "Erreur de champs", ButtonType.OK);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez sélectionner un commentaire à supprimer");
                alert.showAndWait();
            } else {
                try {
                    CommentaireDao cc = new CommentaireDao();
                    Comment cNew = cc.displayById(idcommentaire);
                    cc.delete(cNew);
                    sp.setContent(null);
                    comms(id, idU);
                } catch (SQLException ex) {
                    Logger.getLogger(GererCommController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
}
