package com.example.applicationandroiddecommandeenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Edit_Client extends AppCompatActivity {

    Database db;
    int id_selected_row;
    Client client;

    EditText edit_client_id, edit_client_name, edit_client_contact_name, edit_client_code, edit_client_adr, edit_client_code_tva, edit_client_description;
    Button btn_edit_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__client);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels * 0.95), (int) (dm.heightPixels * 0.7));

        db = new Database(Edit_Client.this);

        edit_client_id = findViewById(R.id.edit_client_id);
        edit_client_name = findViewById(R.id.edit_client_name);
        edit_client_contact_name = findViewById(R.id.edit_client_contact_name);
        edit_client_code = findViewById(R.id.edit_client_code);
        edit_client_adr = findViewById(R.id.edit_client_adr);
        edit_client_code_tva = findViewById(R.id.edit_client_code_tva);
        edit_client_description = findViewById(R.id.edit_client_description);

        btn_edit_client = findViewById(R.id.btn_edit_client);

        Bundle extra = getIntent().getExtras();
        id_selected_row = extra.getInt("idSelectedRow");

        client = db.getClientById(id_selected_row);

        edit_client_id.setText(Integer.toString(id_selected_row));
        edit_client_name.setText(client.getNom());
        edit_client_contact_name.setText(client.getNom_Contact());
        edit_client_code.setText(Integer.toString(client.getCode()));
        edit_client_adr.setText(client.getAdr());
        edit_client_code_tva.setText(Integer.toString(client.getCode_TVA()));
        if (client.getDescription() != null) {
            edit_client_description.setText(client.getDescription());
        }

        btn_edit_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean B = true;
                String Adr = null;
                String Description = null;
                int code_tva = 0;
                if (edit_client_name.getText().toString().isEmpty()) {
                    edit_client_name.setError("The name field cannot be empty !");
                    edit_client_name.requestFocus();
                    B = false;
                } else if (!isNameValid(edit_client_name.getText().toString())) {
                    B = false;
                    edit_client_name.setError("Name contains just [A-Z, a-z] characters.");
                    edit_client_name.requestFocus();
                }
                if (edit_client_contact_name.getText().toString().isEmpty()) {
                    edit_client_contact_name.setError("The Contact name field cannot be empty !");
                    edit_client_contact_name.requestFocus();
                    B = false;
                } else if (!isNameValid(edit_client_contact_name.getText().toString())) {
                    B = false;
                    edit_client_contact_name.setError("Contact name contains just [A-Z, a-z] characters.");
                    edit_client_contact_name.requestFocus();
                }
                if(edit_client_adr.getText().toString().isEmpty()){
                    edit_client_adr.setError("The address field is required !");
                    edit_client_adr.requestFocus();
                    B = false;
                }else if(!isAddressValid(edit_client_adr.getText().toString())){
                    edit_client_adr.setError("Address contains just [A-Z / a-z / 0-9 / ,] characters");
                    edit_client_adr.requestFocus();
                    B = false;
                }
                if (B) {
                    if (!edit_client_description.getText().toString().isEmpty()) {
                        Description = edit_client_description.getText().toString();
                    }
                    if (!edit_client_code_tva.getText().toString().isEmpty()) {
                        code_tva = Integer.parseInt(edit_client_code_tva.getText().toString());
                    }
                    client = new Client(
                            Integer.parseInt(edit_client_id.getText().toString()),
                            edit_client_name.getText().toString(),
                            Integer.parseInt(edit_client_code.getText().toString()),
                            Description,
                            edit_client_adr.getText().toString(),
                            code_tva,
                            edit_client_contact_name.getText().toString()
                    );

                    db.deleteClient(client.getID_Client());
                    boolean insert = db.insertClient(client);
                    if(insert) {
                        Toast.makeText(Edit_Client.this, "Client Edited !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Edit_Client.this, "Error on Client Edit ! ", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    boolean isNameValid(CharSequence Name) {
        return Pattern.compile("[a-zA-Z ]+").matcher(Name).matches();
    }

    boolean isCodeValid(CharSequence CIN) {
        return Pattern.compile("[0-9]+").matcher(CIN).matches();
    }

    boolean isAddressValid(CharSequence Adr){
        return Pattern.compile("[a-zA-Z0-9,. ]+").matcher(Adr).matches();
    }
}