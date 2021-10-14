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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class BackGallController implements Initializable {

    @FXML
    private TableView<?> tvphoto;
    @FXML
    private TableColumn<Photo, String> coltitre;
    @FXML
    private TableColumn<Photo, String> coltheme;
    @FXML
    private TableColumn<Photo, String> colcouleur;
    @FXML
    private TableColumn<Photo, String> coldate;
    @FXML
    private Button btnsupp;
    @FXML
    private ImageView imgv;
    private ListPhoto listPhoto = new ListPhoto();
    private ObservableList<Photo> phs = FXCollections.observableArrayList();
    @FXML
    private Label fotify;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    public void clear() {
        tvphoto.getItems().clear();
    }

    public void remplir() {
        PhotoDAO pdao = PhotoDAO.getInstance();

        tvphoto.setItems(listPhoto.getPhoto());

        coltitre.setCellValueFactory(cell -> cell.
                getValue().getTitreProperty());
        coltheme.setCellValueFactory(cell -> cell.
                getValue().getThemeProperty());
        colcouleur.setCellValueFactory(cell -> cell.
                getValue().getcouleurProperty());
        coldate.setCellValueFactory(cell -> cell.
                getValue().getdateeProperty());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        retour.setOnAction(e -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/Back.fxml"));
                Scene scene = new Scene(type);
                Image image = new Image("/img/pik.gif");
                scene.setCursor(new ImageCursor(image,
                        image.getWidth() / 2,
                        image.getHeight() / 2));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fotify");
                new animatefx.animation.Pulse(type).play();
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        fotify.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/Back.fxml"));
                Scene scene = new Scene(type);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fotify");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        PhotoDAO pdao = PhotoDAO.getInstance();

        remplir();

        // TODO
        tvphoto.setOnMouseClicked(e -> {
            ObservableList<Photo> photo = (ObservableList<Photo>) tvphoto.getSelectionModel().getSelectedItems();
            for (int i = 0; i < photo.size(); i++) {
                imgv.setImage(new Image(photo.get(i).geturl()));
            }

        });

        btnsupp.setOnAction(e -> {
            ObservableList<Photo> photo = (ObservableList<Photo>) tvphoto.getSelectionModel().getSelectedItems();
            pdao.delete(photo.get(0));
            tvphoto.getItems().removeAll((ObservableList<Photo>) tvphoto.getSelectionModel().getSelectedItems()
            );
        });

    }

}
