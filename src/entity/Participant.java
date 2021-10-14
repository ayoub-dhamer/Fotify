/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 */
public class Participant {

    private int id_participer;
    private int id_evenement;
    private int id_user;
    private Date date;

    public Participant(int id_evenement, int id_user, Date date) {
        this.id_evenement = id_evenement;
        this.id_user = id_user;
        this.date = date;
    }

    public Participant(int id_participer, int id_evenement, int id_user, Date date) {
        this.id_participer = id_participer;
        this.id_evenement = id_evenement;
        this.id_user = id_user;
        this.date = date;
    }

    public int getId_participer() {
        return id_participer;
    }

    public void setId_participer(int id_participer) {
        this.id_participer = id_participer;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Participer{" + "id_participer=" + id_participer + ", id_evenement=" + id_evenement + ", id_user=" + id_user + ", date=" + date + '}';
    }

}
