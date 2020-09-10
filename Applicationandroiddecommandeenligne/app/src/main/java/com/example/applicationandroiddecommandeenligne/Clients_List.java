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

public class Clients_List extends AppCompatActivity implements RecyclerViewClick{

    String login_value = null;
    int id_selected_row = -1;
    int REQUEST_CODE = 1;

    RecyclerView Client_recyclerView;
    Button btn_add_client,btn_edit_client,btn_remove_client;

    Database db;
    Clients_RecyclerView_Adapter Adapter;
    ArrayList<String> clients_id, clients_code,clients_contact_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients__list);

        Client_recyclerView = findViewById(R.id.Clients_recyclerView);
        btn_add_client = findViewById(R.id.btn_add_client);
        btn_edit_client = findViewById(R.id.btn_edit_client);
        btn_remove_client = findViewById(R.id.btn_remove_client);

        db = new Database(Clients_List.this);
        clients_id = new ArrayList<>();
        clients_code = new ArrayList<>();
        clients_contact_name = new ArrayList<>();

        storeDataInArrays();

        Adapter =  new Clients_RecyclerView_Adapter(Clients_List.this, this, clients_id, clients_code, clients_contact_name);
        Client_recyclerView.setAdapter(Adapter);
        Client_recyclerView.setLayoutManager(new LinearLayoutManager(Clients_List.this));

        Bundle extra = getIntent().getExtras();
        login_value = extra.getString("Login");

        btn_add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clients_List.this, Add_New_Client.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btn_edit_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_selected_row == -1){
                    Toast.makeText(Clients_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Clients_List.this, Edit_Client.class);
                    intent.putExtra("idSelectedRow", id_selected_row);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        btn_remove_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_selected_row == -1){
                    Toast.makeText(Clients_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Clients_List.this, Remove_Client.class);
                    intent.putExtra("idSelectedRow", id_selected_row);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Clients_List.this, Profile.class);
        intent.putExtra("Login",login_value);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(int position, boolean makeToast) {
        if(position == -1){
            id_selected_row = -1;
        }
        else {
            id_selected_row = Integer.parseInt(clients_id.get(position));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                id_selected_row = -1;

                clients_id.clear();
                clients_code.clear();
                clients_contact_name.clear();
                storeDataInArrays();

                Adapter =  new Clients_RecyclerView_Adapter(Clients_List.this, this, clients_id, clients_code, clients_contact_name);
                Client_recyclerView.setAdapter(Adapter);
                Client_recyclerView.setLayoutManager(new LinearLayoutManager(Clients_List.this));
            }
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllClientsData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"There is no client !", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                clients_id.add(cursor.getString(0));
                clients_code.add(cursor.getString(2));
                clients_contact_name.add(cursor.getString(6));
            }
        }
    }
}
