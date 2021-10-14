/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author hp
 */
public class User {

    private SimpleIntegerProperty userId;
    private SimpleStringProperty userUsername;
    private SimpleStringProperty userNom;
    private SimpleStringProperty userPrenom;
    private SimpleStringProperty userBio;
    private SimpleIntegerProperty userAge;
    private SimpleIntegerProperty userTel;
    private SimpleStringProperty userEmail;
    private SimpleStringProperty userPassword;
    private SimpleStringProperty userType;

    public User(Integer userId, String userNom, String userPrenom, String userBio, Integer userAge, Integer userTel, String userEmail, String userPassword, String userType) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userUsername = new SimpleStringProperty(userNom + ' ' + userPrenom);
        this.userNom = new SimpleStringProperty(userNom);
        this.userPrenom = new SimpleStringProperty(userPrenom);
        this.userBio = new SimpleStringProperty(userBio);
        this.userAge = new SimpleIntegerProperty(userAge);
        this.userTel = new SimpleIntegerProperty(userTel);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.userType = new SimpleStringProperty(userType);
    }

    public User(Integer userId, String userNom, String userPrenom, String userBio) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userUsername = new SimpleStringProperty(userNom + ' ' + userPrenom);
        this.userNom = new SimpleStringProperty(userNom);
        this.userPrenom = new SimpleStringProperty(userPrenom);
        this.userBio = new SimpleStringProperty(userBio);
    }

    public User(String userNom, String userPrenom, String userBio, Integer userTel) {

        this.userNom = new SimpleStringProperty(userNom);
        this.userPrenom = new SimpleStringProperty(userNom + ' ' + userPrenom);
        this.userBio = new SimpleStringProperty(userBio);
        this.userTel = new SimpleIntegerProperty(userTel);
    }

    public User() {
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(Integer userId) {
        this.userId = new SimpleIntegerProperty(userId);
    }

    public String getUserUsername() {
        return userUsername.get();
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = new SimpleStringProperty(userUsername);
    }

    public String getUserNom() {
        return userNom.get();
    }

    public void setUserNom(String userNom) {
        this.userNom = new SimpleStringProperty(userNom);
    }

    public String getUserPrenom() {
        return userPrenom.get();
    }

    public void setUserPrenom(String userPrenom) {
        this.userPrenom = new SimpleStringProperty(userPrenom);
    }

    public String getUserBio() {
        return userBio.get();
    }

    public void setUserBio(String userBio) {
        this.userBio = new SimpleStringProperty(userBio);
    }

    public int getUserAge() {
        return userAge.get();
    }

    public void setUserAge(Integer userAge) {
        this.userAge = new SimpleIntegerProperty(userAge);
    }

    public int getUserTel() {
        return userTel.get();
    }

    public void setUserTel(Integer userTel) {
        this.userTel = new SimpleIntegerProperty(userTel);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = new SimpleStringProperty(userEmail);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = new SimpleStringProperty(userPassword);
    }

    public String getUserType() {
        return userType.get();
    }

    public void setUserType(String userType) {
        this.userType = new SimpleStringProperty(userType);
    }

    @Override
    public String toString() {
        return "User{" + "Id=" + userId + ", Username =" + userUsername + ", Name=" + userNom + ", Last Name=" + userPrenom + ", Bio=" + userBio + ", Age=" + userAge + ", Phone=" + userTel + ", Email=" + userEmail + ", Password=" + userPassword + ", Type=" + userType + '}';
    }

}
