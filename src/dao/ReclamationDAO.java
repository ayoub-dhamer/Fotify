/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reclamation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Connexion;

/**
 *
 */
public class ReclamationDAO implements IReclamationDAO<Reclamation> {

    private static ReclamationDAO instance;
    private Statement ste;
    private ResultSet rs;
    private final Connection cnx;

    public ReclamationDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    public static ReclamationDAO getInstance() {
        if (instance == null) {
            try {
                instance = new ReclamationDAO();
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public void insert(Reclamation r) {

        String req = "insert into reclamation (sujet,description,etat,user_id,photo_id) values ('" + r.getSujet() + "','" + r.getDescription() + "','" + r.getEtat().toString() + "'," + r.getUser_id() + "," + r.getPhoto_id() + ")";
        try {
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Reclamation r) {

        String req = "delete from reclamation where id=" + r.getId();
        Reclamation x = displayById(r.getId());

        if (x != null) {
            try {

                ste.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ReclamationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("n'existe pas");
        }
    }

    @Override
    public boolean update(Reclamation r) {
        String qry = "UPDATE reclamation SET sujet = '" + r.getSujet() + "', description = '" + r.getDescription() + "', etat = '" + r.getEtat().toString() + "' WHERE id = " + r.getId();

        try {
            if (ste.executeUpdate(qry) > 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Reclamation> displayAll() {
        String req = "select r.*,u.*,p.* from reclamation r inner join user u on r.user_id = u.id inner join photo p on p.id = r.photo_id ";
        List<Reclamation> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt(1));
                r.setSujet(rs.getString("sujet"));
                r.setDescription(rs.getString("description"));
                r.setEtat(rs.getString("etat"));
                r.setDate_creation(rs.getString("date_creation"));
                // get user

            }

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Reclamation displayById(int id) {
        String req = "select * from reclamation where id =" + id;
        Reclamation r = new Reclamation();
        try {
            rs = ste.executeQuery(req);
            // while(rs.next()){
            if (!rs.next()) {
                return null;
            }
            r.setId(rs.getInt("id"));
            r.setSujet(rs.getString("sujet"));
            r.setDescription(rs.getString("description"));
            r.setEtat(rs.getString("etat"));

            r.setUser_id(rs.getInt("user_id"));
            // r.setMembre(new Membre(rs.getInt("membre_id"), rs.getString("nom"), rs.getString("prenom")));
            //}  
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public List<Reclamation> getReclamationByUser(int user_id) {
        String req = " select r.*,p.* from reclamation r inner join photo p on p.id = r.photo_id where user_id = " + user_id + " order by r.date_creation desc";
        List<Reclamation> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt(1));
                r.setSujet(rs.getString("sujet"));
                r.setDescription(rs.getString("description"));
                r.setEtat(rs.getString("etat"));
                r.setDate_creation(rs.getString("date_creation"));

                list.add(r);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public List<Reclamation> isplayById(int idd) {
        String req = "select * from reclamation where user_id =" + idd;
        List<Reclamation> list = new ArrayList<>();
        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Reclamation p = new Reclamation();
                p.setId(rs.getInt(1));
                p.setSujet(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setEtat(rs.getString(4));
                p.setDate_creation(rs.getString(5));
                p.setUser_id(rs.getInt(6));
                p.setPhoto_id(rs.getInt(7));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ObservableList<Reclamation> playById() {
        String req = "select * from reclamation";
        ObservableList<Reclamation> list = FXCollections.observableArrayList();
        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Reclamation p = new Reclamation();
                p.setId(rs.getInt(1));
                p.setSujet(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setEtat(rs.getString(4));
                p.setDate_creation(rs.getString(5));
                p.setUser_id(rs.getInt(6));
                p.setPhoto_id(rs.getInt(7));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
