package ru.nesk27.contacts;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityTwo extends AppCompatActivity implements View.OnClickListener  {

    Button btnAdd, btnFind, btnDelete, btnUpd, btnDel;
    EditText lastname, firstname, id;

    DBHelper1 dbHelper1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnFind = (Button) findViewById(R.id.btnFind);
        btnFind.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        lastname = (EditText) findViewById(R.id.lastname);
        firstname = (EditText) findViewById(R.id.firstname);
        id = (EditText) findViewById(R.id.id);

        dbHelper1 = new DBHelper1(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String etFirstname = firstname.getText().toString();
        String etLastname = lastname.getText().toString();
        String etId = id.getText().toString();

        SQLiteDatabase database = dbHelper1.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId())
        {
            case R.id.btnAdd:
                contentValues.put(DBHelper1.KEY_NAME, etFirstname);
                contentValues.put(DBHelper1.KEY_LASTNAME, etLastname);

                database.insert(DBHelper1.TABLE_CONTACTS, null, contentValues);
                break;
            case R.id.btnFind:
                Cursor cursor = database.query(DBHelper1.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper1.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper1.KEY_NAME);
                    int lastnameIndex = cursor.getColumnIndex(DBHelper1.KEY_LASTNAME);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex) + ", lastname = " + cursor.getString(lastnameIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "found 0 rows");

                cursor.close();
                break;
            case R.id.btnDelete:
                database.delete(DBHelper1.TABLE_CONTACTS, null, null); // Пока удаляются ВСЕ! записи из бд
                break;
            case R.id.btnUpd:
                if (etId.equalsIgnoreCase(""))
                {
                    break;
                }
                contentValues.put(DBHelper1.KEY_LASTNAME, etLastname);
                contentValues.put(DBHelper1.KEY_NAME, etFirstname);
                int updCount = database.update(DBHelper1.TABLE_CONTACTS, contentValues, DBHelper1.KEY_NAME + "= ?", new String[] {etFirstname});

                Log.d("mLog", "updates rows count = " + updCount);
                break;
            case R.id.btnDel:
                if (etId.equalsIgnoreCase(""))
                {
                    break;
                }
                int delCount = database.delete(DBHelper1.TABLE_CONTACTS, DBHelper1.KEY_NAME + "= ?", new String[]{etFirstname});
                Log.d("mLog", "Delete rows count = " + delCount);

                break;
            default:
                break;
        }
        dbHelper1.close();
    }

}
