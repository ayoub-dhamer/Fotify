/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import entity.Course;
import dao.CourDAO;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 */
public class HomeController implements Initializable {

    @FXML
    private FlowPane fp;
    private ArrayList<ImageView> img = new ArrayList<>();
    private ArrayList<Hyperlink> hp = new ArrayList<>();
    public ArrayList<Label> title = new ArrayList<>();
    public ArrayList<Image> arl = new ArrayList<>();
    public ArrayList<Label> pnl = new ArrayList<>();
    private ArrayList<Course> cours = new ArrayList<>();
    private ArrayList<String> xx = new ArrayList<>();

    private List<Course> list = new ArrayList();
    private List<Course> listt = new ArrayList();
    @FXML
    private TextField search;

    private ListData listdata = new ListData();

    private Hyperlink b;
    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
     */
    @FXML
    void search() {

    }

    void retour() {
        b.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/firstView.fxml"));
                Scene scene = new Scene(type);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Fotify");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CoursController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            
            try {
                Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
                pane.getChildren().add(n);
            } catch (IOException ex) {
                Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            CourDAO pdao = new CourDAO();
            
            for (Course c : pdao.displayAllList()) {
                title.add(new Label(c.getTitle()));
                arl.add(new Image(c.getImage()));
                hp.add(new Hyperlink(c.getUrl()));
                
                xx.add(c.getUrl());
                
                pnl.add(new Label("Par : Mr " + c.getAuthor()));
            }
            final WebView wb = new WebView();
            
            for (int i = 0; i < title.size(); i++) {
                img.add(new ImageView(arl.get(i)));
                img.get(i).setFitWidth(250);
                img.get(i).setFitHeight(180);
                
                title.get(i).setWrapText(true);
                title.get(i).setMaxWidth(150);
                title.get(i).setTextFill(Color.WHITE);
                
                pnl.get(i).setWrapText(true);
                pnl.get(i).setMaxWidth(150);
                pnl.get(i).setTextFill(Color.WHITE);
                
                String x = xx.get(i);
                
                hp.get(i).setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/webview.fxml"));
                        Region root = (Region) loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        WebviewController spc = loader.getController();
                        spc.setIdd(x);//envoie de l'ID de la photo
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            for (int i = 0; i < title.size(); i++) {
                BorderPane bp = new BorderPane();
                VBox vb = new VBox();
                vb.getChildren().addAll(title.get(i), pnl.get(i));
                vb.setAlignment(Pos.CENTER_RIGHT);
                vb.setSpacing(20);
                
                bp.setLeft(img.get(i));
                bp.setCenter(vb);
                
                bp.setBottom(hp.get(i));
                //bp.setRight(pnl.get(i));
                bp.setPrefSize(400, 200);
                
                BorderPane.setAlignment(img.get(i), Pos.CENTER);
                bp.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                fp.getChildren().addAll(bp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
