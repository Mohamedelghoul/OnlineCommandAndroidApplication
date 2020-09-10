package com.example.applicationandroiddecommandeenligne;

public class Ligne_Cde {
    int ID_Ligne;
    int ID_Cde;
    String ID_Article;
    int Total_Cde_HT;
    int Taux_TVA;
    int Mnt_TVA_Ligne;
    int Total_Ligne_TTC;

   public Ligne_Cde(int ID_Ligne, int ID_Cde, String ID_Article, int total_Cde_HT, int taux_TVA, int mnt_TVA_Ligne, int total_Ligne_TTC) {
         this.ID_Ligne = ID_Ligne;
        this.ID_Cde = ID_Cde;
        this.ID_Article = ID_Article;
        Total_Cde_HT = total_Cde_HT;
        Taux_TVA = taux_TVA;
        Mnt_TVA_Ligne = mnt_TVA_Ligne;
        Total_Ligne_TTC = total_Ligne_TTC;
    }

    public int getID_Ligne() {
        return ID_Ligne;
    }

    public int getID_Cde() {
        return ID_Cde;
    }

    public String getID_Article() {
        return ID_Article;
    }


    public int getTotal_Cde_HT() {
        return Total_Cde_HT;
    }

    public int getTaux_TVA() {
        return Taux_TVA;
    }

    public int getMnt_TVA_Ligne() {
        return Mnt_TVA_Ligne;
    }

    public int getTotal_Ligne_TTC() {
        return Total_Ligne_TTC;
    }

    public void setID_Ligne(int ID_Ligne) {
        this.ID_Ligne = ID_Ligne;
    }

    public void setID_Cde(int ID_Cde) {
        this.ID_Cde = ID_Cde;
    }

    public void setID_Article(String ID_Article) {
        this.ID_Article = ID_Article;
    }

    public void setTotal_Cde_HT(int total_Cde_HT) {
        Total_Cde_HT = total_Cde_HT;
    }

    public void setTaux_TVA(int taux_TVA) {
        Taux_TVA = taux_TVA;
    }

    public void setMnt_TVA_Ligne(int mnt_TVA_Ligne) {
        Mnt_TVA_Ligne = mnt_TVA_Ligne;
    }

    public void setTotal_Ligne_TTC(int total_Ligne_TTC) {
        Total_Ligne_TTC = total_Ligne_TTC;
    }
}
