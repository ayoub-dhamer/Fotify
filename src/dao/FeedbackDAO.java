/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Feedback;
import utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class FeedbackDAO implements IFeedbackDAO<Feedback> {

    private static FeedbackDAO instance;
    private final Connection cnx;
    private final Statement ste;

    public FeedbackDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    public static FeedbackDAO getInstance() {
        if (instance == null) {
            try {
                instance = new FeedbackDAO();
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public void add(Feedback f) {
        String req = "insert into feedback (date,description,rating,id_abonne,id_membre) values (?,?,?,?,?)";
        PreparedStatement statement;
        try {
            statement = cnx.prepareStatement(req);
            statement.setString(1, String.valueOf(f.getDateAjoutFeedBack()));
            statement.setString(2, f.getContenuFeedBack());
            statement.setInt(3, f.getRating());
            statement.setInt(4, f.getIdMembre());
            statement.setInt(5, f.getIdMembreAbonne());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete() {
        String req = "delete from feedback where id_membre = " + UserDAO.userInteractor + " AND id_abonne = " + UserDAO.connectedUser.getUserId();
        try {
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean update(String des, int rating) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        String req = "UPDATE feedback SET date = '" + String.valueOf(now) + "', description = '" + des + "', rating = '" + rating + "' WHERE id_abonne = " + UserDAO.connectedUser.getUserId() + " AND id_membre = " + UserDAO.userInteractor;
        try {
            if (ste.executeUpdate(req) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ObservableList<Feedback> list() {
        String req = "select * from feedback";
        ObservableList<Feedback> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setId(rs.getInt("id_feedback"));
                f.setDateAjoutFeedBack(rs.getString("date"));
                f.setContenuFeedBack(rs.getString("description"));
                f.setRating(rs.getInt("rating"));
                f.setIdMembreAbonne(rs.getInt("id_abonne"));
                f.setIdMembre(rs.getInt("id_membre"));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ObservableList<Feedback> feedbackMembre() {
        String req = "select * from feedback where id_membre = " + UserDAO.connectedUser.getUserId();
        ObservableList<Feedback> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setId(rs.getInt("id_feedback"));
                f.setDateAjoutFeedBack(rs.getString("date"));
                f.setContenuFeedBack(rs.getString("description"));
                f.setRating(rs.getInt("rating"));
                f.setIdMembreAbonne(rs.getInt("id_abonne"));
                f.setIdMembre(rs.getInt("id_membre"));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ObservableList<Feedback> feedMembre(int idc) {
        String req = "select * from feedback where id_membre = " + idc;
        ObservableList<Feedback> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setId(rs.getInt("id_feedback"));
                f.setDateAjoutFeedBack(rs.getString("date"));
                f.setContenuFeedBack(rs.getString("description"));
                f.setRating(rs.getInt("rating"));
                f.setIdMembreAbonne(rs.getInt("id_abonne"));
                f.setIdMembre(rs.getInt("id_membre"));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Feedback returnFeedbackById(int id) {
        String req = "select * from feedback where id_feedback =" + id;
        Feedback f = new Feedback();
        try {
            ResultSet rs = ste.executeQuery(req);
            rs.next();
            f.setId(rs.getInt("id_feedback"));
            f.setDateAjoutFeedBack(rs.getString("date"));
            f.setContenuFeedBack(rs.getString("description"));
            f.setRating(rs.getInt("rating"));
            f.setIdMembreAbonne(rs.getInt("id_abonne"));
            f.setIdMembre(rs.getInt("id_membre"));
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    @Override
    public Feedback findFeedback() {
        String req = "select * from feedback where id_membre =" + UserDAO.userInteractor + " AND id_abonne =" + UserDAO.connectedUser.getUserId();
        Feedback f = new Feedback();
        try {
            ResultSet rs = ste.executeQuery(req);
            rs.next();
            f.setId(rs.getInt("id_feedback"));
            f.setDateAjoutFeedBack(rs.getString("date"));
            f.setContenuFeedBack(rs.getString("description"));
            f.setRating(rs.getInt("rating"));
            f.setIdMembreAbonne(rs.getInt("id_abonne"));
            f.setIdMembre(rs.getInt("id_membre"));
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
}
