/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class UserDAO implements IUserDAO<User> {

    private static UserDAO instance;
    private final Connection cnx;
    private final Statement ste;
    public static User connectedUser;
    public static int userInteractor;
    private static final String ALGORITHM = "md5";
    private static final String DIGEST_STRING = "HG58YZ3CR9";
    private static final String CHARSET_UTF_8 = "utf-8";
    private static final String SECRET_KEY_ALGORITHM = "DESede";
    private static final String TRANSFORMATION_PADDING = "DESede/CBC/PKCS5Padding";
    private ResultSet rs;
    public String pwd;
    
    /**
     *
     * @param message
     * @return
     */
    @Override
    public String encrypt(String message) {
        String passwordToHash = message;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    public UserDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            try {
                instance = new UserDAO();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public Object SignIn(String email, String password) {
        try {
            pwd = encrypt(password);
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String req = "select * from user where email = '" + email + "' and password ='" + pwd + "'";
        User u = new User();
        try {
            ResultSet rs = ste.executeQuery(req);
            if (rs.next()) {
                u.setUserId(rs.getInt(1));
                u.setUserUsername(rs.getString(2));
                u.setUserNom(rs.getString(3));
                u.setUserPrenom(rs.getString(4));
                u.setUserBio(rs.getString(5));
                u.setUserAge(rs.getInt(6));
                u.setUserTel(rs.getInt(7));
                u.setUserEmail(rs.getString(8));
                u.setUserPassword(rs.getString(9));
                u.setUserType(rs.getString(10));
            } else {
                u = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    @Override
    public void SignUp(User o) {
        String req = "insert into user (username, nom, prenom, BIO, Age, tel, email, password, type, enable) values (?, ?,?,?,?,?,?,?,?, ?)";
        PreparedStatement statement;
        try {
            statement = cnx.prepareStatement(req);
            statement.setString(1, o.getUserNom() + ' ' + o.getUserPrenom());
            statement.setString(2, o.getUserNom());
            statement.setString(3, o.getUserPrenom());
            statement.setString(4, o.getUserBio());
            statement.setInt(5, o.getUserAge());
            statement.setInt(6, o.getUserTel());
            statement.setString(7, o.getUserEmail());
            statement.setString(8, o.getUserPassword());
            statement.setString(9, o.getUserType());
            statement.setInt(10, 0);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void delete(User u) {
        String req = "delete from user where idU = " + u.getUserId();
        User p = returnUserById(u.getUserId());
        if (p != null) {
            try {
                ste.executeUpdate(req);
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("n'existe pas");
        }
    }

    public ObservableList<User> displayAlll() {
        String s = "Membre";
        String req = "select * from user where type= '" + s + "'and idU<>  " + UserDAO.connectedUser.getUserId();
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setUserId(rs.getInt(1));
                p.setUserNom(rs.getString(2));
                p.setUserPrenom(rs.getString(3));
                p.setUserBio(rs.getString(4));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public User returnUserById(int idM) {
        String req = "select * from user where idU =" + idM;
        User p = new User();
        try {
            rs = ste.executeQuery(req);
            rs.next();
            p.setUserId(rs.getInt(1));
            p.setUserUsername(rs.getString(2));
            p.setUserNom(rs.getString(3));
            p.setUserPrenom(rs.getString(4));
            p.setUserBio(rs.getString(5));
            p.setUserAge(rs.getInt(6));
            p.setUserTel(rs.getInt(7));
            p.setUserEmail(rs.getString(8));
            p.setUserPassword(rs.getString(9));
            p.setUserType(rs.getString(10));
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public Integer starsCount(int nb) {
        String req = "select count(*) as total from feedback where id_membre = " + UserDAO.connectedUser.getUserId() + " AND rating = " + nb;
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public Integer sCount(int nb) {
        String req = "select count(*) as total from feedback where id_membre = " + this.userInteractor + " AND rating = " + nb;
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public Integer feedbackCount() {
        String req = "select count(*) as total from feedback where id_membre = " + UserDAO.connectedUser.getUserId();
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public Integer feedbackCount(int id) {
        String req = "select count(*) as total from feedback where id_membre = " + id;
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public Integer imageCount() {
        String req = "select count(*) as total from photo where idU = " + UserDAO.connectedUser.getUserId();
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public Integer imageCount(int id) {
        String req = "select count(*) as total from photo where idU = " + id;
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public Integer verify() {
        String req = "select count(*) as total from feedback where id_membre = " + this.userInteractor + " AND id_abonne = " + UserDAO.connectedUser.getUserId();
        int i = 0;
        try {
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    @Override
    public ObservableList<User> displayAll() {
        String req = "select * from user";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setUserId(rs.getInt(1));
                p.setUserUsername(rs.getString(2));
                p.setUserNom(rs.getString(3));
                p.setUserPrenom(rs.getString(4));
                p.setUserBio(rs.getString(5));
                p.setUserAge(rs.getInt(6));
                p.setUserTel(rs.getInt(7));
                p.setUserEmail(rs.getString(8));
                p.setUserPassword(rs.getString(9));
                p.setUserType(rs.getString(10));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
