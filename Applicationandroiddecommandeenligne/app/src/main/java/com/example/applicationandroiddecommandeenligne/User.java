package com.example.applicationandroiddecommandeenligne;

import java.util.Date;
import java.util.Objects;

public class User {
    int ID_User;
    String Nom;
    String Prenom;
    String Date_Naissance;
    int CIN;
    String Email;
    String Login;
    String Password;

    public User() {
    }

    public User(int ID_User, String Nom, String Prenom, String Date_Naissance, int CIN, String Email, String Login, String Password) {
        this.ID_User = ID_User;
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.Date_Naissance = Date_Naissance;
        this.CIN = CIN;
        this.Email = Email;
        this.Login = Login;
        this.Password = Password;
    }

    public int getID_User() {
        return ID_User;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getDate_Naissance() {
        return Date_Naissance;
    }

    public int getCIN() {
        return CIN;
    }

    public String getEmail() {
        return Email;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setDate_Naissance(String date_Naissance) {
        Date_Naissance = date_Naissance;
    }

    public void setCIN(int CIN) {
        this.CIN = CIN;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID_User == user.ID_User &&
                CIN == user.CIN &&
                Objects.equals(Nom, user.Nom) &&
                Objects.equals(Prenom, user.Prenom) &&
                Objects.equals(Date_Naissance, user.Date_Naissance) &&
                Objects.equals(Email, user.Email) &&
                Objects.equals(Login, user.Login) &&
                Objects.equals(Password, user.Password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_User, Nom, Prenom, Date_Naissance, CIN, Email, Login, Password);
    }
}
