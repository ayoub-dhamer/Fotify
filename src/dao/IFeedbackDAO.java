/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Feedback;
import javafx.collections.ObservableList;

/**
 *
 * @param <T>
 */
public interface IFeedbackDAO<T> {

    void add(T f);

    void delete();

    boolean update(String s, int r);

    ObservableList<T> list();

    ObservableList<T> feedbackMembre();
    
    Feedback findFeedback();
    
    Feedback returnFeedbackById(int id);
    
    ObservableList<Feedback> feedMembre(int idc);
}
