/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXToolbar;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class FrontController implements Initializable {

    @FXML
    private JFXToolbar toolbar;
    @FXML
    private ImageView imglogo;
    @FXML
    private Pane pane;
    @FXML
    private Button ab5;
    @FXML
    private Button ab6;
    @FXML
    private Button ab3;
    @FXML
    private Button ab4;
    @FXML
    private Button ab2;
    @FXML
    private Button ab;
    @FXML
    private Button ab1;
    @FXML
    public AnchorPane ap;
    @FXML
    private Button singout;
    @FXML
    private Button ab51;
    @FXML
    private VBox vbox;
    @FXML
    private HBox imgslider;
    private Connection con;
    @FXML
    private ScrollPane scroller;
    String s1 = "";
    @FXML
    private AnchorPane panne;

    public FrontController() {

    }

    private final double IMG_WIDTH = 1125;
    private final double IMG_HEIGHT = 135;

    private int NUM_OF_IMGS;
    private final int SLIDE_FREQ = 4; // in secs

    private Statement ste;
    private PreparedStatement pre;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void logout(ActionEvent event) throws IOException {

        AnchorPane page = FXMLLoader.load(getClass().getResource(""));
        panne.getChildren().setAll(page);
    }

    @FXML
    private void gererprofile(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource(""));
        ap.getChildren().setAll(pane);

    }

    @FXML
    private void gererevenement(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/AfficherEvenement.fxml"));
        ap.getChildren().setAll(pane);
    }

    @FXML
    private void gererreclamation(ActionEvent event) {

    }

    @FXML
    private void dashboard(ActionEvent event) throws IOException {
        AnchorPane page = FXMLLoader.load(getClass().getResource("/view/Front.fxml"));
        ap.getChildren().setAll(page);
    }

}
