/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Participant;
import entity.Event;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import dao.EvenementDAO;
import dao.ParticiperDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class EventController implements Initializable {

    @FXML
    private TableView<Event> table1;
    @FXML
    private TableColumn<Event, String> tabletitre;
    @FXML
    private TableColumn<Event, String> tablecontenu;
    @FXML
    private TableColumn<Event, String> tableimage;
    @FXML
    private TableColumn<Event, String> tabledajout;
    @FXML
    private TableColumn<Event, String> tabledmodif;
    @FXML
    private TextField titre;
    @FXML
    private TextField contenu;
    @FXML
    private ImageView importeimage;
    @FXML
    private DatePicker dateajout;
    @FXML
    private DatePicker datemodif;
    String img = "";
    List<String> type;
    private Statement ste;
    private Event cc = null;
    @FXML
    private AnchorPane ap;

    @FXML
    private Label erreurcontenu;
    @FXML
    private Label erreurimg;
    @FXML
    private Label erreurdateajout;
    @FXML
    private Label erreurdatemodif;
    @FXML
    private Label erreurtitre;
    @FXML
    private Button imagee;
    String cssLayout = "-fx-background-color:  #1f1f22;\n"
            + "-fx-border-insets: 2;\n"
            + "-fx-border-width: 2;\n"
            + "-fx-text-fill:white;\n"
            + "-fx-border-radius: 10;\n" + "-fx-border-color: #fabe2e;\n";
    String cLayout = "-fx-background-color:  #1f1f22;\n";
    @FXML
    private PieChart piechart;
    @FXML
    private Label caption;
    private Connection con;
    @FXML
    private TableView<Participant> table_participation;
    @FXML
    private TableColumn<Participant, String> id_evenement;
    @FXML
    private TableColumn<Participant, String> id_user;
    @FXML
    private TableColumn<Participant, String> date_participation;
    @FXML
    private Button retour;

    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
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

        ap.setStyle(cLayout);
        imagee.setStyle(cssLayout);

        try {
            Statement stmt1 = con.createStatement();
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            String SQL1 = "SELECT evenement.titre, evenement.nb_participer FROM evenement";
            ResultSet rs1 = stmt1.executeQuery(SQL1);
            while (rs1.next()) {
                pieData.add(new PieChart.Data("NOM evenement : " + rs1.getString(1) + "\n" + "nb participation evenement : " + rs1.getString(2), rs1.getDouble(2)));

            }

            piechart.setData(pieData);

            caption.setTextFill(Color.RED);
            caption.setStyle("-fx-font: 24 arial;");

            for (final PieChart.Data data : piechart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                    int i = (int) data.getPieValue();
                    caption.setText(String.valueOf("nb_participer : " + i));
                });
            }
            afficher();
            afficherParticiper();
            type = new ArrayList();
            type.add("*.jpg");
            type.add("*.png");

            table1.setOnMouseClicked((MouseEvent event) -> {
                cc = (Event) table1.getSelectionModel().getSelectedItem();
                titre.setText(cc.getTitre());
                contenu.setText(cc.getContenu());
                img = cc.getImage();
                importeimage.setImage(new Image("http://127.0.0.1/doc/" + cc.getImage()));
                LocalDate d1 = cc.getDateajout().toLocalDate();
                LocalDate d2 = cc.getDatemodif().toLocalDate();
                dateajout.setValue(d1);
                datemodif.setValue(d2);
            });
            titre.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    titre.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
                }
            });
            titre.textProperty().addListener(new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue.isEmpty()) {
                        erreurtitre.setText("remplir champ titre");
                    } else if (newValue.length() > 200) {
                        erreurtitre.setText("Max champ titre 200");
                    } else {
                        erreurtitre.setText("");
                    }
                }

            });
            titre.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (titre.getText().length() == 0) {
                        erreurtitre.setText("remplir champ titre");
                    }

                }

            });
            contenu.textProperty().addListener(new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue.isEmpty()) {
                        erreurcontenu.setText("remplir champ contenu");
                    } else if (newValue.length() > 200) {
                        erreurcontenu.setText("Max champ contenu 200");
                    } else {
                        erreurcontenu.setText("");
                    }
                }

            });
            contenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (contenu.getText().length() == 0) {
                        erreurcontenu.setText("remplir champ contenu");
                    }

                }

            });
            dateajout.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (dateajout.getValue() == null) {
                        erreurdateajout.setText("remplir champ date debut");
                    }

                }

            });
            dateajout.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue == null) {
                    erreurdateajout.setText("remplir champ date debut");
                } else {
                    erreurdateajout.setText("");
                }

            });

            datemodif.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (datemodif.getValue() == null) {
                        erreurdatemodif.setText("remplir champ date modification");
                    }

                }

            });
            datemodif.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue == null) {
                    erreurdatemodif.setText("remplir champ date modification");
                } else {
                    erreurdatemodif.setText("");
                }

            });
            imagee.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue.isEmpty()) {
                        erreurimg.setText("remplir champ image");
                    } else {
                        erreurimg.setText("");
                    }
                }

            });
            imagee.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (imagee.getText().length() == 0) {
                        erreurimg.setText("remplir champ image");
                    }

                }

            });

        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void importimage(ActionEvent event) {

        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png files", type));
        File fc = f.showOpenDialog(null);

        if (fc != null) {
            img = fc.getName();
            FileSystem fileSys = FileSystems.getDefault();
            Path srcPath = fc.toPath();
            Path destPath = fileSys.getPath("C:\\wamp\\www\\doc\\" + fc.getName());
            try {
                Files.copy(srcPath, destPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Image i = new Image(fc.getAbsoluteFile().toURI().toString());
            importeimage.setImage(i);

        }

    }

    @FXML
    private void ajouter(ActionEvent event) {
        if (titre.getText().isEmpty() || (img.isEmpty() && cc.getImage().isEmpty()) || contenu.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "verifer les champs");
        } else {
            try {
                String titre1 = titre.getText();
                String contenu1 = contenu.getText();
                LocalDate dd = dateajout.getValue();
                LocalDate df = datemodif.getValue();
                Date dateajout1 = java.sql.Date.valueOf(dd);
                Date datemodif2 = java.sql.Date.valueOf(df);
                
                EvenementDAO sp = new EvenementDAO();
                Event e = new Event(titre1, contenu1, img, dateajout1, datemodif2);
                
                sp.ajouter(e);
                JOptionPane.showMessageDialog(null, "ajout avec succes");
                titre.clear();
                contenu.clear();
                dateajout.setValue(null);
                datemodif.setValue(null);
                importeimage.setImage(null);
                afficher();
            } catch (SQLException ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void modifier(ActionEvent event) {
        try {
            EvenementDAO cs = new EvenementDAO();
            if (cc == null) {
                JOptionPane.showMessageDialog(null, "choisir evenement");
                
            } else {

                LocalDate dd = dateajout.getValue();
                LocalDate df = datemodif.getValue();
                java.sql.Date d1 = java.sql.Date.valueOf(dd);
                java.sql.Date d2 = java.sql.Date.valueOf(df);
                if (img.length() == 0) {
                    cs.update(new Event(titre.getText(), contenu.getText(), img, d1, d2), cc.getId());
                } else {
                    cs.update(new Event(titre.getText(), contenu.getText(), img, d1, d2), cc.getId());
                }
                afficher();
                JOptionPane.showMessageDialog(null, "evenement modifier");
                titre.clear();
                contenu.clear();
                importeimage.setImage(null);
                dateajout.setValue(null);
                datemodif.setValue(null);
                cc = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void supprimer(ActionEvent event) {
        try {
            EvenementDAO cs = new EvenementDAO();
            Event cc = (Event) table1.getSelectionModel().getSelectedItem();
            if (cc == null) {
                JOptionPane.showMessageDialog(null, "choisir evenenement");
                
            } else {
                cs.delete(cc.getId());
                afficher();
                JOptionPane.showMessageDialog(null, "evenenement supprimer");
                titre.clear();
                contenu.clear();
                importeimage.setImage(null);
                dateajout.setValue(null);
                datemodif.setValue(null);
                cc = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void afficher() {
        try {
            EvenementDAO sp = new EvenementDAO();
            List events = sp.readAll();
            ObservableList et = FXCollections.observableArrayList(events);
            table1.setItems(et);
            tabletitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            tablecontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
            tableimage.setCellValueFactory(new PropertyValueFactory<>("photo"));
            tabledajout.setCellValueFactory(new PropertyValueFactory<>("dateajout"));
            tabledmodif.setCellValueFactory(new PropertyValueFactory<>("datemodif"));
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void afficherParticiper() throws SQLException {
        ParticiperDAO sp = new ParticiperDAO();
        List events = sp.readAll();
        ObservableList et = FXCollections.observableArrayList(events);
        table_participation.setItems(et);
        id_evenement.setCellValueFactory(new PropertyValueFactory<>("id_evenement"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        date_participation.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    @FXML
    private void supprimerPartcipation(ActionEvent event) {
        try {
            ParticiperDAO cs = new ParticiperDAO();
            Participant cc = (Participant) table_participation.getSelectionModel().getSelectedItem();
            try {
                ste = con.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (cc == null) {
                JOptionPane.showMessageDialog(null, "choisir participant");
                
            } else {
                try {
                    cs.delete(cc.getId_participer());
                    
                    afficherParticiper();
                    String SQL1 = "UPDATE library.evenement SET  nb_participer=nb_participer-1 WHERE id ='" + cc.getId_evenement() + "'";
                    int rs1 = ste.executeUpdate(SQL1);
                    
                    JOptionPane.showMessageDialog(null, "participant supprimer");
                    
                    cc = null;
                } catch (SQLException ex) {
                    Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
