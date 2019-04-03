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

public class EditWork extends AppCompatActivity {


    Button btnAdd, btnBack;
    EditText mlastname, mfirstname, msurname, mphone, mwork, mworkphone;
    Long id;


    DB2 db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        db2 = new DB2(this);
        db2.open2();

        mlastname = findViewById(R.id.lastnameEditW2);
        mfirstname = findViewById(R.id.firstnameEditW2);
        msurname = findViewById(R.id.surnameEditW2);
        mphone = findViewById(R.id.phoneEditW2);
        mwork = findViewById(R.id.workEdit);
        mworkphone = findViewById(R.id.workphoneEdit);


        btnAdd = findViewById(R.id.btnConfirmW2);
        btnBack = findViewById(R.id.btnBackEditW2);

        id = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Lastname = extras.getString(DB2.KEY_LASTNAME);
            String Firstname = extras.getString(DB2.KEY_NAME);
            String Surname = extras.getString(DB2.KEY_SURNAME);
            String Phone = extras.getString(DB2.KEY_PHONE);
            String Work = extras.getString(DB2.KEY_WORK);
            String Workphone = extras.getString(DB2.KEY_WORK_PHONE);
            id = extras.getLong(DB.KEY_ID);

            if (Lastname != null) {
                mlastname.setText(Lastname);
            }

            if (Firstname != null) {
                mfirstname.setText(Firstname);
            }

            if (Surname != null) {
                msurname.setText(Surname);
            } else
                msurname.setText("");

            if (Phone != null) {
                mphone.setText(Phone);
            }
            if (Work != null) {
                mwork.setText(Work);
            }
            if (Workphone != null) {
                mworkphone.setText(Workphone);
            }

        }

        btnAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                id = null;
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    mlastname = findViewById(R.id.lastnameEditW2);
                    mfirstname = findViewById(R.id.firstnameEditW2);
                    msurname = findViewById(R.id.surnameEditW2);
                    mphone = findViewById(R.id.phoneEditW2);
                    mwork = findViewById(R.id.workEdit);
                    mworkphone = findViewById(R.id.workphoneEdit);
                    id = extras.getLong(DB.KEY_ID);
                    String etFirstname = mfirstname.getText().toString();
                    String etLastname = mlastname.getText().toString();
                    String etSurname = msurname.getText().toString();
                    String etPhone = mphone.getText().toString();
                    String etWork = mwork.getText().toString();
                    String etWorkphone = mworkphone.getText().toString();

                    if (etFirstname.equalsIgnoreCase("") | etLastname.equalsIgnoreCase("") | etPhone.equalsIgnoreCase("") | etWork.equalsIgnoreCase("") | etWorkphone.equalsIgnoreCase(""))
                    {
                        Toast toastError = Toast.makeText(EditWork.this, "Контакт не изменен! Заполните все данные!", Toast.LENGTH_SHORT);
                        toastError.show();
                    } else {
                        db2.updateDB2(etLastname, etFirstname, etSurname, etPhone, etWork, etWorkphone, id);
                        Toast toast = Toast.makeText(EditWork.this, "Контакт успешно изменен!", Toast.LENGTH_SHORT);
                        toast.show();
                        db2.close2();
                        Intent intent = new Intent(EditWork.this, MainActivity.class);
                        startActivity(intent);
                    }
                }


            }

        });


        btnBack.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditWork.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


}

