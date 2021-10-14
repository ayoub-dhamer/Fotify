/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 */
public class Reclamation {

    private int id;
    private String sujet;
    private String description;
    private String etat;
    private String date_creation;
    private int user_id;
    private int photo_id;
    private Photo photo;
    private User user;

    public Reclamation() {
    }

    public Reclamation(String sujet, String description, String etat, String date_creation, int user_id, int photo_id, Photo photo, User user) {
        this.sujet = sujet;
        this.description = description;
        this.etat = etat;
        this.date_creation = date_creation;
        this.user_id = user_id;
        this.photo_id = photo_id;
        this.photo = photo;
        this.user = user;
    }

    public Reclamation(int id, String sujet, String description, String etat, String date_creation, int user_id, int photo_id, Photo photo, User user) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.etat = etat;
        this.date_creation = date_creation;
        this.user_id = user_id;
        this.photo_id = photo_id;
        this.photo = photo;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", sujet=" + sujet + ", description=" + description + ", etat=" + etat + ", date_creation=" + date_creation + ", user_id=" + user_id + ", photo_id=" + photo_id + '}';
    }

}
