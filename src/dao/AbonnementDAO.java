/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Subscriber;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Connexion;

/**
 *
 */
public class AbonnementDAO implements ISubscriberDAO<Subscriber> {

    private static AbonnementDAO instance;
    private final Statement ste;
    private final Connection cnx;
    private ResultSet rs;

    public AbonnementDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    public static AbonnementDAO getInstance() {
        if (instance == null) {
            try {
                instance = new AbonnementDAO();
            } catch (SQLException ex) {
                Logger.getLogger(AbonnementDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public void insert(Subscriber o) {

        String req = "insert into abonnement (Anom,idA,idU,nomA) values ('" + o.getAnomProperty().get() + "','" + o.getIdA() + "','" + o.getIdU() + "','" + o.getnomAProperty().get() + "')";
        try {
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public List<Subscriber> displayAll() {
        String req = "select * from abonnement where idU= " + UserDAO.connectedUser.getUserId();
        List<Subscriber> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Subscriber p = new Subscriber();
                p.setIdA(rs.getInt(1));
                p.setAnom(rs.getString(2));
                p.setIdU(rs.getInt(3));
                p.setnomA(rs.getString(4));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Subscriber> dislayAll(int id) {
        String req = "select * from abonnement where idU= '" + UserDAO.connectedUser.getUserId() + "'and idA = " + id;
        List<Subscriber> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Subscriber p = new Subscriber();
                p.setIdA(rs.getInt(1));
                p.setAnom(rs.getString(2));
                p.setIdU(rs.getInt(3));
                p.setnomA(rs.getString(4));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Subscriber> felowers() { 
        String req = "select * from abonnement where idU= " + UserDAO.connectedUser.getUserId();
        List<Subscriber> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Subscriber p = new Subscriber();
                p.setId(rs.getInt(1));
                p.setAnom(rs.getString(2));
                p.setIdU(rs.getInt(3));
                p.setIdA(rs.getInt(4));
                p.setnomA(rs.getString(5));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Subscriber> felowing() {
        String req = "select * from abonnement where idA= " + UserDAO.connectedUser.getUserId();
        List<Subscriber> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Subscriber p = new Subscriber();
                p.setId(rs.getInt(1));
                p.setAnom(rs.getString(2));
                p.setIdU(rs.getInt(3));
                p.setIdA(rs.getInt(4));
                p.setnomA(rs.getString(5));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Subscriber dislay(int id) {
        String req = "select * from abonnement where idU= '" + UserDAO.connectedUser.getUserId() + "'and idA = " + id;
        Subscriber list = new Subscriber();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Subscriber p = new Subscriber();
                p.setIdA(rs.getInt(1));
                p.setAnom(rs.getString(2));
                p.setIdU(rs.getInt(3));
                p.setnomA(rs.getString(4));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void delete(int id) {
        String req = "delete from abonnement where idU= '" + UserDAO.connectedUser.getUserId() + "'and idA = " + id;
        Subscriber c = dislay(id);
        if (c != null) {
            try {

                ste.executeUpdate(req);
            } catch (SQLException ex) {
                Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("n'existe pas");
        }
    }
}
