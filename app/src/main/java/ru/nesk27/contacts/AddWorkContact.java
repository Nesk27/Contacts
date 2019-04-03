package ru.nesk27.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWorkContact extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnBack;
    EditText lastname, firstname, surname, phone, date;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wokcontact);

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

                if (etFirstname.equalsIgnoreCase("") | etLastname.equalsIgnoreCase("") | etPhone.equalsIgnoreCase("") | etDate.equalsIgnoreCase("")) {
                    Toast toastError = Toast.makeText(AddWorkContact.this, "Контакт не добавлен! Заполните все данные!", Toast.LENGTH_SHORT);
                    toastError.show();
                } else {
                    db.addRec(etLastname, etFirstname, etSurname, etPhone, etDate, R.drawable.ic_account_box_black_36dp);
                    Toast toast = Toast.makeText(AddWorkContact.this, "Контакт успешно добавлен!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    db.close();
                }

                break;
            case R.id.btnBack:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }



}
