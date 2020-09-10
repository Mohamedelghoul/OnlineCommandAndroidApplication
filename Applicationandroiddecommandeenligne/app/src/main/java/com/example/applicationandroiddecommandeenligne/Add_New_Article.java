package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Add_New_Article extends AppCompatActivity {

    Database db;
    Article article;

    Button btn_add_new_article,btn_new_article_generate_number;
    EditText new_article_id,new_article_name,new_article_code,new_article_prix,new_article_Qte_Stock,new_article_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__article);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Add_New_Article.this);

        new_article_id = findViewById(R.id.new_article_id);
        new_article_name = findViewById(R.id.new_article_name);
        new_article_code = findViewById(R.id.new_article_code);
        new_article_prix = findViewById(R.id.new_article_prix);
        new_article_Qte_Stock = findViewById(R.id.new_article_Qte_Stock);
        new_article_description = findViewById(R.id.new_article_description);

        btn_add_new_article = findViewById(R.id.btn_add_new_article);
        btn_new_article_generate_number = findViewById(R.id.btn_new_article_generate_number);

        new_article_id.setText(Integer.toString(db.getValidIdArticle()));

        btn_new_article_generate_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_article_code.setText(Integer.toString(getRandomArticleCode()));
            }
        });

        btn_add_new_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean B = true;
                String Description = null;
                if(new_article_name.getText().toString().isEmpty()){
                    new_article_name.setError("The name field is required !");
                    new_article_name.requestFocus();
                    B = false;
                }else if (!isNameValid(new_article_name.getText().toString())){
                    new_article_name.setError("Name contains just [A-Z, a-z, 0-9] characters.");
                    new_article_name.requestFocus();
                    B = false;
                }
                if (new_article_code.getText().toString().isEmpty()) {
                    new_article_code.setError("The code field is required !");
                    new_article_code.requestFocus();
                    B = false;
                }else if (!isCodeValid(new_article_code.getText().toString()) || new_article_code.getText().toString().length() != 6){
                    new_article_code.setError("Code must contains 6 numbers.");
                    new_article_code.requestFocus();
                    B = false;
                }
                if (new_article_prix.getText().toString().isEmpty()) {
                    new_article_prix.setError("The price field is required !");
                    new_article_prix.requestFocus();
                    B = false;
                }else if (!isCodeValid(new_article_prix.getText().toString())){
                    new_article_prix.setError("Code must contains just numbers.");
                    new_article_prix.requestFocus();
                    B = false;
                }
                if (new_article_Qte_Stock.getText().toString().isEmpty()) {
                    new_article_Qte_Stock.setError("The Stock Quantity field is required !");
                    new_article_Qte_Stock.requestFocus();
                    B = false;
                }else if (!isCodeValid(new_article_Qte_Stock.getText().toString())){
                    new_article_Qte_Stock.setError("Stock Quantity must contains just numbers.");
                    new_article_Qte_Stock.requestFocus();
                    B = false;
                }
                if(B){
                    if (!new_article_description.getText().toString().isEmpty()){
                        Description = new_article_description.getText().toString();
                    }
                    article = new Article(
                            Integer.parseInt(new_article_id.getText().toString()),
                            new_article_name.getText().toString(),
                            Integer.parseInt(new_article_code.getText().toString()),
                            Description,
                            Integer.parseInt(new_article_prix.getText().toString()),
                            Integer.parseInt(new_article_Qte_Stock.getText().toString())
                            );

                    Boolean insert = db.insertArticle(article);
                    if (insert){
                        Toast.makeText(Add_New_Article.this,"Article Added !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Add_New_Article.this,"Error in adding the article to the database !",Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    boolean isNameValid(CharSequence Name){
        return Pattern.compile("[a-zA-Z0-9- ]+").matcher(Name).matches();
    }

    boolean isCodeValid(CharSequence CIN){
        return Pattern.compile("[0-9]+").matcher(CIN).matches();
    }

    public int getRandomArticleCode() {
        int number;
        boolean isValid = true;
        Cursor cursor = db.readAllArticlesData();
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