package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Remove_Commande extends AppCompatActivity {

    Database db;
    TextView remove_commande_question;
    Button btn_yes_remove_commande,btn_no_remove_commande;

    int id_row_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__commande);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.4));

        db = new Database(Remove_Commande.this);
        remove_commande_question = findViewById(R.id.remove_commande_question);
        btn_yes_remove_commande = findViewById(R.id.btn_yes_remove_commande);
        btn_no_remove_commande = findViewById(R.id.btn_no_remove_commande);

        Bundle extra = getIntent().getExtras();
        id_row_selected = extra.getInt("idSelectedRow");
        remove_commande_question.setText("Do you want to remove the order with id '"+id_row_selected +"' ?");

        btn_yes_remove_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteCommande(id_row_selected);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_no_remove_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}