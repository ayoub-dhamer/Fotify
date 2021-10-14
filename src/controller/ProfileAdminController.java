/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import entity.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 */
public class ProfileAdminController implements Initializable {

    @FXML
    private Label fotify;
    @FXML
    private TableView<User> tvuser;
    @FXML
    private TableColumn<User, String> colnom;
    @FXML
    private TableColumn<User, String> colprenom;
    @FXML
    private TableColumn<User, String> colbio;
    private TableColumn<User, Integer> colage;
    @FXML
    private TableColumn<User, String> colemail;
    @FXML
    private Button supp;
    @FXML
    private Button retour;

    private ObservableList<User> user;
    private ListUser listPhoto = new ListUser();
    private ObservableList<User> phs = FXCollections.observableArrayList();
    @FXML
    private TableColumn<User, String> coltype;
    @FXML
    private TextField rech;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tvuser.setItems(listPhoto.getUser());
        colnom.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        colprenom.setCellValueFactory(new PropertyValueFactory<User, String>("nom"));
        colbio.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        colemail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        coltype.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        

        FilteredList<User> filteredData = new FilteredList<>(listPhoto.getUser(), e -> true);
        rech.setOnKeyReleased(event -> {
            rech.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super User>) user -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.getUserNom().toLowerCase().contains(newValue)) {
                        return true;
                    } else if (user.getUserPrenom().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (user.getUserBio().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (user.getUserEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (user.getUserType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<User> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvuser.comparatorProperty());
            tvuser.setItems(sortedData);

        });

        supp.setOnAction(event -> {
            try {
                ObservableList<User> u = tvuser.getSelectionModel().getSelectedItems();

                UserDAO udao = UserDAO.getInstance();
                udao.delete(u.get(0));
                tvuser.getItems().removeAll(tvuser.getSelectionModel().getSelectedItems());
                JOptionPane.showMessageDialog(null, "User delete success");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please select item to delete");

            }

        });

        retour.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/Back.fxml"));
                Scene scene = new Scene(type);
                Image image = new Image("/img/pik.gif");
                scene.setCursor(new ImageCursor(image,
                        image.getWidth() / 2,
                        image.getHeight() / 2));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fotify");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

}
