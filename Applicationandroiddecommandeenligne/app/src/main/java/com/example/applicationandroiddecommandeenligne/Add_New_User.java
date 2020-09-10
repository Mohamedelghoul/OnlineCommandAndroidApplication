package com.example.applicationandroiddecommandeenligne;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Add_New_User extends AppCompatActivity {

    User user;
    String date = null;
    String login_value = null;
    Database db;

    CalendarView calendar;
    Button btn_add_new_user;
    EditText new_user_id, new_user_nom, new_user_prenom, new_user_cin, new_user_email, new_user_login, new_user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__user);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Add_New_User.this);

        calendar = (CalendarView) findViewById(R.id.new_user_date_naissance);
        new_user_id = findViewById(R.id.new_user_id);
        new_user_nom = findViewById(R.id.new_user_nom);
        new_user_prenom = findViewById(R.id.new_user_prenom);
        new_user_cin = findViewById(R.id.new_user_cin);
        new_user_email = findViewById(R.id.new_user_email);
        new_user_login = findViewById(R.id.new_user_login);
        new_user_pass = findViewById(R.id.new_user_pass);
        btn_add_new_user = findViewById(R.id.btn_add_new_user);

        new_user_id.setText(Integer.toString(db.getValidIdUser()));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String D = Integer.toString(day);
                String M = Integer.toString(month);
                if (day < 10) {
                    D = "0" + day;
                }
                if (month < 10) {
                    M = "0" + month;
                }
                date = year + "-" + M + "-" + D;
            }
        });

        btn_add_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean B = true;
                if (new_user_nom.getText().toString().isEmpty()) {
                    new_user_nom.setError("The last name field is required !");
                    new_user_nom.requestFocus();
                    B = false;
                } else if (!isNameValid(new_user_nom.getText().toString())){
                    B = false;
                    new_user_nom.setError("Last name contains just [A-Z, a-z] characters.");
                    new_user_nom.requestFocus();
                }
                if (new_user_prenom.getText().toString().isEmpty()) {
                    new_user_prenom.setError("The first name field is required !");
                    new_user_prenom.requestFocus();
                    B = false;
                } else if(!isNameValid(new_user_prenom.getText().toString())){
                    B = false;
                    new_user_prenom.setError("First name contains just [A-Z, a-z] characters.");
                    new_user_prenom.requestFocus();
                }
                if (new_user_cin.getText().toString().isEmpty()) {
                    new_user_cin.setError("The CIN number field is required !");
                    new_user_cin.requestFocus();
                    B = false;
                } else if (!isCINValid(new_user_cin.getText().toString()) || new_user_cin.getText().toString().length() != 8){
                    B = false;
                    new_user_cin.setError("CIN must contains 8 numbers.");
                    new_user_cin.requestFocus();
                }
                if (new_user_email.getText().toString().isEmpty()) {
                    new_user_email.setError("The email field is required !");
                    new_user_email.requestFocus();
                    B = false;
                } else if (!isEmailValid(new_user_email.getText().toString())){
                    B = false;
                    new_user_email.setError("Invalid email address !");
                    new_user_email.requestFocus();
                }
                if (new_user_login.getText().toString().isEmpty()) {
                    new_user_login.setError("The username field is required !");
                    new_user_login.requestFocus();
                    B = false;
                } else if (!isUsernameValid(new_user_login.getText().toString())){
                    B = false;
                    new_user_login.setError("Username contains just [A-Z, a-z, 0-9, -._] characters.");
                    new_user_login.requestFocus();
                }
                if (new_user_pass.getText().toString().isEmpty()) {
                    new_user_pass.setError("The password field is required !");
                    new_user_pass.requestFocus();
                    B = false;
                }
                if (date == null) {
                    B = false;
                    Toast.makeText(Add_New_User.this, "Please select a birthday date !", Toast.LENGTH_LONG).show();
                }
                if (B) {
                    user = new User(
                            Integer.parseInt(new_user_id.getText().toString()),
                            new_user_nom.getText().toString(),
                            new_user_prenom.getText().toString(),
                            date,
                            Integer.parseInt(new_user_cin.getText().toString()),
                            new_user_email.getText().toString(),
                            new_user_login.getText().toString(),
                            new_user_pass.getText().toString()
                    );

                    Boolean insert = db.insertUser(user);
                    if (insert){
                        Toast.makeText(Add_New_User.this,"User Added !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Add_New_User.this,"Error in adding the user to the database !",Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isNameValid(CharSequence Name){
        return Pattern.compile("[a-zA-Z ]+").matcher(Name).matches();
    }

    boolean isUsernameValid(CharSequence Username){
        return Pattern.compile("[a-zA-Z0-9-._]+").matcher(Username).matches();
    }

    boolean isCINValid(CharSequence CIN){
        return Pattern.compile("[0-9]+").matcher(CIN).matches();
    }
}