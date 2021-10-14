/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Connexion;

/**
 *
 */
public class EvenementDAO implements IEventDAO<Event> {

    private final Connection cnx;
    private static EvenementDAO instance;
    private Statement ste;
    private PreparedStatement pre;

    public EvenementDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }
    
    public static EvenementDAO getInstance() {
        if (instance == null) {
            try {
                instance = new EvenementDAO();
            } catch (SQLException ex) {
                Logger.getLogger(EvenementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public void ajouter(Event t) {
        try {
            pre = cnx.prepareStatement("INSERT INTO `library`.`evenement` ( `titre`, `contenu`, `image`, `dateajout`,`datemodif`,`idUser`,`nb_participer`) VALUES ( ?, ?, ?, ?, ?, ?, ?);");
            pre.setString(1, t.getTitre());
            pre.setString(2, t.getContenu());
            pre.setString(6, "0");
            pre.setString(7, "0");
            Date sDate = new java.sql.Date(t.getDateajout().getTime());
            Date sDate1 = new java.sql.Date(t.getDatemodif().getTime());
            pre.setString(3, t.getImage());
            pre.setDate(4, sDate);
            pre.setDate(5, sDate1);

            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvenementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean delete(int id) {
        try {
            if (chercher(id)) {
                pre = cnx.prepareStatement("delete from `library`.`evenement` where id  = (?);");
                pre.setInt(1, id);
                pre.execute();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean chercher(int id) {
        String req = "select * from evenement";
        List<Integer> list = new ArrayList<>();

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(rs.getInt(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list.contains(id);
    }

    public boolean update(Event t, int id) {
        if (chercher(id)) {

            try {
                pre = cnx.prepareStatement("UPDATE evenement SET   titre =?,contenu =?,image =?,dateajout =?,datemodif =? WHERE id = ?");
                pre.setString(1, t.getTitre());

                pre.setString(2, t.getContenu());

                Date sDate = new java.sql.Date(t.getDateajout().getTime());
                Date sDate1 = new java.sql.Date(t.getDatemodif().getTime());
                pre.setString(3, t.getImage());
                pre.setDate(4, sDate);
                pre.setDate(5, sDate1);
                pre.setInt(6, id);
                pre.executeUpdate();
                pre.executeUpdate();

                return true;
            } catch (SQLException ex) {
                Logger.getLogger(EvenementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public List<Event> readAll() {
        String req = "select * from evenement  ";
        List<Event> list = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                ImageView v = new ImageView();
                v.setImage(new Image("http://127.0.0.1/doc/" + rs.getString(4)));
                v.setFitWidth(100);
                v.setFitHeight(100);
                Event p2 = new Event(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6));
                p2.setPhoto(v);
                list.add(p2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
