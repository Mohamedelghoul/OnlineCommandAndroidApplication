package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Remove_Article extends AppCompatActivity {

    Database db;
    TextView remove_article_question;
    Button btn_yes_remove_article,btn_no_remove_article;

    int id_row_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__article);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.4));

        db = new Database(Remove_Article.this);
        remove_article_question = findViewById(R.id.remove_article_question);
        btn_yes_remove_article = findViewById(R.id.btn_yes_remove_article);
        btn_no_remove_article = findViewById(R.id.btn_no_remove_article);

        Bundle extra = getIntent().getExtras();
        id_row_selected = extra.getInt("idSelectedRow");
        remove_article_question.setText("Do you want to remove the client with id '"+id_row_selected +"' ?");

        btn_yes_remove_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteArticle(id_row_selected);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_no_remove_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}