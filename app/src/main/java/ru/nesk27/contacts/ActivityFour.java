package ru.nesk27.contacts;

import android.content.ContentValues;
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

public class ActivityFour extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd;
    EditText lastname, firstname;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);


        db = new DB(this);
        db.open();

    }


    @Override
    public void onClick(View v) {
        String etFirstname = firstname.getText().toString();
        String etLastname = lastname.getText().toString();



        ContentValues contentValues = new ContentValues();

        switch (v.getId())
        {
            case R.id.btnAdd:
                contentValues.put(DBHelper1.KEY_NAME, etFirstname);
                contentValues.put(DBHelper1.KEY_LASTNAME, etLastname);

                db.addRec(etLastname, etFirstname, null, 0);
                break;
            default:
                break;
        }
        db.close();
    }



}
