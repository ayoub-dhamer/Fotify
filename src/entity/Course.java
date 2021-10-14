/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Amine
 */
public class Course {

    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty description;
    private SimpleStringProperty date;
    private SimpleStringProperty category;
    private SimpleStringProperty url;
    private SimpleStringProperty image;
    private SimpleIntegerProperty idU;

    public Course() {
    }

    public Course(String title, String author, String description, String date, String category) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);

    }

    public Course(String title, String author, String description, String date, String category, String url, String image, int idU) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);
        this.url = new SimpleStringProperty(url);
        this.image = new SimpleStringProperty(image);
        this.idU = new SimpleIntegerProperty(idU);
    }

    public Course(String title, String author, String description, String date, String category, String url, String image) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);
        this.url = new SimpleStringProperty(url);
        this.image = new SimpleStringProperty(image);

    }

    public Course(int id, String title, String author, String description, String date, String category, String url, String image, int idU) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);

        this.url = new SimpleStringProperty(url);
        this.image = new SimpleStringProperty(image);
        this.idU = new SimpleIntegerProperty(idU);
    }

    public Course(int id, String title, String author, String description, String date, String category, String url, String image) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);

        this.url = new SimpleStringProperty(url);
        this.image = new SimpleStringProperty(image);

    }

    public Course(int id, String title, String author, String description, String date, String category) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.category = new SimpleStringProperty(category);

    }

    public String getImage() {
        return image.get();

    }

    public void setImage(String image) {
        this.image = new SimpleStringProperty(image);
    }

    public String getUrl() {
        return url.get();

    }

    public void setUrl(String url) {
        this.url = new SimpleStringProperty(url);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public int getIdU() {
        return idU.get();
    }

    public void setIdU(int idU) {
        this.idU = new SimpleIntegerProperty(idU);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setAuthor(String author) {
        this.author = new SimpleStringProperty(author);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleIntegerProperty getIdProperty() {
        return id;
    }

    public SimpleIntegerProperty getIdUProperty() {
        return idU;
    }

    public SimpleStringProperty getTitleProperty() {
        return title;
    }

    public SimpleStringProperty getAuthorProperty() {
        return author;
    }

    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }

    public SimpleStringProperty getDateProperty() {
        return date;
    }

    public SimpleStringProperty getCategoryProperty() {
        return category;
    }

    public SimpleStringProperty getUrlProperty() {
        return url;
    }

    public SimpleStringProperty getImageProperty() {
        return image;
    }

   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
