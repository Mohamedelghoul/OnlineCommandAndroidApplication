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

public class Users_List extends AppCompatActivity implements RecyclerViewClick {

    String login_value = null;
    String login_selected_row = null;
    int id_selected_row = -1;
    int s = 1;
    int REQUEST_CODE = 1;

    RecyclerView Users_recyclerView;
    Button btn_add_user,btn_edit_user,btn_remove_user;

    Database db;
    ArrayList<String> users_id, users_login, users_name;
    Users_RecyclerView_Adapter Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        Users_recyclerView = findViewById(R.id.Users_recyclerView);
        btn_add_user = findViewById(R.id.btn_add_user);
        btn_edit_user = findViewById(R.id.btn_edit_user);
        btn_remove_user = findViewById(R.id.btn_remove_user);

        db = new Database(Users_List.this);
        users_id = new ArrayList<>();
        users_login = new ArrayList<>();
        users_name = new ArrayList<>();

        Bundle extra = getIntent().getExtras();
        login_value = extra.getString("Login");
        s ++;


        storeDataInArrays(login_value);

         Adapter =  new Users_RecyclerView_Adapter(Users_List.this, users_id, users_login, users_name,this);
         Users_recyclerView.setAdapter(Adapter);
         Users_recyclerView.setLayoutManager(new LinearLayoutManager(Users_List.this));

         btn_add_user.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(Users_List.this, Add_New_User.class);
                 startActivityForResult(intent,REQUEST_CODE);
             }
         });

        btn_remove_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_selected_row == -1){
                    Toast.makeText(Users_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Users_List.this, Remove_User.class);
                    intent.putExtra("idSelectedRow", id_selected_row);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });

        btn_edit_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(login_selected_row == null){
                    Toast.makeText(Users_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Users_List.this,Edit_User.class);
                    intent.putExtra("loginSelectedRow", login_selected_row);
                    intent.putExtra("idSelectedRow", id_selected_row);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });
    }

    void storeDataInArrays(String login){
        Cursor cursor = db.readAllUsersData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"There is no user !", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                if(!cursor.getString(7).equals(login) ) {
                    users_id.add(cursor.getString(0));
                    users_login.add(cursor.getString(6));
                    users_name.add(cursor.getString(1) + " " + cursor.getString(2));
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                id_selected_row = -1;
                login_selected_row = null;

                users_id.clear();
                users_login.clear();
                users_name.clear();
                storeDataInArrays(login_value);

                Adapter =  new Users_RecyclerView_Adapter(Users_List.this, users_id, users_login, users_name,this);
                Users_recyclerView.setAdapter(Adapter);
                Users_recyclerView.setLayoutManager(new LinearLayoutManager(Users_List.this));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Users_List.this, Profile.class);
        intent.putExtra("Login",login_value);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(int position, boolean makeToast) {
        if(position == -1){
            id_selected_row = -1;
            login_selected_row = null;
        }
        else {
            id_selected_row = Integer.parseInt(users_id.get(position));
            login_selected_row = users_login.get(position);
        }
    }

}