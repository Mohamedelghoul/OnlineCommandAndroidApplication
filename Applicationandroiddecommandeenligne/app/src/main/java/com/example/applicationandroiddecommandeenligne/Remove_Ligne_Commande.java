package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Remove_Ligne_Commande extends AppCompatActivity {

    Database db;
    TextView remove_ligne_commande_question;
    Button btn_yes_remove_ligne_commande, btn_no_remove_ligne_commande;

    int id_row_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__ligne__commande);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.4));

        db = new Database(Remove_Ligne_Commande.this);
        remove_ligne_commande_question = findViewById(R.id.remove_ligne_commande_question);
        btn_yes_remove_ligne_commande = findViewById(R.id.btn_yes_remove_ligne_commande);
        btn_no_remove_ligne_commande = findViewById(R.id.btn_no_remove_ligne_commande);

        Bundle extra = getIntent().getExtras();
        id_row_selected = extra.getInt("idSelectedRow");
        remove_ligne_commande_question.setText("Do you want to remove the order line with id '"+id_row_selected +"' ?");

        btn_yes_remove_ligne_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteLigneCommande(id_row_selected);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_no_remove_ligne_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
