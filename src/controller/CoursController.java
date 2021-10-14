/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import entity.Course;
import dao.CourDAO;

/**
 * FXML Controller class
 *
 */
public class CoursController implements Initializable {

    int u = 2;

    @FXML
    private AnchorPane an;

    @FXML
    private TextField tftitle;

    @FXML
    private TextField tfauthor;

    @FXML
    private TextArea txtdesc;
    @FXML
    private TextField s;

    @FXML
    private TextField txtsearch;

    @FXML
    private Hyperlink ref;

    @FXML
    private Hyperlink my;

    @FXML
    private Button btninsert;

    @FXML
    private Button btnupdate;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnchoose;

    @FXML
    private Button btnbrowse;

    @FXML
    private Button btntype;

    @FXML
    private TableView<Course> tvCours;

    @FXML
    private TableColumn<Course, String> colTitle;

    @FXML
    private TableColumn<Course, String> colAuthor;

    @FXML
    private TableColumn<Course, String> coldesc;

    @FXML
    private TableColumn<Course, String> coldate;
    @FXML
    private TableColumn<Course, String> colcategory;

    @FXML
    private TableColumn<Course, String> colfile;

    @FXML
    private TableColumn<Course, String> colimage;

    @FXML
    private ImageView imgview;

    private ListData listdata = new ListData();
    private final Desktop desktop = Desktop.getDesktop();
    private ObservableList<Course> list = FXCollections.observableArrayList();
    private FileChooser filechooser = new FileChooser();

    public String path;
    public String url;
    public String image;
    public String doc;

    @FXML
    private Label photo;

    @FXML
    private Label lblfile;

    @FXML
    private TextField lbl;

    @FXML
    private Label n;

    @FXML
    private Label lbltitle;

    @FXML
    private Label lblcategory;

    @FXML
    private Label lbldate;
    @FXML
    private Label lblauthor;
    @FXML
    private Label lbldescription;

    @FXML
    private DatePicker d;

    @FXML
    private ComboBox<String> c;

    @FXML
    private Hyperlink allcours;

    @FXML
    private Hyperlink logout;

    @FXML
    private void event(javafx.scene.input.MouseEvent event) {

        ObservableList<Course> cours = tvCours.getSelectionModel().getSelectedItems();

        tftitle.setText(cours.get(0).getTitle());
        tfauthor.setText(cours.get(0).getAuthor());
        txtdesc.setText(cours.get(0).getDescription());
        s.setText(cours.get(0).getDate());
        n.setText(cours.get(0).getCategory());
        lbl.setText(cours.get(0).getUrl());
        photo.setText(cours.get(0).getImage());
        imgview.setImage(new Image(cours.get(0).getImage()));

    }

    @FXML
    void Delete(ActionEvent event) {
        try {
            ObservableList<Course> cours = tvCours.getSelectionModel().getSelectedItems();
            CourDAO pdao = CourDAO.getInstance();
            pdao.delete(cours.get(0));
            tvCours.getItems().removeAll(tvCours.getSelectionModel().getSelectedItem());
            JOptionPane.showMessageDialog(null, "Cours delete success");
            tftitle.clear();
            txtdesc.clear();
            s.clear();
            lbl.clear();
            imgview.setImage(null);
            n.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select item to delete");

        }

    }

    @FXML
    void Insert(ActionEvent event) {

        if (tftitle.getText().isEmpty() || tfauthor.getText().isEmpty() || s.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in the fields");
        } else {
            try {

                Course C = new Course(tftitle.getText(), tfauthor.getText(), txtdesc.getText(), s.getText(), n.getText(), this.path, photo.getText(), UserDAO.connectedUser.getUserId());

                CourDAO pdao = CourDAO.getInstance();
                pdao.insert(C);
                tvCours.getItems().add(C);

                JOptionPane.showMessageDialog(null, "Cours add success");
                tftitle.clear();
                txtdesc.clear();
                s.clear();
                lbl.clear();
                imgview.setImage(null);
                n.setText("");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please fill in the fields");

            }

        }

    }

    @FXML
    void Update(ActionEvent event) {

        try {
            ObservableList<Course> cours = tvCours.getSelectionModel().getSelectedItems();
            CourDAO pdao = CourDAO.getInstance();
            Course cours1 = new Course(cours.get(0).getId(), tftitle.getText(), tfauthor.getText(), txtdesc.getText(), s.getText(), n.getText(), lbl.getText(), photo.getText());

            pdao.update(cours1);

            JOptionPane.showMessageDialog(null, "Cours updated with success");
            tftitle.clear();
            txtdesc.clear();
            s.clear();
            lbl.clear();
            imgview.setImage(null);
            n.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select item to update");

        }

    }

