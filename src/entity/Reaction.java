/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author ayoub
 */
public class Reaction {

    private int idReaction;
    private int idPhoto;
    private int idUserP;
    private int idUserR;
    private Date dateReaction;

    public Reaction(int idReaction, int idPhoto, int idUserP, int idUserR, Date dateReaction) {
        this.idReaction = idReaction;
        this.idPhoto = idPhoto;
        this.idUserP = idUserP;
        this.idUserR = idUserR;
        this.dateReaction = dateReaction;
    }

    public Reaction() {
    }

    public int getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(int idReaction) {
        this.idReaction = idReaction;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public int getIdUserP() {
        return idUserP;
    }

    public void setIdUserP(int idUserP) {
        this.idUserP = idUserP;
    }

    public int getIdUserR() {
        return idUserR;
    }

    public void setIdUserR(int idUserR) {
        this.idUserR = idUserR;
    }

    public Date getDateReaction() {
        return dateReaction;
    }

    public void setDateReaction(Date dateReaction) {
        this.dateReaction = dateReaction;
    }

    @Override
    public String toString() {
        return "Reaction{" + "idReaction=" + idReaction + ", idPhoto=" + idPhoto + ", idUserP=" + idUserP + ", idUserR=" + idUserR + ", dateReaction=" + dateReaction + '}';
    }

}
