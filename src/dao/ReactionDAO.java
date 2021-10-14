/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reaction;
import utils.Connexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ReactionDAO implements IReactionDAO<Reaction> {

    //private static ReclamationDao instance;
    private final Connection cnx;
    private final Statement ste;
    private static ReactionDAO instance;

    public ReactionDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();

    }

    public static ReactionDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReactionDAO();
        }
        return instance;
    }

    @Override
    public void add(int id) {
        if (this.verify(id) != 0) {
            String req = "delete from reaction where id_userP = " + UserDAO.userInteractor + " And id_userR = " + UserDAO.connectedUser.getUserId() +" And id_photo = " + id;

            try {

                ste.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ReactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            Date now = new Date();
            String req = "insert into reaction (id_photo, id_userP, id_userR, date) values ('" + id + "','" + UserDAO.userInteractor + "','" + UserDAO.connectedUser.getUserId() + "','" + String.valueOf(now) + "')";
            try {
                ste.executeUpdate(req);
            } catch (SQLException ex) {
                Logger.getLogger(ReactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /*
    @Override
    public ObservableList<Reaction> list() {
        String req = "select * from reaction";
        ObservableList<Reaction> list = FXCollections.observableArrayList();

        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Reaction r = new Reaction();
                r.setId(rs.getInt("id_reaction"));
                r.setDateAjoutReaction(rs.getDate("date_ajout"));
                r.setContenuReaction(rs.getString("description"));
                r.setRating(rs.getInt("rating"));
                r.setIdImage(rs.getInt("id_image"));

                list.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private Reaction displayById(int id) {
        String req = "select * from reaction where id =" + id;
        Reaction r = new Reaction();
        try {
            ResultSet rs = ste.executeQuery(req);
            rs.next();
            r.setId(rs.getInt("id_reaction"));
            r.setDateAjoutReaction(rs.getDate("date_ajout"));
            r.setContenuReaction(rs.getString("description"));
            r.setRating(rs.getInt("rating"));
            r.setIdImage(rs.getInt("id_image"));
        } catch (SQLException ex) {
            Logger.getLogger(ReactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
     */
    public Integer verify(int id) {
        String req = "select count(*) as total from reaction where id_userP = " + UserDAO.userInteractor + " AND id_userR = " + UserDAO.connectedUser.getUserId() + " And id_photo = " + id;
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public Integer likesCount(int id) {
        String req = "select count(*) as total from reaction where id_photo = " + id;
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

}
