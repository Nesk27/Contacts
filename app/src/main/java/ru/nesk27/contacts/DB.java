package ru.nesk27.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {
    private static final String DB_NAME = "ContactDB3";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "ContactFriends";

    public static final String KEY_ID = "_id";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_DATE = "date";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    KEY_ID + " integer primary key autoincrement, " +
                    KEY_PHOTO + " integer, " +
                    KEY_LASTNAME + " text," +
                    KEY_NAME + " text," +
                    KEY_SURNAME + " text," +
                    KEY_PHONE + " text," +
                    KEY_DATE + " text" +
                    ");";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String lastname, String name, String surname, String phone, String date, int photo) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_LASTNAME, lastname);
        cv.put(KEY_NAME, name);
        cv.put(KEY_SURNAME, surname);
        cv.put(KEY_PHONE, phone);
        cv.put(KEY_PHOTO, photo);
        cv.put(KEY_DATE, date);
        mDB.insert(DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, KEY_ID + " = " + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

           /* ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.put(COLUMN_TXT, "sometext " + i);
                cv.put(COLUMN_IMG, R.drawable.ic_launcher_background);
                db.insert(DB_TABLE, null, cv);
            }*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
