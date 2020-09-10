package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView Login, ID_User, Nom, CIN, Date_Naissance, Email;
    Button btn_actions_lists,btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Login = (TextView) findViewById(R.id.Login);
        ID_User = (TextView) findViewById(R.id.var_id);
        Nom = (TextView) findViewById(R.id.var_name);
        CIN = (TextView) findViewById(R.id.var_cin);
        Date_Naissance = (TextView) findViewById(R.id.var_birthday);
        Email = (TextView) findViewById(R.id.var_email);
        btn_actions_lists = (Button) findViewById(R.id.btn_actions_lists);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        Database db = new Database(Profile.this);
        User user;

        Bundle extra = getIntent().getExtras();
        String login_value = extra.getString("Login");
        user = db.getUserByLogin(login_value);
        if (user != null) {
            Login.setText(user.getLogin());
            ID_User.setText(Integer.toString(user.getID_User()));
            Nom.setText(user.getNom() + " " + user.getPrenom());
            CIN.setText(Integer.toString(user.getCIN()));
            Date_Naissance.setText(user.getDate_Naissance());
            Email.setText(user.getEmail());
        }
        else{
            Login.setText("connexion to database lost");
        }

        btn_actions_lists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Actions_Lists.class);
                intent.putExtra("Login",Login.getText().toString());
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Authentification.class);
                startActivity(intent);
            }
        });
    }

}