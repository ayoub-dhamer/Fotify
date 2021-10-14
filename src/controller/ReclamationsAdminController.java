/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PhotoDAO;
import dao.ReclamationDAO;
import dao.UserDAO;

import entity.Reclamation;
import utils.EmailService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class ReclamationsAdminController implements Initializable {

    private ObservableList<Reclamation> reclamations;
    private FilteredList<Reclamation> filteredReclamation;

    @FXML
    private TableView<Reclamation> reclamationTV;
    @FXML
    private TableColumn<Reclamation, String> membreTC;
    @FXML
    private TableColumn<Reclamation, String> sujetTC;
    @FXML
    private TableColumn<Reclamation, String> date_creationTC;
    @FXML
    private TableColumn<Reclamation, String> etatTC;
    @FXML
    private TableColumn<Reclamation, Void> actionTC;
    @FXML
    private TextField rechercheTF;
    @FXML
    private ComboBox<String> rechercheCB;
    @FXML
    private Label fotify;
    @FXML
    private Button retour;

    public void getData() throws SQLException {
        ReclamationDAO rdao = new ReclamationDAO();
        UserDAO rda = new UserDAO();
        PhotoDAO ps1 = new PhotoDAO();
        this.reclamations = FXCollections.observableArrayList(rdao.playById());

        sujetTC.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        date_creationTC.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        etatTC.setCellValueFactory((CellDataFeatures<Reclamation, String> param) -> {
            Reclamation reclamation = param.getValue();
            String etat = reclamation.getEtat();
            return new SimpleObjectProperty<String>(etat);
        });
        UserDAO uda = UserDAO.getInstance();
        etatTC.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(/*Etat.values()*/)));
        membreTC.setCellValueFactory((CellDataFeatures<Reclamation, String> p) -> {
            return new SimpleStringProperty(uda.returnUserById(p.getValue().getUser_id()).getUserNom()+" "+uda.returnUserById(p.getValue().getUser_id()).getUserPrenom());
        });

        etatTC.setOnEditCommit((CellEditEvent<Reclamation, String> event) -> {
            TablePosition<Reclamation, String> pos = event.getTablePosition();
            String newEtat = event.getNewValue();
            int row = pos.getRow();
            Reclamation reclamation = event.getTableView().getItems().get(row);
            reclamation.setEtat(newEtat);
            rdao.update(reclamation);
            EmailService.sendMailFunc(rda.returnUserById(reclamation.getUser_id()).getUserEmail(), "Reclamation: " + reclamation.getSujet(), "Votre Reclamation est " + reclamation.getEtat());

        });

        actionTC.setCellFactory((TableColumn<Reclamation, Void> param) -> {
            final TableCell<Reclamation, Void> cell = new TableCell<Reclamation, Void>() {
                private final HBox paddedButton = new HBox();
                private final Button detailbtn = new Button("Details");
                private final Button supprimerbtn = new Button("Supprimer");

                {

                    paddedButton.setSpacing(10);
                    paddedButton.getChildren().add(detailbtn);
                    paddedButton.getChildren().add(supprimerbtn);

                    detailbtn.setOnAction((ActionEvent event) -> {
                        Reclamation reclamation = getTableView().getItems().get(getIndex());

                        try {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource(
                                            "/view/DetailReclamationAdmin.fxml"
                                    )
                            );
                            loader.load();
                            DetailReclamationAdminController dialogController = loader.getController();
                            dialogController.initData(reclamation);
                            rechercheTF.getScene().setRoot(loader.getRoot());

                        } catch (IOException ex) {
                            Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    supprimerbtn.setOnAction((ActionEvent event) -> {
                        getTableView().getSelectionModel().select(getIndex());
                        Reclamation reclamation = reclamationTV.getSelectionModel().getSelectedItem();

                        if (reclamation != null) {
                            reclamations.remove(reclamation);
                            rdao.delete(reclamation);

                        }

                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(paddedButton);
                    }
                }
            };
            return cell;
        });

        filteredReclamation = new FilteredList<>(reclamations, p -> true);
        rechercheTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredReclamation.setPredicate((rec) -> {
                if (newValue == null || newValue.isEmpty()) {

                    return this.checkRechercheCB(rec, rechercheCB.getSelectionModel().getSelectedItem()) && true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (rec.getSujet().toLowerCase().contains(lowerCaseFilter)) {
                    return this.checkRechercheCB(rec, rechercheCB.getSelectionModel().getSelectedItem()) && true;// Filter matches last name.
                } else {
                    return false; // Does not match.
                }
            });

        });
        rechercheCB.getItems().addAll("Tout", "EN_ATTENTE", "TRAITE", "REFUSE");
        rechercheCB.getSelectionModel().select(0);

        rechercheCB.valueProperty().addListener((observable, oldValue, newValue) -> {

            filteredReclamation.setPredicate((rec) -> {
                String lowerCaseFilter = rechercheTF.getText().toLowerCase();
                if (lowerCaseFilter == null || lowerCaseFilter.isEmpty()) {
                    return this.checkRechercheCB(rec, newValue) && true;
                }

                if (rec.getPhoto().gettitre().toLowerCase().contains(lowerCaseFilter)) {
                    return this.checkRechercheCB(rec, newValue) && true; // Filter matches first name.
                } else if (rec.getSujet().toLowerCase().contains(lowerCaseFilter)) {
                    return this.checkRechercheCB(rec, newValue) && true;// Filter matches last name.
                } else if (rec.getUser().getUserNom().toLowerCase().contains(lowerCaseFilter)) {
                    return this.checkRechercheCB(rec, newValue) && true;// Filter matches last name.
                }
                return false;

            });
        });

        SortedList<Reclamation> sortedData = new SortedList<>(filteredReclamation);

        sortedData.comparatorProperty().bind(reclamationTV.comparatorProperty());

        reclamationTV.setItems(sortedData);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        // TODO
        try {
            getData();

            //membreTC.setCellFactory(new TreeItemPropertyValueFactory<Reclamation, String>("membre"));
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationsAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkRechercheCB(Reclamation rec, String value) {

        if (value == null || value == "Tout") {
            return true;
        }
        if (rec.getEtat().toString() == value) {
            return true; // Filter matches first name.
        } else {
            return false;
        }

    }

}
