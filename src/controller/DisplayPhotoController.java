package controller;

import dao.CommentaireDao;
import dao.PhotoDAO;
import dao.ReactionDAO;
import dao.UserDAO;
import entity.Comment;
import entity.Photo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 */
public class DisplayPhotoController implements Initializable {

    public int g;
    @FXML
    private Label tftitre;
    @FXML
    private Label tfcol;
    @FXML
    private ImageView ima;
    @FXML
    private Button btnProfil;
    @FXML
    private Button BtnRech;
    @FXML
    private ScrollPane sp;
    @FXML
    private Button btncomm;
    public ArrayList comments;
    public ArrayList<Label> comms = new ArrayList<>();
    public ArrayList<String> usernames = new ArrayList<>();
    public ArrayList<Label> imaa = new ArrayList<>();
    @FXML
    private ScrollPane sp1;
    public int idU = 2;
    @FXML
    private Button btnmodif;
    @FXML
    private Pane pane;
    @FXML
    private Button rec;
    @FXML
    private Button savebtn;

    public static void saveToFile(Image image) {
        File outputFile = new File("C:\\Users\\Public\\Desktop\\" + "downloaded.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private Button likeBtn;

    public void getcomms(int iidd) {

        try {
            CommentaireDao cc = new CommentaireDao();
            
            imaa.clear();
            for (Comment c : cc.displaycomms(iidd)) {
                
                imaa.add(new Label("  " + c.getnom_user() + " :    " + c.getcomm()));
                VBox vBox = new VBox();
                vBox.getChildren().clear();
                imaa.forEach(e -> vBox.getChildren().add(e));
                imaa.forEach(e -> e.setMinHeight(50));
                String cssLayout = "-fx-background-color: #2d424a;\n"
                        + "-fx-border-insets: 2;\n"
                        + "-fx-border-width: 2;\n"
                        + "-fx-text-fill:white;\n"
                        + "-fx-border-radius: 10;\n";
                String cLayout = "-fx-background-color:#1f1f22;\n";
                vBox.setStyle(cLayout);
                vBox.setSpacing(5);
                imaa.forEach(e -> e.setMaxWidth(Double.MAX_VALUE));
                imaa.forEach(e -> e.setStyle(cssLayout));
                sp.setContent(vBox);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplayPhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mycomms(int iidd) {

        try {
            CommentaireDao ccc = new CommentaireDao();
            
            comms.clear();
            for (Comment c : ccc.owndisplaycomms(iidd, UserDAO.connectedUser.getUserId())) {//////jddiiiiiid
                
                comms.add(new Label("  " + c.getnom_user() + " :    " + c.getcomm()));
                VBox vB = new VBox();
                vB.getChildren().clear();
                comms.forEach(e -> vB.getChildren().add(e));
                comms.forEach(e -> e.setMinHeight(50));
                String cssLayout = "-fx-background-color:#2d424a;\n"
                        + "-fx-border-insets: 2;\n"
                        + "-fx-border-width: 2;\n"
                        + "-fx-text-fill:white;\n"
                        + "-fx-border-radius: 10;\n";
                String cLayout = "-fx-background-color:#1f1f22;\n";
                vB.setStyle(cLayout);
                vB.setSpacing(5);
                comms.forEach(e -> e.setMaxWidth(Double.MAX_VALUE));
                comms.forEach(e -> e.setStyle(cssLayout));
                sp1.setContent(vB);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplayPhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ReactionDAO rdao = ReactionDAO.getInstance();
            if (rdao.verify(PhotoDAO.id_photo) != 0) {
                likeBtn.setText(rdao.likesCount(PhotoDAO.id_photo) + " | Je n'aime pas");
                likeBtn.setStyle("-fx-font: 22 arial; -fx-base: #9370DB;");

            } else {
                likeBtn.setText(rdao.likesCount(PhotoDAO.id_photo) + " | J'aime");
                likeBtn.setStyle("-fx-font: 22 arial; -fx-base: black;");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisplayPhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        likeBtn.setOnMouseClicked(event -> {

            try {
                ReactionDAO rdao = ReactionDAO.getInstance();
                rdao.add(PhotoDAO.id_photo);
                if (rdao.verify(PhotoDAO.id_photo) != 0) {
                    likeBtn.setText(rdao.likesCount(PhotoDAO.id_photo) + " | Je n'aime pas");
                    likeBtn.setStyle("-fx-font: 22 arial; -fx-base: #9370DB;");//#d11daa
                } else {
                    likeBtn.setText(rdao.likesCount(PhotoDAO.id_photo) + " | J'aime");
                    likeBtn.setStyle("-fx-font: 22 arial; -fx-base: black;");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DisplayPhotoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        try {
            Node n = (Node) FXMLLoader.load(getClass().getResource("/view/SideMenu.fxml"));
            pane.getChildren().add(n);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        BtnRech.setOnAction(e -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/RechercheView.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnProfil.setOnAction(e -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/view/ProfileView.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public int setIdd(int id) {

        try {
            PhotoDAO ps1 = new PhotoDAO();
            Photo photo1 = new Photo();
            photo1 = ps1.displayById(id);//recuperer la photo avec son id
            Image immg = new Image(photo1.geturl());//jdiiid
            savebtn.setOnAction(e -> {
                saveToFile(immg);
                JOptionPane.showMessageDialog(null, "Photo téléchargée");
            });//jdiiiid7
            g = id;
            tftitre.setText(photo1.gettitre());
            Image image = new Image(photo1.geturl());
            ima.setImage(image);
            ima.setFitHeight(200);
            ima.setFitWidth(200);
            ima.setOnMouseEntered(é -> {
                ima.setFitHeight(300);
                ima.setFitWidth(300);
            });
            ima.setOnMouseExited(é -> {
                ima.setFitHeight(200);
                ima.setFitWidth(200);
            });
            sp.setContent(null);
            getcomms(id);
            mycomms(id);
            
            //le Bouton Ajouter Commentaire Lambda expression 🙂🙂
            btncomm.setOnAction(e -> {
                CommentaireDao ccc = CommentaireDao.getInstance();
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("ajouter un commentaire");
                
                dialog.setContentText("ajoutez votre commentaire:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent() && !dialog.getResult().isEmpty()) {
                    Comment c1 = new Comment(result.get(), UserDAO.connectedUser.getUserNom() + " " + UserDAO.connectedUser.getUserPrenom(), id, UserDAO.connectedUser.getUserId());
                    ccc.insert(c1);
                    sp.setContent(null);
                    getcomms(id);
                    mycomms(id);
                } else {
                    JOptionPane.showMessageDialog(null, "veuillez saisir un commentaire");
                }
                
            });
            
            btnmodif.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GererComm.fxml"));
                    Region root = (Region) loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    GererCommController gcc = loader.getController();
                    gcc.setIdd(id, idU);//envoie de l'ID de la photo
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            rec.setOnAction(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreeReclamation.fxml"));
                    Region root = (Region) loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    AddReclamationController hspc = loader.getController();
                    hspc.setIdd(id);//envoie de l'ID de la photo
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplayPhotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
