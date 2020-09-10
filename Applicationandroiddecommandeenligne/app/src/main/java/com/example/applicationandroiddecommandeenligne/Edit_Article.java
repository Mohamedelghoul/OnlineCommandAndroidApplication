package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Edit_Article extends AppCompatActivity {

    Database db;
    int id_selected_row;
    Article article;

    EditText edit_article_id,edit_article_name,edit_article_code,edit_article_prix,edit_article_Qte_Stock,edit_article_description;
    Button btn_edit_article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__article);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Edit_Article.this);

        edit_article_id = findViewById(R.id.edit_article_id);
        edit_article_name = findViewById(R.id.edit_article_name);
        edit_article_code = findViewById(R.id.edit_article_code);
        edit_article_prix = findViewById(R.id.edit_article_prix);
        edit_article_Qte_Stock = findViewById(R.id.edit_article_Qte_Stock);
        edit_article_description = findViewById(R.id.edit_article_description);
        btn_edit_article = findViewById(R.id.btn_edit_article);

        Bundle extra = getIntent().getExtras();
        id_selected_row = extra.getInt("idSelectedRow");

        article = db.getArticleById(id_selected_row);

        edit_article_id.setText(Integer.toString(id_selected_row));
        edit_article_name.setText(article.getNom());
        edit_article_code.setText(Integer.toString(article.getCode()));
        edit_article_prix.setText(Integer.toString(article.getPrix()));
        edit_article_Qte_Stock.setText(Integer.toString(article.getQte_Stock()));
        if (article.getDescription() != null) {
            edit_article_description.setText(article.getDescription());
        }

        btn_edit_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean B = true;
                String Description = null;
                if(edit_article_name.getText().toString().isEmpty()){
                    edit_article_name.setError("The name field is required !");
                    edit_article_name.requestFocus();
                    B = false;
                }else if (!isNameValid(edit_article_name.getText().toString())){
                    edit_article_name.setError("Name contains just [A-Z, a-z] characters.");
                    edit_article_name.requestFocus();
                    B = false;
                }
                if (edit_article_prix.getText().toString().isEmpty()) {
                    edit_article_prix.setError("The price field is required !");
                    edit_article_prix.requestFocus();
                    B = false;
                }else if (!isCodeValid(edit_article_prix.getText().toString())){
                    edit_article_prix.setError("Code must contains just numbers.");
                    edit_article_prix.requestFocus();
                    B = false;
                }
                if (edit_article_Qte_Stock.getText().toString().isEmpty()) {
                    edit_article_Qte_Stock.setError("The Stock Quantity field is required !");
                    edit_article_Qte_Stock.requestFocus();
                    B = false;
                }else if (!isCodeValid(edit_article_Qte_Stock.getText().toString())){
                    edit_article_Qte_Stock.setError("Stock Quantity must contains just numbers.");
                    edit_article_Qte_Stock.requestFocus();
                    B = false;
                }
                if(B){
                    if (!edit_article_description.getText().toString().isEmpty()){
                        Description = edit_article_description.getText().toString();
                    }
                    article = new Article(
                            Integer.parseInt(edit_article_id.getText().toString()),
                            edit_article_name.getText().toString(),
                            Integer.parseInt(edit_article_code.getText().toString()),
                            Description,
                            Integer.parseInt(edit_article_prix.getText().toString()),
                            Integer.parseInt(edit_article_Qte_Stock.getText().toString())
                    );

                    db.deleteArticle(id_selected_row);
                    Boolean insert = db.insertArticle(article);
                    if (insert){
                        Toast.makeText(Edit_Article.this,"Article Edited !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Edit_Article.this,"Error in Edited the article to the database !",Toast.LENGTH_LONG).show();
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
}