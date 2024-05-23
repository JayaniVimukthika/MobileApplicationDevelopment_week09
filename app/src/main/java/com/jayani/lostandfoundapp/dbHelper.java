package com.jayani.lostandfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class dbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "Task_data1";
    //Database Table name
    private static final String TABLE_NAME = "Tasks";
    //Table columns
    public static final String ID = "id";

    public static final String POSTTYPE = "postType";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String DESCRIPTION = "description";

    public static final String DATE = "date";

    public static final String LOCATION = "location";
    public static final String LOCATIONLAT = "locationlat";
    public static final String LOCATIONLONG = "locationlong";

    private SQLiteDatabase sqLiteDatabase;


    //creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT," + POSTTYPE + " INTEGER NOT NULL,"+NAME+" TEXT NOT NULL,"+PHONE+" TEXT NOT NULL,"+DESCRIPTION+" TEXT NOT NULL,"+DATE+" TEXT NOT NULL,"+LOCATION+" TEXT NOT NULL,"+LOCATIONLAT+" TEXT,"+LOCATIONLONG+" TEXT);";
    //Constructor
    public dbHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItem(itemModel itemModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.POSTTYPE, itemModel.getPostType());
        contentValues.put(dbHelper.NAME, itemModel.getName());
        contentValues.put(dbHelper.PHONE, itemModel.getPhone());

        contentValues.put(dbHelper.DESCRIPTION, itemModel.getDescription());
        contentValues.put(dbHelper.DATE, itemModel.getDate());
        contentValues.put(dbHelper.LOCATION, itemModel.getLocation());
        contentValues.put(dbHelper.LOCATIONLAT, itemModel.getlocationlat());
        contentValues.put(dbHelper.LOCATIONLONG, itemModel.getlocationlong());


        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(dbHelper.TABLE_NAME, null,contentValues);
    }

    public ArrayList<itemModel> getItemList(){
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        ArrayList<itemModel> storeTasks = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                int postType = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                String phone = cursor.getString(3);
                String description = cursor.getString(4);
                String date = cursor.getString(5);
                String location = cursor.getString(6);
                String locationlat = cursor.getString(7);
                String locationlong = cursor.getString(8);
                storeTasks.add(new itemModel(id, postType,name, phone, description, date, location,locationlat,locationlong));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeTasks;
    }


    public void deleteTask(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + " = ? ", new String[]
                {String.valueOf(id)});

        sqLiteDatabase.close();
    }
}
