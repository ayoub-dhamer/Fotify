/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FeedbackDAO;
import entity.Feedback;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import dao.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 */
public class AddFeedbackController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField feedback_description;
    @FXML
    private Button btn_ajouterFeedback;
    @FXML
    private Button btn_listerfeedback;
    @FXML
    private Rating feedback_rating;
    @FXML
    private Pane pane;
    @FXML
    private Button retour_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
            pane.getChildren().add(n);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        retour_btn.setOnMouseClicked(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/showuser.fxml"));
                Region root = (Region) loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                ShowUserController spc = loader.getController();
                spc.setIdd(UserDAO.userInteractor);//envoie de l'ID de la photo   
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        btn_listerfeedback.setOnAction(event -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShowUserFeeds.fxml"));
                Region root = (Region) loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                DisplayUserFeedbackController spc = loader.getController();
                spc.setIdu(UserDAO.userInteractor);//envoie de l'ID de la photo   
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    void setIdd(Integer userId) {
        btn_ajouterFeedback.setOnAction(event -> {
            if (isNullOrEmpty(feedback_description.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Champ Description doit etre remplie!");
                alert.show();
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                Date now = new Date();

                Feedback f = new Feedback(String.valueOf(now), feedback_description.getText(), (Integer) Math.round((float) feedback_rating.getRating()), userId, UserDAO.connectedUser.getUserId());
                FeedbackDAO fdao;
                fdao = FeedbackDAO.getInstance();
                fdao.add(f);
                feedback_description.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Feedback insérée avec succés!");
                alert.show();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShowUserFeeds.fxml"));
                    Region root = (Region) loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    DisplayUserFeedbackController spc = loader.getController();
                    spc.setIdu(userId);//envoie de l'ID de la photo   
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AddFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
}
