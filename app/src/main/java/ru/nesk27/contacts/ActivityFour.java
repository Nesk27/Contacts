package ru.nesk27.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityFour extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnBack;
    EditText lastname, firstname, surname, phone, date;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);
        surname = (EditText) findViewById(R.id.surname);
        phone = (EditText) findViewById(R.id.phone);
        date = (EditText) findViewById(R.id.date);


        db = new DB(this);
        db.open();

    }


    @Override
    public void onClick(View v) {
        String etFirstname = firstname.getText().toString();
        String etLastname = lastname.getText().toString();
        String etSurname = surname.getText().toString();
        String etPhone = phone.getText().toString();
        String etDate = date.getText().toString();



        switch (v.getId())
        {
            case R.id.btnAdd:

                if (!"".equalsIgnoreCase(etFirstname) & !"".equalsIgnoreCase(etLastname)) {
                    db.addRec(etLastname, etFirstname, etSurname, etPhone, etDate, R.drawable.ic_account_box_black_36dp);
                    Toast toast = Toast.makeText(ActivityFour.this, "Контакт успешно добавлен!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, ActivityThree.class);
                    startActivity(intent);
                    db.close();
                } else {
                    Toast toastError = Toast.makeText(ActivityFour.this, "Контакт не добавлен! Заполните все данные!", Toast.LENGTH_SHORT);
                    toastError.show();
                }

                break;
            case R.id.btnBack:
                Intent intent = new Intent(this, ActivityThree.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }



}
