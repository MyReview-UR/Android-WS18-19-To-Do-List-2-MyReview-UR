package de.ur.mi.android.todolist;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDatabase {
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "todolistitems";

    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DATE = "date";

    public static final int COLUMN_TASK_INDEX = 1;
    public static final int COLUMN_DATE_INDEX = 2;

    private ToDoDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public ToDoListDatabase(Context context) {
        dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public long insertToDoItem(ToDoItem item) {
        ContentValues newToDoValues = new ContentValues();

        newToDoValues.put(KEY_TASK, item.getName());
        newToDoValues.put(KEY_DATE, item.getFormattedDate());

        return db.insert(DATABASE_TABLE, null, newToDoValues);
    }

    public void removeToDoItem(ToDoItem item) {
        String whereClause = KEY_TASK + " = '" + item.getName() + "' AND "
                + KEY_DATE + " = '" + item.getFormattedDate() + "'";

        db.delete(DATABASE_TABLE, whereClause, null);
    }

    public ArrayList<ToDoItem> getAllToDoItems() {
        ArrayList<ToDoItem> items = new ArrayList<ToDoItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_TASK, KEY_DATE }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(COLUMN_TASK_INDEX);
                String date = cursor.getString(COLUMN_DATE_INDEX);

                Date formatedDate = null;
                try {
                    formatedDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance(Locale.GERMAN);
                cal.setTime(formatedDate);

                items.add(new ToDoItem(task, cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));

            } while (cursor.moveToNext());
        }
        return items;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text not null, " + KEY_DATE + " text);";

        public ToDoDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
