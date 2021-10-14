/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author repalce by your name
 */
public class Feedback {

    private int id;
    private String dateAjoutFeedBack;
    private String contenuFeedBack;
    private Integer rating;
    private int idMembreAbonne;
    private int idMembre;

    public Feedback(int id, String dateAjoutFeedBack, String contenuFeedBack, int rating, int idMembreAbonne, int idMembre) {
        this.id = id;
        this.dateAjoutFeedBack = dateAjoutFeedBack;
        this.contenuFeedBack = contenuFeedBack;
        this.rating = rating;
        this.idMembreAbonne = idMembreAbonne;
        this.idMembre = idMembre;
    }

    public Feedback(String dateAjoutFeedBack, String contenuFeedBack, int rating, int idMembreAbonne, int idMembre) {

        this.dateAjoutFeedBack = dateAjoutFeedBack;
        this.contenuFeedBack = contenuFeedBack;
        this.rating = rating;
        this.idMembreAbonne = idMembreAbonne;
        this.idMembre = idMembre;
    }

    public Feedback(int id, String dateAjoutFeedBack, String contenuFeedBack, int rating) {
        this.id = id;
        this.dateAjoutFeedBack = dateAjoutFeedBack;
        this.contenuFeedBack = contenuFeedBack;
        this.rating = rating;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateAjoutFeedBack() {
        return dateAjoutFeedBack;
    }

    public void setDateAjoutFeedBack(String dateAjoutFeedBack) {
        this.dateAjoutFeedBack = dateAjoutFeedBack;
    }

    public String getContenuFeedBack() {
        return contenuFeedBack;
    }

    public void setContenuFeedBack(String contenuFeedBack) {
        this.contenuFeedBack = contenuFeedBack;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIdMembreAbonne() {
        return idMembreAbonne;
    }

    public Feedback() {
    }

    public void setIdMembreAbonne(int idMembreAbonne) {
        this.idMembreAbonne = idMembreAbonne;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    @Override
    public String toString() {
        return "FeedBackDAO{" + "id=" + id + ", dateAjoutFeedBack=" + dateAjoutFeedBack + ", contenuFeedBack=" + contenuFeedBack + ", rating=" + rating + ", idMembreAbonne=" + idMembreAbonne + ", idMembre=" + idMembre + '}';
    }

}
