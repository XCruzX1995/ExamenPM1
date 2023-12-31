package com.example.Examen_PM1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Examen_PM1.transactions.Transactions;
import com.example.PM1Examen0423.R;

public class ActivityUpdate extends AppCompatActivity {
    SQLiteConexion connection;
    Spinner spCountry;
    String country;
    Integer idReceived;
    EditText updateName, updatePhone, updateNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        connection = new SQLiteConexion(this, Transactions.nameDataBase, null, 1);

        spCountry = findViewById(R.id.spCountry);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCountry.setAdapter(adapter);

        updateName = findViewById(R.id.etUpdateName);
        updatePhone = findViewById(R.id.etUpdatePhone);
        updateNote = findViewById(R.id.etUpdateNote);

        Bundle recoverValuesBundle = this.getIntent().getExtras();

        idReceived = recoverValuesBundle.getInt("id");
        updateName.setText(recoverValuesBundle.getString("name"));
        updatePhone.setText(recoverValuesBundle.getString("phone"));
        updateNote.setText(recoverValuesBundle.getString("note"));

        Button btnUpdate = findViewById(R.id.btnUpdateContact);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
                Intent intentListView = new Intent(getApplicationContext(), ActivityListView.class);
                startActivity(intentListView);
            }
        });

    }

    private void updateContact() {
        SQLiteDatabase db = connection.getWritableDatabase();
        String[] params = {String.valueOf(idReceived)};

        ContentValues values = new ContentValues();
        country = spCountry.getSelectedItem().toString();
        values.put(Transactions.country, country);
        values.put(Transactions.name, updateName.getText().toString());
        values.put(Transactions.phone, updatePhone.getText().toString());
        values.put(Transactions.note, updateNote.getText().toString());

        db.update(Transactions.tableContacts, values, Transactions.id + "=?", params);
        Toast.makeText(getApplicationContext(), "Contacto Actualizado", Toast.LENGTH_LONG).show();

    }
}