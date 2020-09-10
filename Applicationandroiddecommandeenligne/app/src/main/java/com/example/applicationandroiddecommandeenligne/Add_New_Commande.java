package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Add_New_Commande extends AppCompatActivity {

    Database db;
    Commande commande;
    Client client;
    Cursor cursor;
    Status status = Status.Draft;
    ArrayList<String> Articles_Names_List,ID_Client,Status_List;

    ArrayAdapter<String> Spinner_ID_Client_Adapter,Spinner_Status_Adapter;
    boolean SelectArticleFirstTime = true;
    boolean[] Checked_Articles_List;
    String[] Articles_Names;
    String date;
    String Articles_In_Commande="";

    Button btn_add_new_commande,btn_new_commande_select_articles,btn_new_commande_generate_number;
    EditText new_commande_id,new_commande_num,new_commande_adr_client,new_commande_date,new_commande_ht,new_commande_tva,new_commande_ttc;
    Spinner spinner_new_commande_id_client,spinner_new_commande_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__commande);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Add_New_Commande.this);
        Articles_Names_List = new ArrayList<>();
        ID_Client = new ArrayList<>();
        Status_List = new ArrayList<>();

        new_commande_id = findViewById(R.id.new_commande_id);
        new_commande_num = findViewById(R.id.new_commande_num);
        new_commande_adr_client = findViewById(R.id.new_commande_adr_client);
        new_commande_date = findViewById(R.id.new_commande_date);
        new_commande_ht = findViewById(R.id.new_commande_ht);
        new_commande_tva = findViewById(R.id.new_commande_tva);
        new_commande_ttc = findViewById(R.id.new_commande_ttc);

        btn_new_commande_generate_number = findViewById(R.id.btn_new_commande_generate_number);
        btn_add_new_commande = findViewById(R.id.btn_add_new_commande);
        btn_new_commande_select_articles = findViewById(R.id.btn_new_commande_select_articles);

        spinner_new_commande_id_client = findViewById(R.id.spinner_new_commande_id_client);
        spinner_new_commande_status = findViewById(R.id.spinner_new_commande_status);

        ID_Client.add("-- Client ID --");
        cursor = db.readAllClientsData();
        if (cursor.moveToFirst()) {
            do {
                ID_Client.add(Integer.toString(cursor.getInt(0)));
            } while (cursor.moveToNext());
        }
        Spinner_ID_Client_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ID_Client);
        Spinner_ID_Client_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_new_commande_id_client.setAdapter(Spinner_ID_Client_Adapter);

        Status_List.add("Draft");
        Status_List.add("Treated");
        Status_List.add("Billed");
        Spinner_Status_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Status_List);
        Spinner_Status_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_new_commande_status.setAdapter(Spinner_Status_Adapter);


        new_commande_id.setText(Integer.toString(db.getValidIdCommande()));
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        new_commande_date.setText(date);

        btn_new_commande_generate_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_commande_num.setText(Integer.toString(getRandomCommandeNumber()));
            }
        });

        spinner_new_commande_id_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!ID_Client.get(i).equals("-- Client ID --")) {
                    int ID = Integer.parseInt(ID_Client.get(i));
                    client = db.getClientById(ID);
                    new_commande_adr_client.setText(client.getAdr());
                    if (!new_commande_ht.getText().toString().isEmpty()) {
                        int TVA, TTC;
                        TVA = Integer.parseInt(new_commande_ht.getText().toString()) * client.getCode_TVA() / 100;
                        TTC = Integer.parseInt(new_commande_ht.getText().toString()) + TVA;
                        new_commande_tva.setText(Integer.toString(TVA));
                        new_commande_ttc.setText(Integer.toString(TTC));
                    }
                } else {
                    new_commande_adr_client.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_new_commande_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_new_commande_status.getSelectedItem().toString().equals(Status.Treated.toString())){
                    status = Status.Treated;
                }else if(spinner_new_commande_status.getSelectedItem().toString().equals(Status.Billed.toString())){
                    status = Status.Billed;
                }else if(spinner_new_commande_status.getSelectedItem().toString().equals(Status.Draft.toString())){
                    status = Status.Draft;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_new_commande_select_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_New_Commande.this);


                Cursor C = db.readAllArticlesData();

                if (C.moveToFirst()) {
                    do {
                        Articles_Names_List.add("ID : " + C.getInt(0) + "\nName : " +C.getString(1) + "\nCode : " + C.getString(2) + "\nPrice : " + C.getString(4));
                    } while (C.moveToNext());

                    if (SelectArticleFirstTime) {
                        Articles_Names = new String[Articles_Names_List.size()];
                        Articles_Names_List.toArray(Articles_Names);
                        Checked_Articles_List = new boolean[Articles_Names_List.size()];
                        for (int i = 0; i < Articles_Names_List.size(); i++) {
                            Checked_Articles_List[i] = false;
                        }
                        SelectArticleFirstTime = false;
                    }

                    builder.setMultiChoiceItems(Articles_Names, Checked_Articles_List, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            Checked_Articles_List[i] = b;
                        }
                    });

                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int Which) {
                            int HT = 0;
                            boolean zeroArticleSelected = true;
                            Articles_In_Commande = "";
                            for (int i = 0; i < Checked_Articles_List.length; i++) {
                                if(Checked_Articles_List[i]) {
                                    zeroArticleSelected = false;
                                    break;
                                }
                            }
                            if (!zeroArticleSelected){
                                for (int i = 0; i < Checked_Articles_List.length; i++) {
                                    if (Checked_Articles_List[i]) {
                                        Article A = db.getArticleById(Integer.parseInt(Articles_Names_List.get(i).substring(5,Articles_Names_List.get(i).indexOf("\n"))));
                                        HT += A.getPrix();
                                        Articles_In_Commande += Articles_Names_List.get(i).substring(5,Articles_Names_List.get(i).indexOf("\n")) + ",";
                                    }
                                }
                                new_commande_ht.setText(Integer.toString(HT));
                                if (!spinner_new_commande_id_client.getSelectedItem().toString().equals("-- Client ID --")) {
                                    Client client = db.getClientById(Integer.parseInt(spinner_new_commande_id_client.getSelectedItem().toString()));
                                    int TVA, TTC;
                                    TVA = HT * client.getCode_TVA() / 100;
                                    TTC = HT + TVA;
                                    new_commande_tva.setText(Integer.toString(TVA));
                                    new_commande_ttc.setText(Integer.toString(TTC));
                                }

                            } else
                                Toast.makeText(Add_New_Commande.this, "Select at least an article !", Toast.LENGTH_SHORT).show();

                        }
                    });

                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(Add_New_Commande.this, "There is no articles to choose. The store is empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_add_new_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean B = true;

                if (new_commande_num.getText().toString().isEmpty()) {
                    btn_new_commande_generate_number.requestFocus();
                    Toast.makeText(Add_New_Commande.this, "Generate order number by clicking the button behind !", Toast.LENGTH_SHORT).show();
                    B = false;
                }
                if(spinner_new_commande_id_client.getSelectedItem().toString().equals("-- Client ID --")){
                    spinner_new_commande_id_client.requestFocus();
                    Toast.makeText(Add_New_Commande.this, "Select the client id !", Toast.LENGTH_SHORT).show();
                    B = false;
                }
                if(new_commande_ht.getText().toString().isEmpty()){
                    Toast.makeText(Add_New_Commande.this, "Select articles by clicking the button behind !", Toast.LENGTH_SHORT).show();
                    btn_new_commande_select_articles.requestFocus();
                    B = false;
                }
                if(B){
                    commande = new Commande(
                            Integer.parseInt(new_commande_id.getText().toString()),
                            Integer.parseInt(new_commande_num.getText().toString()),
                            Integer.parseInt(spinner_new_commande_id_client.getSelectedItem().toString()),
                            new_commande_adr_client.getText().toString(),
                            new_commande_date.getText().toString(),
                            status,
                            Integer.parseInt(new_commande_ht.getText().toString()),
                            Integer.parseInt(new_commande_tva.getText().toString()),
                            Integer.parseInt(new_commande_ttc.getText().toString()),
                            Articles_In_Commande
                    );
                   if(commande.getStatus_Cde().equals(Status.Billed)){
                        String[] Articles_ID = Articles_In_Commande.substring(0,Articles_In_Commande.length()-1).split(",");
                        for(int i=0 ; i< Articles_ID.length ; i++){
                            db.updateArticleStock(Integer.parseInt(Articles_ID[i]));
                        }
                       Ligne_Cde ligne = new Ligne_Cde(
                               db.getValidIdCommandeLigne(),
                               commande.getID_Cde(),
                               commande.getArticles_In_Commande(),
                               commande.getTotal_HT(),
                               client.getCode_TVA(),
                               commande.getTotal_TVA(),
                               commande.getTotal_TTC()
                       );
                        db.insertLigneCommande(ligne);
                    }
                    boolean insert = db.insertCommande(commande);
                    if (insert) {
                        Toast.makeText(Add_New_Commande.this, "Order added !", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Add_New_Commande.this, "Error on adding the order to database !", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    public int getRandomCommandeNumber() {
        int number;
        boolean isValid = true;
        Cursor cursor = db.readAllCommandesData();
        do {
            number = (int) (Math.random() * 1000001);
            while (cursor.moveToNext()) {
                if(number == cursor.getInt(2)){
                    isValid = false;
                }
            }
        } while (!isValid);
        return number;
    }
}