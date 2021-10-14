/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FeedbackDAO;
import dao.PhotoDAO;
import dao.UserDAO;
import entity.Feedback;
import entity.Photo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 */
public class ProfileMembreController implements Initializable {

    @FXML
    private Label nomlabel;
    @FXML
    private Button btnAjPh;
    @FXML
    private Button btnrech;
    @FXML
    private ScrollPane sp;
    @FXML
    private ImageView phProfil;
    @FXML
    private GridPane gp;
    public ImageView iv;
    public BorderPane bp;
    private HBox hb;
    public ArrayList<String> imagess = new ArrayList<>();
    public ArrayList<Image> ima = new ArrayList<>();
    public ArrayList<Label> imaa = new ArrayList<>();
    public ArrayList<Integer> imaaa = new ArrayList<>();
    public ArrayList<ImageView> pics = new ArrayList();
    public ArrayList<String> pic = new ArrayList();
    public int Id_membre = 1;

    @FXML
    private ScrollPane scroll_feedback;
    @FXML
    private VBox vb;

    private ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();
    @FXML
    private Button btn_addFeedback;
    @FXML
    private PieChart feedback_stats;
    @FXML
    private Label feedback_nb;
    @FXML
    private Label images_nb;
    @FXML
    private Button btn_deleteFeedback;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            
            UserDAO ud = UserDAO.getInstance();
            if (ud.verify() == 1) {
                btn_addFeedback.setText("Modifier Commentaire");
            } else {
                btn_deleteFeedback.setVisible(false);
            }
            feedback_nb.setText("" + ud.feedbackCount());
            images_nb.setText("" + ud.imageCount());
            ObservableList<PieChart.Data> pc = FXCollections.observableArrayList(
                    new PieChart.Data("1 star rating", ud.starsCount(1)),
                    new PieChart.Data("2 star rating", ud.starsCount(2)),
                    new PieChart.Data("3 star rating", ud.starsCount(3)),
                    new PieChart.Data("4 star rating", ud.starsCount(4)),
                    new PieChart.Data("5 star rating", ud.starsCount(5))
            );
            feedback_stats.setData(pc);
            feedback_stats.getData().stream().map((data) -> {
                data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue() + " cases");
                return data;
            }).forEachOrdered((data) -> {
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                    JOptionPane.showMessageDialog(null, "Country -- " + data.getName() + "\nTotal Cases --" + (int) data.getPieValue());
                });
            });
            
            btn_addFeedback.setOnMouseClicked((MouseEvent e) -> {
                try {
                    if (btn_addFeedback.getText().equals("Modifier Commentaire")) {
                        FXMLLoader l = new FXMLLoader(getClass().getResource("/view/ModifierFeedback.fxml"));
                        Parent root = l.load();
                        
                        EditFeedbackController ModifyFeedbackController = l.getController();
                        FeedbackDAO fd = FeedbackDAO.getInstance();
                        ModifyFeedbackController.transferMsg(fd.findFeedback());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Second Window");
                        stage.show();
                    } else {
                        Parent addFeedbackPage = FXMLLoader.load(ProfileMembreController.this.getClass().getResource("/view/AjouterFeedback.fxml"));
                        Scene scene = new Scene(addFeedbackPage);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ProfileMembreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btn_deleteFeedback.setOnMouseClicked((MouseEvent e) -> {
                try {
                    FeedbackDAO fd = FeedbackDAO.getInstance();
                    fd.delete();
                    Parent profileMembrePage = FXMLLoader.load(ProfileMembreController.this.getClass().getResource("/view/ProfileMembre.fxml"));
                    Scene scene = new Scene(profileMembrePage);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileMembreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            Node no;
            try {
                Label title_id = new Label("ID");
                title_id.setMinWidth(40);
                title_id.setMaxWidth(40);
                title_id.setAlignment(Pos.BASELINE_RIGHT);
                Label title_date = new Label("Date d'ajout");
                title_date.setMinWidth(250);
                title_date.setMaxWidth(250);
                title_date.setAlignment(Pos.CENTER);
                Label title_description = new Label("Description");
                title_description.setMinWidth(400);
                title_description.setMaxWidth(400);
                title_description.setAlignment(Pos.CENTER);
                Label title_rating = new Label("Notation");
                title_rating.setMinWidth(80);
                title_rating.setMaxWidth(80);
                
                no = (Node) FXMLLoader.load(getClass().getResource("/view/Item.fxml"));
                ((HBox) no).setFillHeight(true);
                ((HBox) no).setAlignment(Pos.CENTER);
                ((HBox) no).setMaxSize(1000, 100);
                ((HBox) no).setMaxWidth(1000);
                ((HBox) no).setMinWidth(1000);
                
                //((HBox) n).setStyle("-fx-background-color: #FFFACD;");
                ((HBox) no).getChildren().addAll(title_id, title_date, title_description, title_rating);
                vb.getChildren().add(no);
            } catch (IOException ex) {
                Logger.getLogger(DisplayFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                
                FeedbackDAO fb = FeedbackDAO.getInstance();
                feedbacks = fb.feedbackMembre();
                TextField field = new TextField();
                Node[] nodes = new Node[feedbacks.size()];
                for (int i = 0; i < nodes.length; i++) {
                    Label label_id = new Label("" + feedbacks.get(i).getId());
                    label_id.setMinWidth(30);
                    label_id.setMaxWidth(30);
                    Label label_date = new Label("" + feedbacks.get(i).getDateAjoutFeedBack());
                    label_date.setMinWidth(250);
                    label_date.setMaxWidth(250);
                    Label label_description = new Label("" + feedbacks.get(i).getContenuFeedBack());
                    label_description.setMinWidth(400);
                    label_description.setMaxWidth(400);
                    Label label_rating = new Label("" + feedbacks.get(i).getRating());
                    label_rating.setMinWidth(10);
                    label_rating.setMaxWidth(10);
                    
                    Image image = new Image("/utils/star.png");
                    ImageView iv = new ImageView();
                    iv.setFitWidth(20);
                    iv.setPreserveRatio(true);
                    iv.setSmooth(true);
                    iv.setCache(true);
                    
                    iv.setImage(image);
                    nodes[i] = (Node) FXMLLoader.load(getClass().getResource("/view/Item.fxml"));
                    ((HBox) nodes[i]).setFillHeight(true);
                    
                    ((HBox) nodes[i]).setAlignment(Pos.CENTER);
                    ((HBox) nodes[i]).setMaxSize(1000, 100);
                    ((HBox) nodes[i]).setMaxWidth(1000);
                    ((HBox) nodes[i]).setMinWidth(1000);
                    
                    ((HBox) nodes[i]).getChildren().addAll(label_id, label_date, label_description, label_rating, iv);
                    vb.getChildren().add(nodes[i]);
                }
            } catch (IOException ex) {
                Logger.getLogger(DisplayFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PhotoDAO ps1 = null;
            ps1 = new PhotoDAO();
            InputStream stream;
            
            Image image = new Image("/utils/user.png");
            phProfil.setImage(image);
            
            for (Photo j : ps1.displayByIdMembre(Id_membre)) {
                ima.add(new Image(j.geturl()));
                imaa.add(new Label(j.gettitre()));
                imaaa.add(j.getid_photo());
            }
            
            for (int i = 0; i < ima.size(); i++) {
                pics.add(new ImageView(ima.get(i)));
                pics.get(i).setFitWidth(250);
                pics.get(i).setFitHeight(250);
            }
            
            for (int i = 0; i < ima.size(); i++) {
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(pics.get(i));
                borderPane.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
                borderPane.setBottom(imaa.get(i));
                BorderPane.setMargin(imaa.get(i), new Insets(10, 10, 10, 10));
                BorderPane.setAlignment(imaa.get(i), Pos.TOP_CENTER);
                String s = imaa.get(i).getText();
                int n = imaaa.get(i);
                
                borderPane.setOnMouseClicked(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdatePhotoView.fxml"));
                        Region root = (Region) loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        UpdatePhotoController spc = loader.getController();
                        spc.setIdd(n);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ProfileMembreController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
                gp.add(borderPane, i + 2, 1);
                
                sp.setContent(gp);
            }
            
            btnAjPh.setOnAction(e -> {
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("/view/AjouterPhotoView.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileMembreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnrech.setOnAction(e -> {
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("/view/RechercheView.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileMembreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfileMembreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
