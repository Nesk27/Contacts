package ru.nesk27.contacts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnActTwo;
    Button btnActThree;


    String[] names = { "Иван", "Nik", "Petya", "Sidr", "Klava", "Boris"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView lvMain = (ListView) findViewById(R.id.lvMain); // Находим список

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names); // Создаём адаптер

        lvMain.setAdapter(adapter); // Присваивем адаптер списку

        btnActTwo = (Button) findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this);

        btnActThree = (Button) findViewById(R.id.btnActThree);
        btnActThree.setOnClickListener(this);
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
        switch (v.getId())
        {
            case R.id.btnActTwo :
                Intent intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.btnActThree :
                Intent intent2 = new Intent(this, ActivityThree.class);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }
}
