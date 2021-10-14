/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Participant;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Connexion;

/**
 *
 */
public class ParticiperDAO implements IParticipantDAO<Participant> {

    private Connection cnx;
    private static ParticiperDAO instance;
    private Statement ste;
    private PreparedStatement pre;

    public ParticiperDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }
    
    public static ParticiperDAO getInstance() {
        if (instance == null) {
            try {
                instance = new ParticiperDAO();
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public void ajouter(Participant t) {
        try {
            if (!chercher(t.getId_participer())) {
                pre = cnx.prepareStatement("INSERT INTO `library`.`participer` ( `id_evenement`, `id_user`, `date`) VALUES ( ?, ?, ?);");
                pre.setInt(1, t.getId_evenement());
                pre.setInt(2, t.getId_user());
                Date sDate = new java.sql.Date(t.getDate().getTime());

                pre.setDate(3, sDate);
                pre.executeUpdate();
                System.out.println("ajout valide");
            } else {
                System.out.println("ajout invalide");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticiperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean delete(int id) {
        try {
            if (chercher(id)) {
                pre = cnx.prepareStatement("delete from `library`.`participer` where id_participer  = (?);");
                pre.setInt(1, id);

                pre.execute();
                System.out.println("valide");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticiperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("n'existe pas");
        return false;
    }

    public boolean delete1(int id) {

        try {
            pre = cnx.prepareStatement("delete from `library`.`participer` where id_participer  = (?);");
            pre.setInt(1, id);

            pre.execute();
            System.out.println("valide");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ParticiperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean chercher(int id) {
        String req = "select * from participer";
        List<Integer> list = new ArrayList<>();

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(rs.getInt(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticiperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list.contains(id);
    }

    public boolean chercher_ajout(Participant t) {
        String req = "select * from participer where id_evenement= '" + t.getId_evenement() + "' AND id_user ='" + t.getId_user() + "'";
        List<Participant> list = new ArrayList<>();

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
                list.add(new Participant(rs.getInt(1), rs.getInt(2), rs.getInt(3), d1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticiperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (list.size() != 0);
    }

    public List<Participant> readAll() {
        String req = "select * from participer  ";
        List<Participant> list = new ArrayList<>();

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {

                java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
                list.add(new Participant(rs.getInt(1), rs.getInt(2), rs.getInt(3), d1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParticiperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
