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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Edit_Commande extends AppCompatActivity {

    Database db;
    Commande commande;
    Client client;
    Cursor cursor;
    Status status = Status.Draft;
    ArrayList<String> Articles_Names_List,ID_Client,Status_List;
    List<String> Selected_Articles_IDs;

    ArrayAdapter<String> Spinner_ID_Client_Adapter,Spinner_Status_Adapter;
    boolean[] Checked_Articles_List;
    String[] Articles_Names;
    String Articles_In_Commande="";
    int id_selected_row;
    boolean SelectArticleFirstTime = true;

    Button btn_edit_commande,btn_edit_commande_select_articles;
    EditText edit_commande_id,edit_commande_num,edit_commande_adr_client,edit_commande_date,edit_commande_ht,edit_commande_tva,edit_commande_ttc;
    Spinner spinner_edit_commande_id_client,spinner_edit_commande_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__commande);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Edit_Commande.this);
        Articles_Names_List = new ArrayList<>();
        ID_Client = new ArrayList<>();
        Status_List = new ArrayList<>();

        edit_commande_id = findViewById(R.id.edit_commande_id);
        edit_commande_num = findViewById(R.id.edit_commande_num);
        edit_commande_adr_client = findViewById(R.id.edit_commande_adr_client);
        edit_commande_date = findViewById(R.id.edit_commande_date);
        edit_commande_ht = findViewById(R.id.edit_commande_ht);
        edit_commande_tva = findViewById(R.id.edit_commande_tva);
        edit_commande_ttc = findViewById(R.id.edit_commande_ttc);

        btn_edit_commande = findViewById(R.id.btn_edit_commande);
        btn_edit_commande_select_articles = findViewById(R.id.btn_edit_commande_select_articles);

        spinner_edit_commande_id_client = findViewById(R.id.spinner_edit_commande_id_client);
        spinner_edit_commande_status = findViewById(R.id.spinner_edit_commande_status);

        ID_Client.add("-- Client ID --");
        cursor = db.readAllClientsData();
        if (cursor.moveToFirst()) {
            do {
                ID_Client.add(Integer.toString(cursor.getInt(0)));
            } while (cursor.moveToNext());
        }
        Spinner_ID_Client_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ID_Client);
        Spinner_ID_Client_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_edit_commande_id_client.setAdapter(Spinner_ID_Client_Adapter);

        Status_List.add("Draft");
        Status_List.add("Treated");
        Status_List.add("Billed");
        Spinner_Status_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Status_List);
        Spinner_Status_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_edit_commande_status.setAdapter(Spinner_Status_Adapter);

        Bundle extra = getIntent().getExtras();
        id_selected_row = extra.getInt("idSelectedRow");
        commande = db.getCommandeById(id_selected_row);
        Articles_In_Commande = commande.getArticles_In_Commande();

        edit_commande_id.setText(Integer.toString(commande.getID_Cde()));
        edit_commande_num.setText(Integer.toString(commande.getNum_Cde()));
        for(int i=0;i<ID_Client.size();i++){
            if(ID_Client.get(i).equals(Integer.toString(commande.getID_Client()))){
                spinner_edit_commande_id_client.setSelection(i);
            }
        }
        edit_commande_adr_client.setText(commande.getAdr_Client());
        edit_commande_date.setText(commande.getDate_Cde());
        if(commande.getStatus_Cde().equals(Status.Treated)){
            spinner_edit_commande_status.setSelection(1);
        }else if(commande.getStatus_Cde().equals(Status.Billed)){
            spinner_edit_commande_status.setSelection(2);
        }
        edit_commande_ht.setText(Integer.toString(commande.getTotal_HT()));
        edit_commande_tva.setText(Integer.toString(commande.getTotal_TVA()));
        edit_commande_ttc.setText(Integer.toString(commande.getTotal_TTC()));

        spinner_edit_commande_id_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!ID_Client.get(i).equals("-- Client ID --")) {
                    int ID = Integer.parseInt(ID_Client.get(i));
                    client = db.getClientById(ID);
                    edit_commande_adr_client.setText(client.getAdr());
                    if (!edit_commande_ht.getText().toString().isEmpty()) {
                        int TVA, TTC;
                        TVA = Integer.parseInt(edit_commande_ht.getText().toString()) * client.getCode_TVA() / 100;
                        TTC = Integer.parseInt(edit_commande_ht.getText().toString()) + TVA;
                        edit_commande_tva.setText(Integer.toString(TVA));
                        edit_commande_ttc.setText(Integer.toString(TTC));
                    }
                } else {
                    edit_commande_adr_client.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_edit_commande_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_edit_commande_status.getSelectedItem().toString().equals(Status.Treated.toString())){
                    status = Status.Treated;
                }else if(spinner_edit_commande_status.getSelectedItem().toString().equals(Status.Billed.toString())){
                    status = Status.Billed;
                }else if(spinner_edit_commande_status.getSelectedItem().toString().equals(Status.Draft.toString())){
                    status = Status.Draft;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_edit_commande_select_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Commande.this);


                Cursor C = db.readAllArticlesData();

                if (C.moveToFirst()) {
                    do {
                        Articles_Names_List.add("ID : " + C.getInt(0) + "\nName : " +C.getString(1) + "\nCode : " + C.getString(2) + "\nPrice : " + C.getString(4));
                    } while (C.moveToNext());


                    if (SelectArticleFirstTime) {
                        int substringLastIndex = commande.getArticles_In_Commande().length() -1 ;
                        Selected_Articles_IDs = Arrays.asList(commande.getArticles_In_Commande().substring(0, substringLastIndex).split(","));
                        Articles_Names = new String[Articles_Names_List.size()];
                        Articles_Names_List.toArray(Articles_Names);
                        Checked_Articles_List = new boolean[Articles_Names_List.size()];
                        for (int i = 0; i < Articles_Names_List.size(); i++) {
                            for (int j = 0; j < Selected_Articles_IDs.size(); j++) {
                                if (Integer.parseInt(Articles_Names_List.get(i).substring(5, Articles_Names_List.get(i).indexOf("\n"))) == Integer.parseInt(Selected_Articles_IDs.get(j))) {
                                    Checked_Articles_List[i] = true;
                                    break;
                                } else Checked_Articles_List[i] = false;
                            }
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
                                if (Checked_Articles_List[i]) {
                                    zeroArticleSelected = false;
                                    break;
                                }
                            }
                            if (!zeroArticleSelected) {
                                for (int i = 0; i < Checked_Articles_List.length; i++) {
                                    if (Checked_Articles_List[i]) {
                                        Article A = db.getArticleById(Integer.parseInt(Articles_Names_List.get(i).substring(5, Articles_Names_List.get(i).indexOf("\n"))));
                                        HT += A.getPrix();
                                        Articles_In_Commande += Articles_Names_List.get(i).substring(5, Articles_Names_List.get(i).indexOf("\n")) + ",";
                                    }
                                }
                                edit_commande_ht.setText(Integer.toString(HT));
                                if (!spinner_edit_commande_id_client.getSelectedItem().toString().equals("-- Client ID --")) {
                                    Client client = db.getClientById(Integer.parseInt(spinner_edit_commande_id_client.getSelectedItem().toString()));
                                    int TVA, TTC;
                                    TVA = HT * client.getCode_TVA() / 100;
                                    TTC = HT + TVA;
                                    edit_commande_tva.setText(Integer.toString(TVA));
                                    edit_commande_ttc.setText(Integer.toString(TTC));
                                }
                            } else {
                                Toast.makeText(Edit_Commande.this, "Select at least an article !", Toast.LENGTH_SHORT).show();
                                edit_commande_ht.setText("");
                                edit_commande_tva.setText("");
                                edit_commande_ttc.setText("");
                            }
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
                    Toast.makeText(Edit_Commande.this, "There is no articles to choose. The store is empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_edit_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean B = true;

                if(spinner_edit_commande_id_client.getSelectedItem().toString().equals("-- Client ID --")){
                    spinner_edit_commande_id_client.requestFocus();
                    Toast.makeText(Edit_Commande.this, "Select the client id !", Toast.LENGTH_SHORT).show();
                    B = false;
                }
                if(edit_commande_ht.getText().toString().isEmpty()){
                    Toast.makeText(Edit_Commande.this, "Select articles by clicking the button behind !", Toast.LENGTH_SHORT).show();
                    btn_edit_commande_select_articles.requestFocus();
                    B = false;
                }
                if(B){
                    commande = new Commande(
                            Integer.parseInt(edit_commande_id.getText().toString()),
                            Integer.parseInt(edit_commande_num.getText().toString()),
                            Integer.parseInt(spinner_edit_commande_id_client.getSelectedItem().toString()),
                            edit_commande_adr_client.getText().toString(),
                            edit_commande_date.getText().toString(),
                            status,
                            Integer.parseInt(edit_commande_ht.getText().toString()),
                            Integer.parseInt(edit_commande_tva.getText().toString()),
                            Integer.parseInt(edit_commande_ttc.getText().toString()),
                            Articles_In_Commande
                    );
                    if(commande.getStatus_Cde().equals(Status.Billed)) {
                        String[] Articles_ID = Articles_In_Commande.substring(0,Articles_In_Commande.length()-1).split(",");
                        for (int i = 0; i < Articles_ID.length; i++) {
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
                     db.deleteCommande(commande.getID_Cde());
                    boolean insert = db.insertCommande(commande);
                    if (insert) {
                        Toast.makeText(Edit_Commande.this, "Order added !", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Edit_Commande.this, "Error on adding the order to database !", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }
}