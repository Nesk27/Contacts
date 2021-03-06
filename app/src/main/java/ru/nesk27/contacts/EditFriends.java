package ru.nesk27.contacts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFriends extends AppCompatActivity {

    int DIALOG_DATE = 1;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;

    Button btnAdd, btnBack;
    EditText mlastname, mfirstname, msurname, mphone;
    Long id;
    TextView mdate;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);

        db = new DB(this);
        db.open();

        mlastname = findViewById(R.id.lastnameEditW);
        mfirstname = findViewById(R.id.firstnameEditW);
        msurname = findViewById(R.id.surnameEditW);
        mphone = findViewById(R.id.phoneEditW);
        mdate = findViewById(R.id.dateEditW);

        btnAdd = findViewById(R.id.btnConfirmW);
        btnBack = findViewById(R.id.btnBackEditW);

        id = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Lastname = extras.getString(DB.KEY_LASTNAME);
            String Firstname = extras.getString(DB.KEY_NAME);
            String Surname = extras.getString(DB.KEY_SURNAME);
            String Phone = extras.getString(DB.KEY_PHONE);
            String Date = extras.getString(DB.KEY_DATE);
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
            if (Date != null) {
                mdate.setText(Date);
            }

        }

        btnAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                id = null;
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    mlastname = findViewById(R.id.lastnameEditW);
                    mfirstname = findViewById(R.id.firstnameEditW);
                    msurname = findViewById(R.id.surnameEditW);
                    mphone = findViewById(R.id.phoneEditW);
                    mdate = findViewById(R.id.dateEditW);
                    id = extras.getLong(DB.KEY_ID);
                    String etFirstname = mfirstname.getText().toString();
                    String etLastname = mlastname.getText().toString();
                    String etSurname = msurname.getText().toString();
                    String etPhone = mphone.getText().toString();
                    String etDate = mdate.getText().toString();

                    if (etFirstname.equalsIgnoreCase("") | etLastname.equalsIgnoreCase("") | etPhone.equalsIgnoreCase("") | etDate.equalsIgnoreCase(""))
                    {
                        Toast toastError = Toast.makeText(EditFriends.this, "Контакт не изменен! Заполните все данные!", Toast.LENGTH_SHORT);
                        toastError.show();
                    } else {
                        db.updateDB(etLastname, etFirstname, etSurname, etPhone, etDate, id);
                        Toast toast = Toast.makeText(EditFriends.this, "Контакт успешно изменен!", Toast.LENGTH_SHORT);
                        toast.show();
                        db.close();
                        Intent intent = new Intent(EditFriends.this, MainActivity.class);
                        startActivity(intent);
                    }
                }


            }

        });


        btnBack.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditFriends.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    public void onClickDate(View view) {
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear + 1;
            myDay = dayOfMonth;
            mdate.setText(myDay + "." + myMonth + "." + myYear);
        }
    };



}
