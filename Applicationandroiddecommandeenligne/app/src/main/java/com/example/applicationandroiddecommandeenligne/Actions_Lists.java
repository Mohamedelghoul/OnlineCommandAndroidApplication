package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Actions_Lists extends AppCompatActivity {

    String login_value;

    TextView btn_close,login;
    Button btn_list_clients,btn_list_commandes,btn_list_articles,btn_list_users;
    androidx.constraintlayout.widget.ConstraintLayout pop_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_lists);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.8), (int) (dm.heightPixels * 0.6));

        btn_close = (TextView) findViewById(R.id.btn_close);
        btn_list_articles = (Button) findViewById(R.id.btn_list_articles);
        btn_list_commandes = (Button) findViewById(R.id.btn_list_commandes);
        btn_list_users = (Button) findViewById(R.id.btn_list_users);
        btn_list_clients = (Button) findViewById(R.id.btn_list_clients);
        pop_up = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.pop_up);

        Bundle extra = getIntent().getExtras();
        login_value = extra.getString("Login");
        if (!login_value.equals("admin")) {
            btn_list_users.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = pop_up.getLayoutParams();
            params.height = (int) (dm.heightPixels * 0.4);
            pop_up.setLayoutParams(params);
        }



        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_list_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Actions_Lists.this, Users_List.class);
                intent.putExtra("Login",login_value);
                startActivity(intent);
                finish();
            }
        });

        btn_list_clients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Actions_Lists.this, Clients_List.class);
                intent.putExtra("Login",login_value);
                startActivity(intent);
                finish();
            }
        });

        btn_list_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Actions_Lists.this, Articles_List.class);
                intent.putExtra("Login",login_value);
                startActivity(intent);
                finish();
            }
        });

        btn_list_commandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Actions_Lists.this, Commandes_List.class);
                intent.putExtra("Login",login_value);
                startActivity(intent);
                finish();
            }
        });

    }
}