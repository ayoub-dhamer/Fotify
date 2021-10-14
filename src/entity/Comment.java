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
 * @author YACINE
 */
public class Comment {

    private SimpleIntegerProperty id_comm;
    private SimpleStringProperty comm;
    private SimpleStringProperty nom_user;
    private SimpleIntegerProperty id_photo;
    private SimpleIntegerProperty idU;

    public Comment() {
    }

    ;
public Comment(int id_comm, String comm, String nom_user, int id_photo, int idU) {
        this.id_comm = new SimpleIntegerProperty(id_comm);
        this.comm = new SimpleStringProperty(comm);
        this.nom_user = new SimpleStringProperty(nom_user);
        this.id_photo = new SimpleIntegerProperty(id_photo);
        this.id_comm = new SimpleIntegerProperty(idU);
    }

    ;
public Comment(int id_comm, String comm) {
        this.id_comm = new SimpleIntegerProperty(id_comm);
        this.comm = new SimpleStringProperty(comm);

    }

    ;
public Comment(String comm, String nom_user, int id_photo) {

        this.comm = new SimpleStringProperty(comm);
        this.nom_user = new SimpleStringProperty(nom_user);
        this.id_photo = new SimpleIntegerProperty(id_photo);
    }

    ;
public Comment(String comm, String nom_user, int id_photo, int idU) {

        this.comm = new SimpleStringProperty(comm);
        this.nom_user = new SimpleStringProperty(nom_user);
        this.id_photo = new SimpleIntegerProperty(id_photo);
        this.idU = new SimpleIntegerProperty(idU);
    }

    ;


public int getid_comm() {
        return id_comm.get();
    }

    public void setid_comm(int id_comm) {
        this.id_comm = new SimpleIntegerProperty(id_comm);
    }

    public String getcomm() {
        return comm.get();
    }

    public void setcomm(String comm) {
        this.comm = new SimpleStringProperty(comm);
    }

    public String getnom_user() {
        return nom_user.get();
    }

    public void setnom_user(String nom_user) {
        this.nom_user = new SimpleStringProperty(nom_user);
    }

    public int getid_photo() {
        return id_photo.get();
    }

    public void setid_photo(int id_photo) {
        this.id_photo = new SimpleIntegerProperty(id_photo);
    }

    public int getidUser() {
        return idU.get();
    }

    public void setidUser(int idU) {
        this.idU = new SimpleIntegerProperty(idU);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id_comm);
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
        final Comment other = (Comment) obj;
        if (!Objects.equals(this.id_comm, other.id_comm)) {
            return false;
        }
        return true;
    }

}
