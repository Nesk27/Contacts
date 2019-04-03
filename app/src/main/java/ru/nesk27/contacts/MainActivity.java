package ru.nesk27.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {


    private static final int CM_DELETE_ID = 1;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursor = db.getAllData();
        startManagingCursor(cursor);

        // формируем столбцы сопоставления
        String[] from = new String[] { DB.KEY_PHOTO, DB.KEY_LASTNAME, DB.KEY_NAME, DB.KEY_SURNAME, DB.KEY_PHONE, DB.KEY_DATE };
        int[] to = new int[] { R.id.ivPhoto, R.id.tvLastname, R.id.tvName, R.id.tvSurname, R.id.tvPhone, R.id.tvDate };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvData);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, EditWork.class);
                i.putExtra(DB.KEY_ID, id);
                i.putExtra(DB.KEY_PHOTO, cursor.getString(
                        cursor.getColumnIndexOrThrow(DB.KEY_PHOTO)));
                i.putExtra(DB.KEY_LASTNAME, cursor.getString(
                        cursor.getColumnIndexOrThrow(DB.KEY_LASTNAME)));
                i.putExtra(DB.KEY_NAME, cursor.getString(
                        cursor.getColumnIndexOrThrow(DB.KEY_NAME)));
                i.putExtra(DB.KEY_SURNAME, cursor.getString(
                        cursor.getColumnIndexOrThrow(DB.KEY_SURNAME)));
                i.putExtra(DB.KEY_PHONE, cursor.getString(
                        cursor.getColumnIndexOrThrow(DB.KEY_PHONE)));
                i.putExtra(DB.KEY_DATE, cursor.getString(
                        cursor.getColumnIndexOrThrow(DB.KEY_DATE)));
                startActivity(i);
            }
        });
    }

    // обработка нажатия кнопки
    public void onButtonClick(View view) {
        /*// добавляем запись
        db.addRec("sometext " + (cursor.getCount() + 1), R.drawable.ic_launcher_background);
        // обновляем курсор
        cursor.requery();*/
        Intent intent = new Intent(this, AddWorkContact.class);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            // обновляем курсор
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }





    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

}
