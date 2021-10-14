/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 */
public class ResetController implements Initializable {

    @FXML
    private PasswordField newpass;

    @FXML
    private Button res;

    @FXML
    private PasswordField pass;

    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    public String user;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public String encrypt(String message) throws Exception {
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

    void setIdd(String text) {
        res.setOnMouseClicked(event -> {
            try {
                if (pass.getText().equals(newpass.getText())) {
                    PreparedStatement pst;
                    try {
                        String updateQuery = " UPDATE userr SET password = ? WHERE email=?";
                        cnx = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
                        pst = cnx.prepareStatement(updateQuery);
                        pst.setString(1, encrypt(newpass.getText()));
                        pst.setString(2, text);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Reset Successfully");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Password do not match");
                }
            } catch (Exception ex) {
                Logger.getLogger(ResetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
