package com.example.applicationandroiddecommandeenligne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {

    //Database Info
    public static final int DB_Version = 1;
    public static final String DB_Name = "DatabaseApp.db";

    //Users Table Info
    public static final String Table_Name1 = "Users";
    public static final String Users_Column1 = "ID_User";
    public static final String Users_Column2 = "Nom";
    public static final String Users_Column3 = "Prenom";
    public static final String Users_Column4 = "Date_Naissance";
    public static final String Users_Column5 = "CIN";
    public static final String Users_Column6 = "Email";
    public static final String Users_Column7 = "Login";
    public static final String Users_Column8 = "Password";

    //Articles Table Info
    public static final String Table_Name2 = "Articles";
    public static final String Articles_Column1 = "ID_Article";
    public static final String Articles_Column2 = "Nom";
    public static final String Articles_Column3 = "Code";
    public static final String Articles_Column4 = "Description";
    public static final String Articles_Column5 = "Prix";
    public static final String Articles_Column6 = "Qte_Stock";

    //Clients Table Info
    public static final String Table_Name3 = "Clients";
    public static final String Clients_Column1 = "ID_Client";
    public static final String Clients_Column2 = "Nom";
    public static final String Clients_Column3 = "Code";
    public static final String Clients_Column4 = "Description";
    public static final String Clients_Column5 = "Adr";
    public static final String Clients_Column6 = "Code_TVA";
    public static final String Clients_Column7 = "Nom_Contact";

    //Commande Table Info
    public static final String Table_Name4 = "Commandes";
    public static final String Commande_Column1 = "ID_Cde";
    public static final String Commande_Column2 = "Num_Cde";
    public static final String Commande_Column3 = "ID_Client";
    public static final String Commande_Column4 = "Adr_Client";
    public static final String Commande_Column5 = "Date_Cde";
    public static final String Commande_Column6 = "Status_Cde";
    public static final String Commande_Column7 = "Total_HT";
    public static final String Commande_Column8 = "Total_TVA";
    public static final String Commande_Column9 = "Total_TTC";
    public static final String Commande_Column10 = "Articles_In_Commande";

    //Ligne Commande Table Info
    public static final String Table_Name5 = "Lignes_cde";
    public static final String Lignes_cde_Column1 = "ID_Ligne";
    public static final String Lignes_cde_Column2 = "ID_Cde";
    public static final String Lignes_cde_Column3 = "ID_Article";
    public static final String Lignes_cde_Column4 = "Total_Cde_HT";
    public static final String Lignes_cde_Column5 = "Taux_TVA";
    public static final String Lignes_cde_Column6 = "Mnt_TVA_Ligne";
    public static final String Lignes_cde_Column7 = "Total_Ligne_TTC";


    public Database(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create Table Users
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Table_Name1 + "(" +
                        Users_Column1 + " INTEGER PRIMARY KEY," +
                        Users_Column2 + " TEXT," +
                        Users_Column3 + " TEXT," +
                        Users_Column4 + " TEXT," +
                        Users_Column5 + " INTEGER," +
                        Users_Column6 + " TEXT," +
                        Users_Column7 + " TEXT UNIQUE," +
                        Users_Column8 + " TEXT);"
        );

        //Create Table Articles
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Table_Name2 + "(" +
                        Articles_Column1 + " INTEGER PRIMARY KEY," +
                        Articles_Column2 + " TEXT," +
                        Articles_Column3 + " INTEGER," +
                        Articles_Column4 + " TEXT," +
                        Articles_Column5 + " INTEGER," +
                        Articles_Column6 + " INTEGER);"
        );

        //Create Table Clients
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Table_Name3 + "(" +
                        Clients_Column1 + " INTEGER PRIMARY KEY," +
                        Clients_Column2 + " TEXT," +
                        Clients_Column3 + " INTEGER," +
                        Clients_Column4 + " TEXT," +
                        Clients_Column5 + " TEXT," +
                        Clients_Column6 + " INTEGER," +
                        Clients_Column7 + " TEXT);"
        );

        //Create Table Commandes
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Table_Name4 + "(" +
                        Commande_Column1 + " INTEGER PRIMARY KEY," +
                        Commande_Column2 + " INTEGER UNIQUE," +
                        Commande_Column3 + " INTEGER," +
                        Commande_Column4 + " TEXT," +
                        Commande_Column5 + " TEXT," +
                        Commande_Column6 + " TEXT," +
                        Commande_Column7 + " INTEGER," +
                        Commande_Column8 + " INTEGER," +
                        Commande_Column9 + " INTEGER," +
                        Commande_Column10 + " TEXT," +
                        "FOREIGN KEY(" + Commande_Column3 + ") REFERENCES " + Table_Name3 + "(" + Clients_Column1 + ")," +
                        "FOREIGN KEY(" + Commande_Column4 + ") REFERENCES " + Table_Name3 + "(" + Clients_Column5 + "));"
        );

        //Create Table Ligne Commande
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Table_Name5 + "(" +
                        Lignes_cde_Column1 + " INTEGER PRIMARY KEY," +
                        Lignes_cde_Column2 + " INTEGER," +
                        Lignes_cde_Column3 + " TEXT," +
                        Lignes_cde_Column4 + " INTEGER," +
                        Lignes_cde_Column5 + " INTEGER," +
                        Lignes_cde_Column6 + " INTEGER," +
                        Lignes_cde_Column7 + " INTEGER," +
                        "FOREIGN KEY (" + Lignes_cde_Column2 + ") REFERENCES " + Table_Name4 + "(" + Commande_Column1 + ")," +
                        "FOREIGN KEY(" + Lignes_cde_Column3 + ") REFERENCES " + Table_Name2 + "(" + Articles_Column1 + "));"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();


        Values.put(this.Users_Column1, u.getID_User());
        Values.put(this.Users_Column2, u.getNom());
        Values.put(this.Users_Column3, u.getPrenom());
        Values.put(this.Users_Column4, u.getDate_Naissance());
        Values.put(this.Users_Column5, u.getCIN());
        Values.put(this.Users_Column6, u.getEmail());
        Values.put(this.Users_Column7, u.getLogin());
        Values.put(this.Users_Column8, u.getPassword());

        long insert = db.insert(Table_Name1, null, Values);

        db.close();

        if (insert == -1)
            return false;
        else
            return true;
    }

    public boolean userExists(User U) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name1 + " WHERE " + Users_Column7 + "=" + "'" + U.getLogin() + "';", null);

        if (cursor.moveToFirst()) {
            int Id = cursor.getInt(0);
            String Nom = cursor.getString(1);
            String Prenom = cursor.getString(2);
            String Date_Naissance = cursor.getString(3);
            int CIN = cursor.getInt(4);
            String Email = cursor.getString(5);
            String Login = cursor.getString(6);
            String Password = cursor.getString(7);
            User user = new User(Id, Nom, Prenom, Date_Naissance, CIN, Email, Login, Password);

            if (user.equals(U)) {
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }

    public boolean validateLogin(String L, String P) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name1 + " WHERE " + Users_Column7 + "=" + "'" + L + "';", null);

        if (cursor.moveToFirst()) {
            String Login = cursor.getString(6);
            String Password = cursor.getString(7);
            if (Login.equals(L) && Password.equals(P))
            {
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }

    public User getUserByLogin(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name1 + " WHERE " + Users_Column7 + "=" + "'" + login + "';", null);

        cursor.moveToFirst();
        int Id = cursor.getInt(0);
        String Nom = cursor.getString(1);
        String Prenom = cursor.getString(2);
        String Date_Naissance = cursor.getString(3);
        int CIN = cursor.getInt(4);
        String Email = cursor.getString(5);
        String Login = cursor.getString(6);
        String Password = cursor.getString(7);
        user = new User(Id, Nom, Prenom, Date_Naissance, CIN, Email, Login, Password);

        db.close();
        return user;
    }

    public Cursor readAllUsersData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + Table_Name1, null);
        }
        return cursor;
    }

    public int getValidIdUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id;
        String query = "SELECT MAX(" + Users_Column1 + ") FROM " + Table_Name1;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()) {
            id = cursor.getInt(0)+1;
        }
        else{
            id = 1;
        }
        return id;
    }

    public void deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + Table_Name1 + " WHERE " + Users_Column1 + " = " + id + ";" );

        db.close();

    }

    public boolean insertClient(@NotNull Client client){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Values.put(this.Clients_Column1, client.getID_Client());
        Values.put(this.Clients_Column2, client.getNom());
        Values.put(this.Clients_Column3, client.getCode());
        Values.put(this.Clients_Column4, client.getDescription());
        Values.put(this.Clients_Column5, client.getAdr());
        Values.put(this.Clients_Column6, client.getCode_TVA());
        Values.put(this.Clients_Column7, client.getNom_Contact());

        long insert = db.insert(Table_Name3, null, Values);

        db.close();

        if (insert == -1)
            return false;
        else
            return true;
    }

    public int getValidIdClient(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id;
        String query = "SELECT MAX(" + Clients_Column1 + ") FROM " + Table_Name3;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()) {
            id = cursor.getInt(0)+1;
        }
        else{
            id = 1;
        }
        return id;
    }

    public Cursor readAllClientsData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + Table_Name3, null);
        }
        return cursor;
    }

    public Client getClientById(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Client client = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name3 + " WHERE " + Clients_Column1 + "=" + ID + ";", null);

        cursor.moveToFirst();
        int Id = cursor.getInt(0);
        String Nom = cursor.getString(1);
        int Code = cursor.getInt(2);
        String Description = cursor.getString(3);
        String Adr = cursor.getString(4);
        int Code_TVA = cursor.getInt(5);
        String Nom_Contact = cursor.getString(6);
        client  = new Client(Id,Nom,Code,Description,Adr,Code_TVA,Nom_Contact);

        db.close();
        return client;
    }

    public void deleteClient(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = this.readAllCommandesData();
        while (cursor.moveToNext()) {
            int id_client = cursor.getInt(2);
            String status = cursor.getString(5);
            if (id == id_client && !status.equals(Status.Billed)) {
                db.execSQL("DELETE FROM " + Table_Name4 + " WHERE " + Commande_Column1 + " = " + cursor.getInt(0) + ";" );
            }
        }

        db.execSQL("DELETE FROM " + Table_Name3 + " WHERE " + Clients_Column1 + " = " + id + ";" );

        db.close();

    }

    public Cursor readAllArticlesData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + Table_Name2, null);
        }
        return cursor;
    }

    public int getValidIdArticle(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id;
        String query = "SELECT MAX(" + Articles_Column1 + ") FROM " + Table_Name2;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToNext()) {
            id = cursor.getInt(0)+1;
        }
        else{
            id = 1;
        }
        return id;
    }

    public boolean insertArticle(@NotNull Article article){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Values.put(this.Articles_Column1, article.getID_Article());
        Values.put(this.Articles_Column2, article.getNom());
        Values.put(this.Articles_Column3, article.getCode());
        Values.put(this.Articles_Column4, article.getDescription());
        Values.put(this.Articles_Column5, article.getPrix());
        Values.put(this.Articles_Column6, article.getQte_Stock());


        long insert = db.insert(Table_Name2, null, Values);

        db.close();

        if (insert == -1)
            return false;
        else
            return true;
    }
    public Article getArticleById(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Article article = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name2 + " WHERE " + Articles_Column1 + "=" + ID + ";", null);

        cursor.moveToFirst();
        int Id = cursor.getInt(0);
        String Nom = cursor.getString(1);
        int Code = cursor.getInt(2);
        String Description = cursor.getString(3);
        int Prix = cursor.getInt(4);
        int Qte_Stock = cursor.getInt(5);

        article = new Article(Id,Nom,Code,Description,Prix,Qte_Stock);

        db.close();
        return article;
    }
    public void deleteArticle(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + Table_Name2 + " WHERE " + Articles_Column1 + " = " + id + ";" );

        db.close();

    }

    public Cursor readAllCommandesData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + Table_Name4, null);
        }
        return cursor;
    }

    public int getValidIdCommande(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id;
        String query = "SELECT MAX(" + Commande_Column1 + ") FROM " + Table_Name4;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            id = cursor.getInt(0)+1;
            db.close();
        }
        else{
            id = 1;
        }
        return id;
    }

    public Article getArticleByNom(String Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Article article = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name2 + " WHERE " + Articles_Column2 + "=" + "'" + Name + "';", null);

        cursor.moveToFirst();
        int Id = cursor.getInt(0);
        String Nom = cursor.getString(1);
        int Code = cursor.getInt(2);
        String Description = cursor.getString(3);
        int Prix = cursor.getInt(4);
        int Qte_Stock = cursor.getInt(5);

        article = new Article(Id, Nom, Code, Description, Prix, Qte_Stock);

        db.close();
        return article;
    }

    public boolean insertCommande(@NotNull Commande commande){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Values.put(this.Commande_Column1, commande.getID_Cde());
        Values.put(this.Commande_Column2, commande.getNum_Cde());
        Values.put(this.Commande_Column3,commande.getID_Client());
        Values.put(this.Commande_Column4,commande.getAdr_Client());
        Values.put(this.Commande_Column5,commande.getDate_Cde());
        Values.put(this.Commande_Column6,commande.getStatus_Cde().toString());
        Values.put(this.Commande_Column7,commande.getTotal_HT());
        Values.put(this.Commande_Column8,commande.getTotal_TVA());
        Values.put(this.Commande_Column9,commande.getTotal_TTC());
        Values.put(this.Commande_Column10,commande.getArticles_In_Commande());


        long insert = db.insert(Table_Name4, null, Values);

        db.close();

        if (insert == -1)
            return false;
        else
            return true;
    }

    public void updateArticleStock(int ArticleID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Cursor cursor = db.rawQuery("SELECT " + Articles_Column6 + " FROM " + Table_Name2 + " WHERE " + Articles_Column1 + "=" + ArticleID + ";", null);
        cursor.moveToNext();
        if(cursor.getInt(0) > 1) {
            Values.put(Articles_Column6, cursor.getInt(0)-1);
            db.update(Table_Name2, Values, Articles_Column1 + "=" + ArticleID , null);
        }else{
            db.execSQL("DELETE FROM " + Table_Name2 + " WHERE " + Articles_Column1 + " = " + ArticleID + ";" );
        }
        db.close();
    }

    public int getValidIdCommandeLigne(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id;
        String query = "SELECT MAX(" + Lignes_cde_Column1 + ") FROM " + Table_Name5;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            id = cursor.getInt(0)+1;
            db.close();
        }
        else{
            id = 1;
        }
        return id;
    }

    public boolean insertLigneCommande(@NotNull Ligne_Cde ligne){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Values.put(this.Lignes_cde_Column1, ligne.getID_Ligne());
        Values.put(this.Lignes_cde_Column2, ligne.getID_Cde());
        Values.put(this.Lignes_cde_Column3, ligne.getID_Article());
        Values.put(this.Lignes_cde_Column4, ligne.getTotal_Cde_HT());
        Values.put(this.Lignes_cde_Column5, ligne.getTaux_TVA());
        Values.put(this.Lignes_cde_Column6, ligne.getMnt_TVA_Ligne());
        Values.put(this.Lignes_cde_Column7, ligne.getTotal_Ligne_TTC());


        long insert = db.insert(Table_Name5, null, Values);

        db.close();

        if (insert == -1)
            return false;
        else
            return true;
    }

    public Cursor readAllLignesCommandesData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + Table_Name5, null);
        }
        return cursor;
    }

    public Ligne_Cde getLigneCommandeById(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
       Ligne_Cde ligne;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name5 + " WHERE " + Lignes_cde_Column1 + "=" + ID + ";", null);

        cursor.moveToFirst();
        int ID_Ligne = cursor.getInt(0);
        int ID_Cde = cursor.getInt(1);
        String ID_Article = cursor.getString(2);
        int Total_Cde_HT = cursor.getInt(3);
        int Taux_TVA = cursor.getInt(4);
        int Mnt_TVA_Ligne = cursor.getInt(5);
        int Total_Ligne_TTC = cursor.getInt(6);

        ligne = new Ligne_Cde(ID_Ligne,ID_Cde,ID_Article,Total_Cde_HT,Taux_TVA,Mnt_TVA_Ligne,Total_Ligne_TTC);

        db.close();
        return ligne;
    }

    public void deleteCommande(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + Table_Name4 + " WHERE " + Commande_Column1 + " = " + id + ";" );

        db.close();

    }

    public Commande getCommandeById(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Commande commande;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name4 + " WHERE " + Commande_Column1 + "=" + ID + ";", null);

        cursor.moveToFirst();
        int ID_Cde = cursor.getInt(0);
        int Num_Cde = cursor.getInt(1);
        int ID_Client = cursor.getInt(2);
        String Adr_Client = cursor.getString(3);
        String Date_Cde = cursor.getString(4);
        String status = cursor.getString(5);
        Status Status_Cde = Status.Draft;
        if(status.equals(Status.Treated.toString())){
            Status_Cde = Status.Treated;
        }else if(status.equals(Status.Billed.toString())){
            Status_Cde = Status.Billed;
        }
        int Total_HT = cursor.getInt(6);
        int Total_TVA = cursor.getInt(7);
        int Total_TTC = cursor.getInt(8);
        String Articles_In_Commande = cursor.getString(9);

        commande = new Commande(ID_Cde,Num_Cde,ID_Client,Adr_Client,Date_Cde,Status_Cde,Total_HT,Total_TVA,Total_TTC,Articles_In_Commande);

        db.close();
        return commande;
    }

    public void deleteLigneCommande(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + Table_Name5 + " WHERE " + Lignes_cde_Column1 + " = " + id + ";" );

        db.close();
    }

}
