/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Comment;
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
public class CommentaireDao implements ICommentDAO<Comment> {

    private static CommentaireDao instance;
    private final Statement ste;
    private final Connection cnx;
    private ResultSet rs;

    public CommentaireDao() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    public static CommentaireDao getInstance() {
        if (instance == null) {
            try {
                instance = new CommentaireDao();
            } catch (SQLException ex) {
                Logger.getLogger(CommentaireDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public void insert(Comment o) {
        String req = "insert into commentaire (comm,nom_user,id_photo,idU) values ('" + o.getcomm() + "','" + o.getnom_user() + "','" + o.getid_photo() + "','" + o.getidUser() + "')";
        try {
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Comment o) {
        String req = "delete from commentaire where id_comm=" + o.getid_comm();
        Comment c = displayById(o.getid_photo());

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

    @Override
    public List<Comment> displayAll() {
        String req = "select * from commentaire";
        List<Comment> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Comment p = new Comment();
                p.setid_comm(rs.getInt(1));
                p.setcomm(rs.getString(2));
                p.setnom_user(rs.getString(3));
                p.setid_photo(rs.getInt(4));

                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Comment displayById(int id_comm) {
        String req = "select * from commentaire where id_comm =" + id_comm;
        Comment p = new Comment();
        try {
            rs = ste.executeQuery(req);

            rs.next();
            p.setid_comm(rs.getInt(1));
            p.setcomm(rs.getString(2));
            p.setnom_user(rs.getString(3));
            p.setid_photo(rs.getInt(4));
            p.setidUser(rs.getInt(5));

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public boolean update(Comment os) {
        String qry = "UPDATE commentaire SET comm = '" + os.getcomm() + "'WHERE id_comm = " + os.getid_comm();

        try {
            if (ste.executeUpdate(qry) > 0) {

                return true;

            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Comment> displaycomms(int id_photo) {
        String req = "select * from commentaire where id_photo =" + id_photo;
        List<Comment> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Comment p = new Comment();
                p.setid_comm(rs.getInt(1));
                p.setcomm(rs.getString(2));
                p.setnom_user(rs.getString(3));
                p.setid_photo(rs.getInt(4));

                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public List<Comment> owndisplaycomms(int id_photo, int idU) {
        String req = "select * from commentaire where id_photo ='" + id_photo + "'and idU=" + idU;
        List<Comment> list = new ArrayList<>();

        try {
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Comment p = new Comment();
                p.setid_comm(rs.getInt(1));
                p.setcomm(rs.getString(2));
                p.setnom_user(rs.getString(3));
                p.setid_photo(rs.getInt(4));

                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }
}
