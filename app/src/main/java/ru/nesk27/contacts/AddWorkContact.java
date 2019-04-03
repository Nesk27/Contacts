package ru.nesk27.contacts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddWorkContact extends AppCompatActivity implements View.OnClickListener{


    Button btnAdd, btnBack;
    EditText lastname, firstname, surname, phone, work, workphone;

    DB2 db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_contact);

        btnAdd = (Button) findViewById(R.id.btnAdd2);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(this);


        lastname = (EditText) findViewById(R.id.lastname2);
        firstname = (EditText) findViewById(R.id.firstname2);
        surname = (EditText) findViewById(R.id.surname2);
        phone = (EditText) findViewById(R.id.phone2);
        work = (EditText) findViewById(R.id.work);
        workphone = (EditText) findViewById(R.id.workphone);



        db2 = new DB2(this);
        db2.open2();

    }


    @Override
    public void onClick(View v) {
        String etFirstname = firstname.getText().toString();
        String etLastname = lastname.getText().toString();
        String etSurname = surname.getText().toString();
        String etPhone = phone.getText().toString();
        String etWork = work.getText().toString();
        String etWorkphone = workphone.getText().toString();


        switch (v.getId())
        {
            case R.id.btnAdd2:

                if (etFirstname.equalsIgnoreCase("") | etLastname.equalsIgnoreCase("") | etPhone.equalsIgnoreCase("") | etWork.equalsIgnoreCase("") | etWorkphone.equalsIgnoreCase("")) {
                    Toast toastError = Toast.makeText(AddWorkContact.this, "Контакт не добавлен! Заполните все данные!", Toast.LENGTH_SHORT);
                    toastError.show();
                } else {
                    db2.addRec2(etLastname, etFirstname, etSurname, etPhone, etWork, etWorkphone, R.drawable.ic_account_box_black_36dp);
                    Toast toast = Toast.makeText(AddWorkContact.this, "Контакт успешно добавлен!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    db2.close2();
                }

                break;
            case R.id.btnBack2:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }



}


