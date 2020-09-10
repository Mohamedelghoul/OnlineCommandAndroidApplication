package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Add_New_Client extends AppCompatActivity {

    Database db;
    Client client;

    Button btn_add_new_client,btn_add_new_client_generate_code;
    EditText new_client_id,new_client_name,new_client_contact_name,new_client_code,new_client_adr,new_client_code_tva,new_client_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__client);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Add_New_Client.this);

        new_client_id = findViewById(R.id.new_client_id);
        new_client_name = findViewById(R.id.new_client_name);
        new_client_contact_name = findViewById(R.id.new_client_contact_name);
        new_client_code = findViewById(R.id.new_client_code);
        new_client_adr = findViewById(R.id.new_client_adr);
        new_client_code_tva = findViewById(R.id.new_client_code_tva);
        new_client_description = findViewById(R.id.new_client_description);
        btn_add_new_client_generate_code = findViewById(R.id.btn_add_new_client_generate_code);
        btn_add_new_client = findViewById(R.id.btn_add_new_client);

        new_client_id.setText(Integer.toString(db.getValidIdClient()));

        btn_add_new_client_generate_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_client_code.setText(Integer.toString(getRandomClientCode()));
            }
        });

        btn_add_new_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean B = true;
                String Adr = null;
                String Description = null;
                int code_tva = 0;
                if (new_client_name.getText().toString().isEmpty()) {
                    new_client_name.setError("The name field is required !");
                    new_client_name.requestFocus();
                    B = false;
                } else if (!isNameValid(new_client_name.getText().toString())){
                    B = false;
                    new_client_name.setError("Name contains just [A-Z, a-z] characters.");
                    new_client_name.requestFocus();
                }
                if (new_client_contact_name.getText().toString().isEmpty()) {
                    new_client_contact_name.setError("The Contact name field is required !");
                    new_client_contact_name.requestFocus();
                    B = false;
                }else if (!isNameValid(new_client_contact_name.getText().toString())){
                    B = false;
                    new_client_contact_name.setError("Contact name contains just [A-Z, a-z] characters.");
                    new_client_contact_name.requestFocus();
                }
                if (new_client_code.getText().toString().isEmpty()) {
                    new_client_code.setError("The code field is required !");
                    new_client_code.requestFocus();
                    B = false;
                }else if (!isCodeValid(new_client_code.getText().toString()) || new_client_code.getText().toString().length() != 6){
                    new_client_code.setError("Code must contains 6 numbers.");
                    new_client_code.requestFocus();
                    B = false;
                }
                if(new_client_adr.getText().toString().isEmpty()){
                    new_client_adr.setError("The address field is required !");
                    new_client_adr.requestFocus();
                    B = false;
                }else if(!isAddressValid(new_client_adr.getText().toString())){
                    new_client_adr.setError("Address contains just [A-Z / a-z / 0-9 / ,] characters");
                    new_client_adr.requestFocus();
                    B = false;
                }
                if(B){

                    if (!new_client_description.getText().toString().isEmpty()){
                        Description = new_client_description.getText().toString();
                    }
                    if(!new_client_code_tva.getText().toString().isEmpty()){
                        code_tva = Integer.parseInt(new_client_code_tva.getText().toString());
                    }
                    client = new Client(
                            Integer.parseInt(new_client_id.getText().toString()),
                            new_client_name.getText().toString(),
                            Integer.parseInt(new_client_code.getText().toString()),
                            Description,
                            new_client_adr.getText().toString(),
                            code_tva,
                            new_client_contact_name.getText().toString()
                    );
                    Boolean insert = db.insertClient(client);
                    if (insert){
                        Toast.makeText(Add_New_Client.this,"Client Added !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Add_New_Client.this,"Error in adding the client to the database !",Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
    }

    boolean isNameValid(CharSequence Name){
        return Pattern.compile("[a-zA-Z ]+").matcher(Name).matches();
    }
    boolean isCodeValid(CharSequence CIN){
        return Pattern.compile("[0-9]+").matcher(CIN).matches();
    }
    boolean isAddressValid(CharSequence Adr){
        return Pattern.compile("[a-zA-Z0-9,. ]+").matcher(Adr).matches();
    }
    public int getRandomClientCode() {
        int code;
        boolean isValid = true;
        Cursor cursor = db.readAllClientsData();
        do {
            code = (int) (Math.random() * 1000001);
            while (cursor.moveToNext()) {
                if(code == cursor.getInt(2)){
                    isValid = false;
                }
            }
        } while (!isValid);
        return code;
    }
}