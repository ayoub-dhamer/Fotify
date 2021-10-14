/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AbonnementDAO;
import dao.FeedbackDAO;
import dao.PhotoDAO;
import dao.UserDAO;
import entity.Subscriber;
import entity.Photo;
import entity.User;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class ShowUserController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private ImageView phProfil;
    @FXML
    private ScrollPane sp;
    @FXML
    private GridPane gp;
    public String nomaccount;

    public ArrayList<String> imagess = new ArrayList<>();
    public ArrayList<Subscriber> myabs = new ArrayList<>();
    public ArrayList<Image> ima = new ArrayList<>();
    public ArrayList<Label> imaa = new ArrayList<>();
    public ArrayList<Integer> imaaa = new ArrayList<>();
    public ArrayList<ImageView> pics = new ArrayList();
    public ArrayList<String> pic = new ArrayList();
    public String cssLayout = "-fx-background-color:#fabe2e;\n"
            + "-fx-text-fill: #0a0400;\n" + "-fx-font-weight: bold;\n";
    @FXML
    private Button feed;
    @FXML
    private Label noml;
    @FXML
    private Label tell;
    @FXML
    private Label biol;
    @FXML
    private Button showfb;
    @FXML
    private Button btnab;
    @FXML
    private Button btndes;
    @FXML
    private Button suppFeedBtn;

    /**
     * Initializes the controller class.
     *
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
    }

    public boolean desab(int id) {
        try {
            AbonnementDAO abonnementdao = new AbonnementDAO();
            if (abonnementdao.dislayAll(id).isEmpty()) return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(ShowUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean abb(int id) {
        try {
            AbonnementDAO abonnementdao = new AbonnementDAO();
            if (abonnementdao.dislayAll(id).isEmpty()) return false;
        } catch (SQLException ex) {
            Logger.getLogger(ShowUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    void setIdd(Integer userId) {

        try {
            showfb.setOnAction(e -> {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShowUserFeeds.fxml"));
                    Region root = (Region) loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    DisplayUserFeedbackController spc = loader.getController();
                    spc.setIdu(userId);//envoie de l'ID de la photo
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            PhotoDAO ps1 = new PhotoDAO();
            Image image = new Image("/img/logo.png");
            phProfil.setImage(image);
            phProfil.setFitWidth(80);
            phProfil.setFitHeight(80);
            
            for (Photo j : ps1.displayByIdMembre(userId)) {//extrarire les photo apparartenant a un seul utilisateur
                ima.add(new Image(j.geturl()));//Arraylist avec les urls des photo
                imaa.add(new Label(j.gettitre()));//ArrayList avec les titres des photos
                imaaa.add(j.getid_photo());//ArrayList avec les id des photos
            }
            
            for (int i = 0; i < ima.size(); i++) {
                pics.add(new ImageView(ima.get(i)));//ArrayList des photo
                pics.get(i).setFitWidth(250);
                pics.get(i).setFitHeight(250);
            }
            
            for (int i = 0; i < ima.size(); i++) {
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(pics.get(i));
                int nb = imaaa.get(i);
                //borderPane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
                borderPane.setBottom(imaa.get(i));//insertion du titre de l'image au dessus de chaque photo
                BorderPane.setMargin(imaa.get(i), new Insets(10, 10, 10, 10));
                BorderPane.setAlignment(imaa.get(i), Pos.TOP_CENTER);
                imaa.get(i).setAlignment(Pos.CENTER);
                imaa.get(i).setStyle(cssLayout);
                imaa.get(i).setMinHeight(20);
                imaa.get(i).setMinWidth(250);
                BorderPane.setAlignment(imaa.get(i), Pos.TOP_CENTER);
                int nn = imaaa.get(i);
                borderPane.setOnMouseClicked(e -> {
                    PhotoDAO.id_photo = nb;
                    ////////jdiiiiiiiddddddd
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShowPhoto.fxml"));
                        Region root = (Region) loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        DisplayPhotoController spc = loader.getController();
                        spc.setIdd(nn);
                        
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                
                String s = imaa.get(i).getText();
                int n = imaaa.get(i);
                gp.add(borderPane, i + 2, 1);//inserer les images dans un gridpane avec une seul ligne et i colonnes
                sp.setContent(gp);
            }//inserer le gridpane dans un scrollpane pour pouvoir scroller les images
            
            feed.setOnMouseClicked(e -> {
                try {
                    if (feed.getText().equals("Ajouter Feedback")) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AjouterFeedback.fxml"));
                        Region root = (Region) loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        AddFeedbackController spc = loader.getController();
                        spc.setIdd(userId);//envoie de l'ID de la photo
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        FXMLLoader l = new FXMLLoader(getClass().getResource("/view/ModifierFeedback.fxml"));
                        Region root = (Region) l.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        EditFeedbackController spc = l.getController();
                        FeedbackDAO fd = FeedbackDAO.getInstance();
                        spc.transferMsg(fd.findFeedback());//envoie de l'ID de la photo
                        stage.setScene(scene);
                        stage.show();
                        
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            suppFeedBtn.setOnMouseClicked(e -> {
                FeedbackDAO fdao = FeedbackDAO.getInstance();
                fdao.delete();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Feedback supprimée avec succés!");
                alert.show();
                UserDAO ud = UserDAO.getInstance();
                if (ud.verify() != 0) {
                    suppFeedBtn.setVisible(true);
                    feed.setText("Modifier Feedback");
                } else {
                    suppFeedBtn.setVisible(false);
                    feed.setText("Ajouter Feedback");
                }
                
            });
            
            UserDAO ud = UserDAO.getInstance();
            if (ud.verify() != 0) {
                suppFeedBtn.setVisible(true);
                feed.setText("Modifier Feedback");
            } else {
                suppFeedBtn.setVisible(false);
                feed.setText("Ajouter Feedback");
            }
            User uu = new User();
            uu = ud.returnUserById(userId);
            nomaccount = uu.getUserNom() + "  " + uu.getUserPrenom();
            noml.setText(uu.getUserNom() + "  " + uu.getUserPrenom());
            biol.setText("" + uu.getUserEmail());
            tell.setText(uu.getUserBio());
            AbonnementDAO abonnementdao = new AbonnementDAO();
            
            if (desab(userId) == false) {
                btndes.setDisable(true);
            } else {
                btndes.setDisable(false);
            }
            
            if (abb(userId) == false) {
                btnab.setDisable(false);
            } else {
                btnab.setDisable(true);
            }
            
            String nom = UserDAO.connectedUser.getUserNom() + " " + UserDAO.connectedUser.getUserPrenom();
            btnab.setOnAction(e -> {
                Subscriber a = new Subscriber(nom, UserDAO.connectedUser.getUserId(), userId, nomaccount);
                abonnementdao.insert(a);
                btnab.setDisable(true);
                btndes.setDisable(false);
            });
            btndes.setOnAction(e -> {
                abonnementdao.delete(userId);
                btndes.setDisable(true);
                btnab.setDisable(false);
            });
        } catch (SQLException ex) {
            Logger.getLogger(ShowUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
