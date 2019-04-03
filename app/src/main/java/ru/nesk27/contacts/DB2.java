package ru.nesk27.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB2 {

    private static final String DB_NAME = "ContactDBKol";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "ContactKol";

    public static final String KEY_ID = "_id";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_WORK = "work";
    public static final String KEY_WORK_PHONE = "workphone";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    KEY_ID + " integer primary key autoincrement, " +
                    KEY_PHOTO + " integer, " +
                    KEY_LASTNAME + " text," +
                    KEY_NAME + " text," +
                    KEY_SURNAME + " text," +
                    KEY_PHONE + " text," +
                    KEY_WORK + " text," +
                    KEY_WORK_PHONE + " text" +
                    ");";

    private final Context mCtx2;


    private DB2.DBHelper mDBHelper2;
    private SQLiteDatabase mDB2;

    public DB2(Context ctx) {
        mCtx2 = ctx;
    }

    // открыть подключение
    public void open2() {
        mDBHelper2 = new DB2.DBHelper(mCtx2, DB_NAME, null, DB_VERSION);
        mDB2 = mDBHelper2.getWritableDatabase();
    }

    // закрыть подключение
    public void close2() {
        if (mDBHelper2!=null) mDBHelper2.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData2() {
        return mDB2.query(DB_TABLE, null, null, null, null, null, "name");
    }

    // добавить запись в DB_TABLE
    public void addRec2(String lastname, String name, String surname, String phone, String work, String workphone, int photo) {
        ContentValues cv2 = new ContentValues();
        cv2.put(KEY_LASTNAME, lastname);
        cv2.put(KEY_NAME, name);
        cv2.put(KEY_SURNAME, surname);
        cv2.put(KEY_PHONE, phone);
        cv2.put(KEY_PHOTO, photo);
        cv2.put(KEY_WORK, work);
        cv2.put(KEY_WORK_PHONE, workphone);
        mDB2.insert(DB_TABLE, null, cv2);
    }

    // удалить запись из DB_TABLE
    public void delRec2(long id) {
        mDB2.delete(DB_TABLE, KEY_ID + " = " + id, null);
    }


    public void updateDB2(String lastname, String name, String surname, String phone, String work, String workphone, long id)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_LASTNAME, lastname);
        args.put(KEY_NAME, name);
        args.put(KEY_SURNAME, surname);
        args.put(KEY_PHONE, phone);
        args.put(KEY_WORK, work);
        args.put(KEY_WORK_PHONE, workphone);

        mDB2.update(DB_TABLE, args, KEY_ID + "=" + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db2) {
            db2.execSQL(DB_CREATE);

            //Сразу добавляем тестовые данные
            ContentValues cv2 = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv2.put(KEY_LASTNAME, "Фамилия" + i);
                cv2.put(KEY_NAME, "Имя" + i);
                cv2.put(KEY_SURNAME, "Отчество" + i);
                cv2.put(KEY_PHONE, "8-800-555-35-3" + i);
                cv2.put(KEY_PHOTO, R.drawable.ic_account_box_black_36dp);
                cv2.put(KEY_WORK, "Должность" + i);
                cv2.put(KEY_WORK_PHONE, "271-23-2" + i);
                db2.insert(DB_TABLE, null, cv2);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {
        }

    }
}

