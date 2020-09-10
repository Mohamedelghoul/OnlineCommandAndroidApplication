package com.example.applicationandroiddecommandeenligne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Edit_User extends AppCompatActivity {

    String login_selected_row;
    int id_selected_row;
    String date = null;
    User user;
    Database db;

    CalendarView calendar;
    Button btn_edit_user;
    EditText edit_user_id, edit_user_nom, edit_user_prenom, edit_user_cin, edit_user_email, edit_user_login, edit_user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Edit_User.this);

        calendar = (CalendarView) findViewById(R.id.edit_user_date_naissance);
        edit_user_id = findViewById(R.id.edit_user_id);
        edit_user_nom = findViewById(R.id.edit_user_nom);
        edit_user_prenom = findViewById(R.id.edit_user_prenom);
        edit_user_cin = findViewById(R.id.edit_user_cin);
        edit_user_email = findViewById(R.id.edit_user_email);
        edit_user_login = findViewById(R.id.edit_user_login);
        edit_user_pass = findViewById(R.id.edit_user_pass);
        btn_edit_user = findViewById(R.id.btn_edit_user);

        Bundle extra = getIntent().getExtras();
        login_selected_row = extra.getString("loginSelectedRow");
        id_selected_row = extra.getInt("idSelectedRow");
        user = db.getUserByLogin(login_selected_row);

        edit_user_id.setText(login_selected_row);
        edit_user_nom.setText(user.getNom());
        edit_user_prenom.setText(user.getPrenom());
        edit_user_cin.setText(Integer.toString(user.getCIN()));
        edit_user_email.setText(user.getEmail());
        edit_user_login.setText(user.getLogin());
        edit_user_pass.setText(user.getPassword());

        int year = Integer.parseInt(user.getDate_Naissance().substring(0,4));
        int month = Integer.parseInt(user.getDate_Naissance().substring(5,7));
        int day = Integer.parseInt(user.getDate_Naissance().substring(8,10));
        calendar.setDate(getDateAsLong(year,month,day));
        date = user.getDate_Naissance();
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

        btn_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean B = true;
                if (edit_user_nom.getText().toString().isEmpty()) {
                    edit_user_nom.setError("The last name field cannot be empty !");
                    edit_user_nom.requestFocus();
                    B = false;
                } else if (!isNameValid(edit_user_nom.getText().toString())) {
                    B = false;
                    edit_user_nom.setError("Last name contains just [A-Z, a-z] characters.");
                    edit_user_nom.requestFocus();
                }
                if (edit_user_prenom.getText().toString().isEmpty()) {
                    edit_user_prenom.setError("The first name field cannot be empty !");
                    edit_user_prenom.requestFocus();
                    B = false;
                } else if (!isNameValid(edit_user_prenom.getText().toString())) {
                    B = false;
                    edit_user_prenom.setError("First name contains just [A-Z, a-z] characters.");
                    edit_user_prenom.requestFocus();
                }
                if (edit_user_cin.getText().toString().isEmpty()) {
                    edit_user_cin.setError("The CIN number field is required !");
                    edit_user_cin.requestFocus();
                    B = false;
                } else if (!isCINValid(edit_user_cin.getText().toString()) || edit_user_cin.getText().toString().length() != 8) {
                    B = false;
                    edit_user_cin.setError("CIN must contains 8 numbers.");
                    edit_user_cin.requestFocus();
                }
                if (edit_user_email.getText().toString().isEmpty()) {
                    edit_user_email.setError("The email field is required !");
                    edit_user_email.requestFocus();
                    B = false;
                } else if (!isEmailValid(edit_user_email.getText().toString())) {
                    B = false;
                    edit_user_email.setError("Invalid email address !");
                    edit_user_email.requestFocus();
                }

                if (edit_user_pass.getText().toString().isEmpty()) {
                    edit_user_pass.setError("The password field is required !");
                    edit_user_pass.requestFocus();
                    B = false;
                }
                if (B) {
                    user = new User(
                            id_selected_row,
                            edit_user_nom.getText().toString(),
                            edit_user_prenom.getText().toString(),
                            date,
                            Integer.parseInt(edit_user_cin.getText().toString()),
                            edit_user_email.getText().toString(),
                            login_selected_row,
                            edit_user_pass.getText().toString()
                    );
                    db.deleteUser(user.getID_User());
                    Boolean insert = db.insertUser(user);
                    if (insert) {
                        Toast.makeText(Edit_User.this, "User Edited !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Edit_User.this, "Error in Editing the user to the database !", Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    public long getDateAsLong(int year, int month, int date) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTimeInMillis();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isNameValid(CharSequence Name){
        return Pattern.compile("[a-zA-Z ]+").matcher(Name).matches();
    }


    boolean isCINValid(CharSequence CIN){
        return Pattern.compile("[0-9]+").matcher(CIN).matches();
    }
}