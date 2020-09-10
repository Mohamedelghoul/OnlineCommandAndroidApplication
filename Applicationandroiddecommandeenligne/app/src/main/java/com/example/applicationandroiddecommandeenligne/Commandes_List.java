package com.example.applicationandroiddecommandeenligne;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Commandes_List extends AppCompatActivity implements RecyclerViewClick{

    String login_value = null;
    int id_selected_row_commande = -1;
    int id_selected_row_ligne_commande = -1;
    int REQUEST_CODE = 1;
    ArrayList<String> Commandes_id,Commandes_num,Commandes_Status;
    ArrayList<String> Ligne_id,Ligne_Commande_id,Ligne_TTC;

    RecyclerView Ligne_Commandes_recyclerView,Commandes_recyclerView;
    Button btn_add_commande,btn_edit_commande,btn_remove_commande;

    Database db;
    Clients_RecyclerView_Adapter Commande_Adapter;
    Ligne_Cde_RecyclerView_Adapter Ligne_Commande_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes__list);

        Ligne_Commandes_recyclerView = findViewById(R.id.Ligne_Commandes_recyclerView);
        Commandes_recyclerView = findViewById(R.id.Commandes_recyclerView);
        btn_add_commande = findViewById(R.id.btn_add_commande);
        btn_edit_commande = findViewById(R.id.btn_edit_commande);
        btn_remove_commande = findViewById(R.id.btn_remove_commande);

        db = new Database(Commandes_List.this);
        Commandes_id = new ArrayList<>();
        Commandes_num = new ArrayList<>();
        Commandes_Status = new ArrayList<>();

        Ligne_id = new ArrayList<>();
        Ligne_Commande_id = new ArrayList<>();
        Ligne_TTC = new ArrayList<>();

        storeCommandesDataInArrays();
        storeLignesCommandesDataInArrays();

        Commande_Adapter =  new Clients_RecyclerView_Adapter(Commandes_List.this, this, Commandes_id, Commandes_num, Commandes_Status);
        Commandes_recyclerView.setAdapter(Commande_Adapter);
        Commandes_recyclerView.setLayoutManager(new LinearLayoutManager(Commandes_List.this));

        Ligne_Commande_Adapter =  new Ligne_Cde_RecyclerView_Adapter(Commandes_List.this, this, Ligne_id, Ligne_Commande_id, Ligne_TTC);
        Ligne_Commandes_recyclerView.setAdapter(Ligne_Commande_Adapter);
        Ligne_Commandes_recyclerView.setLayoutManager(new LinearLayoutManager(Commandes_List.this));

        Bundle extra = getIntent().getExtras();
        login_value = extra.getString("Login");

        btn_add_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Commandes_List.this, Add_New_Commande.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btn_remove_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_selected_row_commande == -1 && id_selected_row_ligne_commande ==-1){
                    Toast.makeText(Commandes_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                } else if(id_selected_row_commande != -1){
                Intent intent = new Intent(Commandes_List.this, Remove_Commande.class);
                intent.putExtra("idSelectedRow", id_selected_row_commande);
                startActivityForResult(intent, REQUEST_CODE);
                } else if (id_selected_row_ligne_commande != -1) {
                    Intent intent = new Intent(Commandes_List.this, Remove_Ligne_Commande.class);
                    intent.putExtra("idSelectedRow", id_selected_row_ligne_commande);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

        btn_edit_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_selected_row_commande == -1){
                    Toast.makeText(Commandes_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Commandes_List.this, Edit_Commande.class);
                    intent.putExtra("idSelectedRow", id_selected_row_commande);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }

        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Commandes_List.this, Profile.class);
        intent.putExtra("Login",login_value);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(int position, boolean makeToast) {
        if(position == -1){
            id_selected_row_commande = -1;
        }
        else {
            if (makeToast) {
                id_selected_row_ligne_commande = Integer.parseInt(Ligne_id.get(position));
                Ligne_Cde ligne = db.getLigneCommandeById(Integer.parseInt(Ligne_id.get(position)));
                String toast = "Ligne ID : " + ligne.getID_Ligne() +
                        "\nOrder ID : " + ligne.getID_Cde() +
                        "\nArticles IDs : " + ligne.getID_Article() +
                        "\nTotal HT : " + ligne.getTotal_Cde_HT() +
                        "\nTVA Rate : " + ligne.getTaux_TVA() +
                        "\nTVA Amount : " + ligne.getMnt_TVA_Ligne() +
                        "\nTTC Amount : " + ligne.getTotal_Ligne_TTC();
                Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();

            } else{
                id_selected_row_commande = Integer.parseInt(Commandes_id.get(position));
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                id_selected_row_commande = -1;

                Commandes_id.clear();
                Commandes_num.clear();
                Commandes_Status.clear();

                Ligne_id.clear();
                Ligne_Commande_id.clear();
                Ligne_TTC.clear();


                storeCommandesDataInArrays();
                storeLignesCommandesDataInArrays();

                Commande_Adapter =  new Clients_RecyclerView_Adapter(Commandes_List.this, this, Commandes_id, Commandes_num, Commandes_Status);
                Commandes_recyclerView.setAdapter(Commande_Adapter);
                Commandes_recyclerView.setLayoutManager(new LinearLayoutManager(Commandes_List.this));

                Ligne_Commande_Adapter =  new Ligne_Cde_RecyclerView_Adapter(Commandes_List.this, this, Ligne_id, Ligne_Commande_id, Ligne_TTC);
                Ligne_Commandes_recyclerView.setAdapter(Ligne_Commande_Adapter);
                Ligne_Commandes_recyclerView.setLayoutManager(new LinearLayoutManager(Commandes_List.this));
            }
        }
    }

    void storeCommandesDataInArrays(){
        Cursor cursor = db.readAllCommandesData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"There is no orders !", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                Commandes_id.add(cursor.getString(0));
                Commandes_num.add(cursor.getString(1));
                Commandes_Status.add(cursor.getString(5));
            }
        }
    }

    void storeLignesCommandesDataInArrays(){
        Cursor cursor = db.readAllLignesCommandesData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"There is no order lines !", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                Ligne_id.add(cursor.getString(0));
                Ligne_Commande_id.add("Order ID : " + cursor.getString(1));
                Ligne_TTC.add("Total TTC : " + cursor.getString(6));
            }
        }
    }
}