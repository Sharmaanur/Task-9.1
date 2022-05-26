package com.suman.lostfound.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "lfDB";
    private static final String TABLE = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_TYPE = "type";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT ,"
                + KEY_PHONE + " TEXT ,"
                + KEY_DESCRIPTION + " TEXT ,"
                + KEY_DATE + " TEXT ,"
                + KEY_LOCATION + " TEXT,"
                + KEY_TYPE + " INTEGER"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addItem(ItemModel itemModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, itemModel.getName());
        values.put(KEY_PHONE, itemModel.getPhone());
        values.put(KEY_DESCRIPTION, itemModel.getDescription());
        values.put(KEY_DATE, itemModel.getDate());
        values.put(KEY_LOCATION, itemModel.getLocation());
        values.put(KEY_TYPE, itemModel.getType());

        // Inserting Row

        String selectQuery = "SELECT * FROM " + TABLE + " WHERE " +KEY_NAME+ " = '"+ itemModel.getName()+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() < 1) {
            db.insert(TABLE, null, values);
        }
        //2nd argument is String containing nullColumnHack
        //db.close(); // Closing database connection
    }

    public List<ItemModel> getAllItems() {
        List<ItemModel> contactList = new ArrayList<ItemModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE + " ORDER BY"+ " id" + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemModel itemModel = new ItemModel();
                itemModel.setId(cursor.getString(0));
                itemModel.setName(cursor.getString(1));
                itemModel.setPhone(cursor.getString(2));
                itemModel.setDescription(cursor.getString(3));
                itemModel.setDate(cursor.getString(4));
                itemModel.setLocation(cursor.getString(5));
                itemModel.setType(cursor.getInt(6));
                // Adding contact to list
                contactList.add(itemModel);
            } while (cursor.moveToNext());
        }

        // return list
        return contactList;
    }


    public void deleteClient(ItemModel itemModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(itemModel.getId()) });
        db.close();
    }
}
