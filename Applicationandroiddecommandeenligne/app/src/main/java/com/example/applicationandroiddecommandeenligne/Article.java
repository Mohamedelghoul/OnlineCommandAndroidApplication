package com.example.applicationandroiddecommandeenligne;

public class Article {
    int ID_Article;
    String Nom;
    int Code;
    String Description;
    int Prix;
    int Qte_Stock;

    public Article(int ID_Article, String nom, int code, String description, int prix, int qte_Stock) {
        this.ID_Article = ID_Article;
        Nom = nom;
        Code = code;
        Description = description;
        Prix = prix;
        Qte_Stock = qte_Stock;
    }

    public int getID_Article() {
        return ID_Article;
    }

    public String getNom() {
        return Nom;
    }

    public int getCode() {
        return Code;
    }

    public String getDescription() {
        return Description;
    }

    public int getPrix() {
        return Prix;
    }

    public int getQte_Stock() {
        return Qte_Stock;
    }

    public void setID_Article(int ID_Article) {
        this.ID_Article = ID_Article;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setCode(int code) {
        Code = code;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrix(int prix) {
        Prix = prix;
    }

    public void setQte_Stock(int qte_Stock) {
        Qte_Stock = qte_Stock;
    }
}
