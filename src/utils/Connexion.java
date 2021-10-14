/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 */
public class Connexion {

    private static Connexion instance;
    private Connection cnx;
    String url = "jdbc:mysql://localhost:3306/library";

    private Connexion() {
        try {
            cnx = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public static Connexion getInstance() {
        if (instance == null) {
            instance = new Connexion();
        }
        return instance;
    }

    public Connection getConnection() {
        return cnx;
    }

}