    @FXML
    void browse() {
        btnbrowse.setOnMouseClicked(e -> {
            try {

                FileChooser v = new FileChooser();
                v.setTitle("Open IMAGE file...");
                v.getExtensionFilters().add(new FileChooser.ExtensionFilter("IMAGE Files", "*.jpg", "*.jpeg"));

                File infile = v.showOpenDialog(null);

                Image img = SwingFXUtils.toFXImage(ImageIO.read(infile), null);
                imgview.setImage(img);
                doc = "http://localhost/image/" + infile.getName();
                Path from = Paths.get(infile.toURI());
                Path to = Paths.get("C:\\xampp\\htdocs\\image" + infile.getName());
                CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES};
                Path temp = Files.copy(from, to, options);
                this.image = doc;
                photo.setText(doc);
            } catch (IOException ex) {

            };

        });

    }

    @FXML
    void choose(ActionEvent event) {
        try {

            FileChooser r = new FileChooser();
            r.setTitle("Open file...");
            r.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            r.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
            r.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT Files", "*.txt"));
            File h = r.showOpenDialog(null);
            path = "http://localhost/image/" + h.getName();
            Path from = Paths.get(h.toURI());
            Path to = Paths.get("C:\\xampp\\htdocs\\image" + h.getName());
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES};
            Path temp = Files.copy(from, to, options);
            this.url = path;

            lbl.setText(path);

        } catch (Exception ex) {

        }

        // For multiple selection :)
        /* List <File> fileList = r.showOpenMultipleDialog(null);
        if (fileList!=null){
           fileList.stream().forEach(selectedFile -> {
        try {
                desktop.open(selectedFile);
          }catch (IOException ex) {
                
           }
        });
        }*/
    }

    @FXML
    void show(ActionEvent event) {
        LocalDate f = d.getValue();
        s.setText(f.toString());

    }

    @FXML
    void selection(ActionEvent event) {
        String y = c.getSelectionModel().getSelectedItem();
        n.setText(y);

    }

    @FXML
    void refresh() {
        ref.setOnMouseClicked(event -> {
            tfauthor.setText("");
            tftitle.setText("");
            n.setText("");
            s.setText("");
            txtdesc.setText("");
            imgview.setImage(null);
            lbl.setText("");

        });
    }

    @FXML
    void my() {
        CourDAO pdao = CourDAO.getInstance();
        my.setOnMouseClicked(e -> {

            tvCours.setItems(pdao.displayByAu(UserDAO.connectedUser.getUserId()));

            colTitle.setCellValueFactory(cell -> cell.
                    getValue().getTitleProperty());
            colAuthor.setCellValueFactory(cell -> cell.
                    getValue().getAuthorProperty());
            coldesc.setCellValueFactory(cell -> cell.
                    getValue().getDescriptionProperty());
            coldate.setCellValueFactory(cell -> cell.
                    getValue().getDateProperty());
            colcategory.setCellValueFactory(cell -> cell.
                    getValue().getCategoryProperty());
            colfile.setCellValueFactory(cell -> cell.
                    getValue().getUrlProperty());
            colimage.setCellValueFactory(cell -> cell.
                    getValue().getImageProperty());

        });

    }

    @FXML
    void all() {
        allcours.setOnMouseClicked(event -> {
            tvCours.setItems(listdata.getCours());

            colTitle.setCellValueFactory(cell -> cell.
                    getValue().getTitleProperty());
            colAuthor.setCellValueFactory(cell -> cell.
                    getValue().getAuthorProperty());
            coldesc.setCellValueFactory(cell -> cell.
                    getValue().getDescriptionProperty());
            coldate.setCellValueFactory(cell -> cell.
                    getValue().getDateProperty());
            colcategory.setCellValueFactory(cell -> cell.
                    getValue().getCategoryProperty());
            colfile.setCellValueFactory(cell -> cell.
                    getValue().getUrlProperty());
            colimage.setCellValueFactory(cell -> cell.
                    getValue().getImageProperty());

        });

    }

    @FXML
    void type() {
        btntype.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/type.fxml"));
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

    @FXML
    void serach() {

        FilteredList<Course> filteredData = new FilteredList<>(listdata.getCours(), e -> true);
        txtsearch.setOnKeyReleased(event -> {
            txtsearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Course>) cours -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (cours.getTitle().toLowerCase().contains(newValue)) {
                        return true;
                    } else if (cours.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cours.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cours.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cours.getDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cours.getUrl().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cours.getImage().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Course> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tvCours.comparatorProperty());
            tvCours.setItems(sortedData);

        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tvCours.setItems(listdata.getCours());

        colTitle.setCellValueFactory(cell -> cell.
                getValue().getTitleProperty());
        colAuthor.setCellValueFactory(cell -> cell.
                getValue().getAuthorProperty());
        coldesc.setCellValueFactory(cell -> cell.
                getValue().getDescriptionProperty());
        coldate.setCellValueFactory(cell -> cell.
                getValue().getDateProperty());
        colcategory.setCellValueFactory(cell -> cell.
                getValue().getCategoryProperty());
        colfile.setCellValueFactory(cell -> cell.
                getValue().getUrlProperty());
        colimage.setCellValueFactory(cell -> cell.
                getValue().getImageProperty());

        CourDAO pdao = CourDAO.getInstance();
        ArrayList<Course> c33 = new ArrayList<>();
        c33 = (ArrayList<Course>) pdao.displayByA(u);
        tfauthor.setText(UserDAO.connectedUser.getUserNom() + " " + UserDAO.connectedUser.getUserPrenom());

        ObservableList<String> combo = FXCollections.observableArrayList("Basic", "Portraits", "Lightroom", "Photoshop", "Numérique", "Commercial");
        c.setItems(combo);

        lbltitle.setTooltip(new Tooltip("Give a Title"));
        lblauthor.setTooltip(new Tooltip("Cours Author"));
        lbldescription.setTooltip(new Tooltip("Describe here"));
        lbldate.setTooltip(new Tooltip("Pick a Date"));
        lblcategory.setTooltip(new Tooltip("Select your Category"));
        lblfile.setTooltip(new Tooltip("Select your Course"));
        txtsearch.setTooltip(new Tooltip("Search a course"));
        ref.setTooltip(new Tooltip("Reload the table"));
        my.setTooltip(new Tooltip("Your course list"));
        btntype.setTooltip(new Tooltip("Go and write your course"));
        btnchoose.setTooltip(new Tooltip("Press and import your file"));
        btninsert.setTooltip(new Tooltip("Click to insert"));
        btndelete.setTooltip(new Tooltip("Click to delete"));
        btnupdate.setTooltip(new Tooltip("Click to update"));

        logout.setOnMouseClicked(event -> {
            try {

                Parent type = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
                Scene scene = new Scene(type);
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
