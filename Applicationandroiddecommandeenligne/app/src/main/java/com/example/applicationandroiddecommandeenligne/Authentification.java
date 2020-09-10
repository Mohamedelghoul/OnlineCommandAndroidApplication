package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Authentification extends AppCompatActivity {

    EditText username ,password;
    TextView message;
    Button login ;
    Database db = new Database(Authentification.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        username = (EditText) findViewById(R.id.sign_in_id);
        password = (EditText) findViewById(R.id.sign_in_pass);
        message = (TextView) findViewById(R.id.sign_in_msg);
        login = (Button) findViewById(R.id.sign_in_btn);



        User U = new User(1,"admin","admin","1998-05-08",12345678,"sdfsdf@gmail.com","admin","admin");
        if(!db.userExists(U)){
            db.insertUser(U);
        }

        User u = new User(2,"Mohamed","El Ghoul","1998-05-08",11111111,"Mohamed.elghoul@esprit.tn","mohamed","mohamed");
       if(!db.userExists(u)){
           db.insertUser(u);
       }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.isEmpty() && pass.isEmpty()){
                    username.setError("The id field is required !");
                    password.setError("The password field is required !");
                }
                else if (user.isEmpty()) {
                    username.setError("The id field is required !");
                }
                else if (pass.isEmpty()) {
                    password.setError("The password field is required !");
                }
                else if (db.validateLogin(user,pass)) {
                    Intent intent = new Intent(Authentification.this, Profile.class);
                    intent.putExtra("Login",user);
                    startActivity(intent);
                    finish();
                }
                else{
                    message.setText("Username and password does not match !");
                }
            }
        });
    }

}


