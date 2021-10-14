/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @param <User>
 */
public class ListUser<User> {
        private ObservableList<User> user = FXCollections.observableArrayList();

    public ListUser()  {
        UserDAO pdao;
        pdao = UserDAO.getInstance();
        user = (ObservableList<User>) pdao.displayAll();
        
    }

    public ObservableList<User> getUser() {
        return user;
    }
    
}
