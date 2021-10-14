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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 */
public class EditFeedbackController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField feedback_description;
    @FXML
    private Rating feedback_rating;
    @FXML
    private Button btn_modifierFeedback;

    private Feedback feedback;
    @FXML
    private Pane pane;
    @FXML
    private Button retour_btn;

    public void transferMsg(Feedback f) {
        feedback = f;
        feedback_description.setText(feedback.getContenuFeedBack());
        feedback_rating.setRating(f.getRating());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

        btn_modifierFeedback.setOnAction(event -> {
            if (isNullOrEmpty(feedback_description.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Champ Description doit etre remplie!");
                alert.show();
            } else {

                FeedbackDAO fdao;
                fdao = FeedbackDAO.getInstance();
                fdao.update(feedback_description.getText(), (Integer) Math.round((float) feedback_rating.getRating()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Feedback modifié avec succés!");
                alert.show();

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
                    Logger.getLogger(AddFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //feedback_description.setText("");
            }
        });
    }
}
