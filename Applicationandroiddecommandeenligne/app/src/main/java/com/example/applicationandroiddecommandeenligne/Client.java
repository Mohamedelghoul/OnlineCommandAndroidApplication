package com.example.applicationandroiddecommandeenligne;

public class Client {
    int ID_Client;
    String Nom;
    int Code;
    String Description;
    String Adr;
    int Code_TVA;
    String Nom_Contact;

    public Client(int ID_Client, String nom, int code, String description, String adr, int code_TVA, String nom_Contact) {
        this.ID_Client = ID_Client;
        Nom = nom;
        Code = code;
        Description = description;
        Adr = adr;
        Code_TVA = code_TVA;
        Nom_Contact = nom_Contact;
    }

    public int getID_Client() {
        return ID_Client;
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

    public String getAdr() {
        return Adr;
    }

    public int getCode_TVA() {
        return Code_TVA;
    }

    public String getNom_Contact() {
        return Nom_Contact;
    }

    public void setID_Client(int ID_Client) {
        this.ID_Client = ID_Client;
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

    public void setAdr(String adr) {
        Adr = adr;
    }

    public void setCode_TVA(int code_TVA) {
        Code_TVA = code_TVA;
    }

    public void setNom_Contact(String nom_Contact) {
        Nom_Contact = nom_Contact;
    }
}
