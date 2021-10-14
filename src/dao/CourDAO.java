/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Course;
import java.sql.Connection;
import utils.Connexion;

/**
 *
 */
public class CourDAO implements ICourseDAO<Course> {

    private static CourDAO instance;
    private final Statement ste;
    private final Connection cnx;
    private ResultSet rs;

    public CourDAO() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    public static CourDAO getInstance() {
        if (instance == null) {
            try {
                instance = new CourDAO();
            } catch (SQLException ex) {
                Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public void insert(Course c) {
        String req = "insert into cours (title,author,description,date,category,url,image,idU) values ('" + c.getTitle() + "','" + c.getAuthor() + "','" + c.getDescription() + "','" + c.getDate() + "','" + c.getCategory() + "','" + c.getUrl() + "','" + c.getImage() + "','" + c.getIdU() + "')";
        try {
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Course c) {
        String req = "delete from cours where id=" + c.getId();
        Course p = displayById(c.getId());

        if (p != null) {
            try {

                ste.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("n'existe pas");
        }
    }

    @Override
    public boolean update(Course p) {
        String qry = "UPDATE cours SET title = '" + p.getTitle() + "', author = '" + p.getAuthor() + "',description = '" + p.getDescription() + "' ,date = '" + p.getDate() + "' ,category = '" + p.getCategory() + "' , url = '" + p.getUrl() + "',image = '" + p.getImage() + "' WHERE id = " + p.getId();

        try {
            if (ste.executeUpdate(qry) > 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ObservableList<Course> displayAll() {
        String req = "select * from cours";
        ObservableList<Course> list = FXCollections.observableArrayList();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Course p = new Course();
                p.setId(rs.getInt(1));
                p.setTitle(rs.getString(2));
                p.setAuthor(rs.getString(3));
                p.setDescription(rs.getString(4));
                p.setDate(rs.getString(5));
                p.setCategory(rs.getString(6));

                p.setUrl(rs.getString(8));
                p.setImage(rs.getString(7));

                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Course> displayAllList() {
        String req = "select * from cours";
        List<Course> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Course p = new Course();
                p.setId(rs.getInt(1));
                p.setTitle(rs.getString("title"));
                p.setAuthor(rs.getString(3));
                p.setDescription(rs.getString(4));
                p.setDate(rs.getString(5));
                p.setCategory(rs.getString(6));

                p.setUrl(rs.getString(8));
                p.setImage(rs.getString(7));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Course displayById(int id) {
        String req = "select * from cours where id =" + id;
        Course p = new Course();
        try {
            rs = ste.executeQuery(req);
            // while(rs.next()){
            rs.next();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setAuthor(rs.getString("author"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getString("date"));
            p.setCategory(rs.getString("category"));

            p.setUrl(rs.getString("url"));
            p.setImage(rs.getString("image"));
            //}  
        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public ObservableList<Course> displayByAu(int user) {

        String req = "select * from cours where idU =" + user;
        ObservableList<Course> list = FXCollections.observableArrayList();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Course p = new Course();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setAuthor(rs.getString("author"));
                p.setDescription(rs.getString("description"));
                p.setDate(rs.getString("date"));
                p.setCategory(rs.getString("category"));

                p.setUrl(rs.getString("url"));
                p.setImage(rs.getString("image"));
                p.setIdU(rs.getInt("idU"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Course> displayByA(int user) {

        String req = "select * from cours where idU =" + user;
        List<Course> list = new ArrayList<>();
        Course p = new Course();
        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {

                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setAuthor(rs.getString("author"));
                p.setDescription(rs.getString("description"));
                p.setDate(rs.getString("date"));
                p.setCategory(rs.getString("category"));

                p.setUrl(rs.getString("url"));
                p.setImage(rs.getString("image"));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
