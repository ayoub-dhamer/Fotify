/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PhotoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class ListPhoto<Photo> {

    private ObservableList<Photo> phs = FXCollections.observableArrayList();

    public ListPhoto() {
        PhotoDAO pdao = PhotoDAO.getInstance();
        phs = (ObservableList<Photo>) pdao.displayAlll();
    }

    public ObservableList<Photo> getPhoto() {
        return phs;
    }

}
