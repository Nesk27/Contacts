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
import android.widget.Toast;

public class MainActivity extends Activity {


    private static final int CM_DELETE_ID = 1;
    private static final int CM_DELETE_ID2 = 2;
    ListView lvData, lvData2;
    DB db;
    DB2 db2;

    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    SimpleCursorAdapter scAdapter2;
    Cursor cursor2;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        db2 = new DB2(this);
        db2.open2();


        // получаем курсор
        cursor = db.getAllData();
        startManagingCursor(cursor);

        cursor2 = db2.getAllData2();
        startManagingCursor(cursor2);

        // формируем столбцы сопоставления
        String[] from = new String[] { DB.KEY_PHOTO, DB.KEY_LASTNAME, DB.KEY_NAME, DB.KEY_SURNAME, DB.KEY_PHONE, DB.KEY_DATE };
        int[] to = new int[] { R.id.ivPhoto, R.id.tvLastname, R.id.tvName, R.id.tvSurname, R.id.tvPhone, R.id.tvDate };

        String[] from2 = new String[] { DB2.KEY_PHOTO, DB2.KEY_LASTNAME, DB2.KEY_NAME, DB2.KEY_SURNAME, DB2.KEY_PHONE, DB2.KEY_WORK, DB2.KEY_WORK_PHONE };
        int[] to2 = new int[] { R.id.ivPhoto2, R.id.tvLastname2, R.id.tvName2, R.id.tvSurname2, R.id.tvPhone2, R.id.tvWork, R.id.tvWorkphone };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        scAdapter2 = new SimpleCursorAdapter(this, R.layout.item2, cursor2, from2, to2);
        lvData2 = (ListView) findViewById(R.id.lvData2);
        lvData2.setAdapter(scAdapter2);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvData);

        registerForContextMenu(lvData2);


        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, EditFriends.class);
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

        lvData2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cursor2.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, EditWork.class);
                i.putExtra(DB2.KEY_ID, id);
                i.putExtra(DB2.KEY_PHOTO, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB.KEY_PHOTO)));
                i.putExtra(DB2.KEY_LASTNAME, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB2.KEY_LASTNAME)));
                i.putExtra(DB2.KEY_NAME, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB2.KEY_NAME)));
                i.putExtra(DB2.KEY_SURNAME, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB2.KEY_SURNAME)));
                i.putExtra(DB2.KEY_PHONE, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB2.KEY_PHONE)));
                i.putExtra(DB2.KEY_WORK, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB2.KEY_WORK)));
                i.putExtra(DB2.KEY_WORK_PHONE, cursor2.getString(
                        cursor2.getColumnIndexOrThrow(DB2.KEY_WORK_PHONE)));
                startActivity(i);
            }
        });
    }

    // обработка нажатия кнопки
    public void onButtonClick(View view) {
        // добавляем запись
        //db.addRec("sometext " + (cursor.getCount() + 1), R.drawable.ic_launcher_background);
        // обновляем курсор
        //cursor.requery();
        Intent intent = new Intent(this, AddFriendsContact.class);
        startActivity(intent);
    }

    public void onButtonClick2(View view) {
        /*// добавляем запись
        db.addRec("sometext " + (cursor.getCount() + 1), R.drawable.ic_launcher_background);
        // обновляем курсор
        cursor.requery();*/
        Intent intent = new Intent(this, AddWorkContact.class);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvData) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
        } else if (v.getId() == R.id.lvData2) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(0, CM_DELETE_ID2, 0, R.string.delete_record);
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            Toast toast = Toast.makeText(MainActivity.this, "Контакт удален!", Toast.LENGTH_SHORT);
            toast.show();
            // обновляем курсор
            cursor.requery();
            return true;
        } else if (item.getItemId() == CM_DELETE_ID2) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterContextMenuInfo acmi2 = (AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db2.delRec2(acmi2.id);
            Toast toast = Toast.makeText(MainActivity.this, "Контакт удален!", Toast.LENGTH_SHORT);
            toast.show();
            // обновляем курсор
            cursor2.requery();
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
