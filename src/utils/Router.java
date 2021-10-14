/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 */
public class Router {

    private static String viewPath = "/view/";

    public static String getView(String folder, String name) {
        return viewPath + folder + "/" + name + ".fxml";
    }

}
