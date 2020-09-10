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

public class Articles_List extends AppCompatActivity implements RecyclerViewClick{

    String login_value = null;
    int id_selected_row = -1;
    int s = 1;
    int REQUEST_CODE = 1;

    RecyclerView Articles_recyclerView;
    Button btn_add_article, btn_edit_article, btn_remove_article;

    Database db;
    ArrayList<String> articles_id, articles_code, articles_name;
    Users_RecyclerView_Adapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles__list);

        Articles_recyclerView = findViewById(R.id.Articles_recyclerView);
        btn_add_article = findViewById(R.id.btn_add_article);
        btn_edit_article = findViewById(R.id.btn_edit_article);
        btn_remove_article = findViewById(R.id.btn_remove_article);

        db = new Database(Articles_List.this);
        articles_id = new ArrayList<>();
        articles_code = new ArrayList<>();
        articles_name = new ArrayList<>();

        storeDataInArrays();

        Adapter =  new Users_RecyclerView_Adapter(Articles_List.this, articles_id, articles_code, articles_name,this);
        Articles_recyclerView.setAdapter(Adapter);
        Articles_recyclerView.setLayoutManager(new LinearLayoutManager(Articles_List.this));

        Bundle extra = getIntent().getExtras();
        login_value = extra.getString("Login");

        btn_add_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Articles_List.this, Add_New_Article.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        btn_remove_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_selected_row == -1){
                    Toast.makeText(Articles_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Articles_List.this, Remove_Article.class);
                    intent.putExtra("idSelectedRow", id_selected_row);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });

        btn_edit_article.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(id_selected_row == -1){
                    Toast.makeText(Articles_List.this, "No row selected !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Articles_List.this,Edit_Article.class);
                    intent.putExtra("idSelectedRow", id_selected_row);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                id_selected_row = -1;

                articles_id.clear();
                articles_code.clear();
                articles_name.clear();
                storeDataInArrays();

                Adapter =  new Users_RecyclerView_Adapter(Articles_List.this, articles_id, articles_code, articles_name,this);
                Articles_recyclerView.setAdapter(Adapter);
                Articles_recyclerView.setLayoutManager(new LinearLayoutManager(Articles_List.this));
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Articles_List.this, Profile.class);
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
            id_selected_row = Integer.parseInt(articles_id.get(position));
        }
    }


    void storeDataInArrays(){
        Cursor cursor = db.readAllArticlesData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"There is no client !", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                articles_id.add(cursor.getString(0));
                articles_name.add(cursor.getString(1));
                articles_code.add(cursor.getString(2));

            }
        }
    }
}