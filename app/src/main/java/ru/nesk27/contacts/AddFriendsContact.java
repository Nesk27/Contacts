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

public class AddFriendsContact extends AppCompatActivity implements View.OnClickListener {

    int DIALOG_DATE = 1;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;

    Button btnAdd, btnBack;
    EditText lastname, firstname, surname, phone;
    TextView date;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friendcontact);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);


        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);
        surname = (EditText) findViewById(R.id.surname);
        phone = (EditText) findViewById(R.id.phone);
        date = (TextView) findViewById(R.id.date);


        db = new DB(this);
        db.open();

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
            date.setText(myDay + "." + myMonth + "." + myYear);
        }
    };

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
                    Toast toastError = Toast.makeText(AddFriendsContact.this, "Контакт не добавлен! Заполните все данные!", Toast.LENGTH_SHORT);
                    toastError.show();
                } else {
                    db.addRec(etLastname, etFirstname, etSurname, etPhone, etDate, R.drawable.ic_account_box_black_36dp);
                    Toast toast = Toast.makeText(AddFriendsContact.this, "Контакт успешно добавлен!", Toast.LENGTH_SHORT);
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
