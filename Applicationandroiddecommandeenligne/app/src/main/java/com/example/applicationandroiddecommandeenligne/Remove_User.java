package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Remove_User extends AppCompatActivity {

    Database db;
    TextView remove_user_question;
    Button btn_yes_remove_user,btn_no_remove_user;

    int id_row_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove__user);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.4));

        db = new Database(Remove_User.this);
        remove_user_question = findViewById(R.id.remove_user_question);
        btn_no_remove_user = findViewById(R.id.btn_no_remove_user);
        btn_yes_remove_user = findViewById(R.id.btn_yes_remove_user);

        Bundle extra = getIntent().getExtras();
        id_row_selected = extra.getInt("idSelectedRow");
        remove_user_question.setText("Do you want to remove the user with id '"+id_row_selected +"' ?");

        btn_yes_remove_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteUser(id_row_selected);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_no_remove_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}