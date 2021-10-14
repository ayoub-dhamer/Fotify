/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import javafx.collections.ObservableList;

/**
 *
 * @param <T>
 */
public interface IUserDAO<T> {
    
    String encrypt(String message);

    Object SignIn(String email, String password);

    void SignUp(T o);

    void delete(User u);

    ObservableList<T> displayAll();

    T returnUserById(int id);

}
