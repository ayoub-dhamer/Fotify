/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Course;
import dao.CourDAO;

/**
 *
 */
public class ListData {

    /**
     * The data as an observable list of Cours.
     */
    private ObservableList<Course> cours = FXCollections.observableArrayList();

    public ListData() {

        CourDAO pdao = CourDAO.getInstance();
        cours = pdao.displayAll();
    }

    public ObservableList<Course> getCours() {
        return cours;
    }

}
