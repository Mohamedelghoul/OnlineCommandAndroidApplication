package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Remove_Client extends AppCompatActivity {

    Database db;
    TextView remove_client_question;
    Button btn_yes_remove_client,btn_no_remove_client;

    int id_row_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__client);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.4));

        db = new Database(Remove_Client.this);
        remove_client_question = findViewById(R.id.remove_client_question);
        btn_no_remove_client = findViewById(R.id.btn_no_remove_client);
        btn_yes_remove_client = findViewById(R.id.btn_yes_remove_client);

        Bundle extra = getIntent().getExtras();
        id_row_selected = extra.getInt("idSelectedRow");
        remove_client_question.setText("Do you want to remove the client with id '"+id_row_selected +"' ?");

        btn_yes_remove_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteClient(id_row_selected);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_no_remove_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}