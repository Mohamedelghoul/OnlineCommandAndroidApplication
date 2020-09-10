package com.example.applicationandroiddecommandeenligne;

import java.util.Date;

public class Commande {
    int ID_Cde;
    int Num_Cde;
    int ID_Client;
    String Adr_Client;
    String Date_Cde;
    Status Status_Cde;
    int Total_HT;
    int Total_TVA;
    int Total_TTC;
    String Articles_In_Commande;

    public Commande(int ID_Cde, int num_Cde, int ID_Client, String adr_Client, String date_Cde, Status status_Cde, int total_HT, int total_TVA, int total_TTC,String Articles_In_Commande) {
        this.ID_Cde = ID_Cde;
        Num_Cde = num_Cde;
        this.ID_Client = ID_Client;
        Adr_Client = adr_Client;
        Date_Cde = date_Cde;
        Status_Cde = status_Cde;
        Total_HT = total_HT;
        Total_TVA = total_TVA;
        Total_TTC = total_TTC;
        this.Articles_In_Commande = Articles_In_Commande;
    }

    public int getID_Cde() {
        return ID_Cde;
    }

    public int getNum_Cde() {
        return Num_Cde;
    }

    public int getID_Client() {
        return ID_Client;
    }

    public String getAdr_Client() {
        return Adr_Client;
    }

    public String getDate_Cde() {
        return Date_Cde;
    }

    public Status getStatus_Cde() {
        return Status_Cde;
    }

    public int getTotal_HT() {
        return Total_HT;
    }

    public int getTotal_TVA() {
        return Total_TVA;
    }

    public int getTotal_TTC() {
        return Total_TTC;
    }

    public String getArticles_In_Commande() {
        return Articles_In_Commande;
    }

    public void setID_Cde(int ID_Cde) {
        this.ID_Cde = ID_Cde;
    }

    public void setNum_Cde(int num_Cde) {
        Num_Cde = num_Cde;
    }

    public void setID_Client(int ID_Client) {
        this.ID_Client = ID_Client;
    }

    public void setAdr_Client(String adr_Client) {
        Adr_Client = adr_Client;
    }

    public void setDate_Cde(String date_Cde) {
        Date_Cde = date_Cde;
    }

    public void setStatus_Cde(Status status_Cde) {
        Status_Cde = status_Cde;
    }

    public void setTotal_HT(int total_HT) {
        Total_HT = total_HT;
    }

    public void setTotal_TVA(int total_TVA) {
        Total_TVA = total_TVA;
    }

    public void setTotal_TTC(int total_TTC) {
        Total_TTC = total_TTC;
    }

    public void setArticles_In_Commande(String articles_In_Commande) {
        Articles_In_Commande = articles_In_Commande;
    }
}
