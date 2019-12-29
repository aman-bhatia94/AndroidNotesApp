package com.aman.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "todolist"; //database name
    private static final int DB_VERSION = 2; //version of the database
    private SQLiteDatabase database;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create three tables for three lists
        db.execSQL("CREATE TABLE list1(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " noteDesc TEXT," +
                " noteStatus TEXT);");

        db.execSQL("CREATE TABLE list2(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " noteDesc TEXT," +
                " noteStatus TEXT);");

        db.execSQL("CREATE TABLE list3(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " noteDesc TEXT," +
                " noteStatus TEXT);");

        //database = db;


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE list1");
        db.execSQL("DROP TABLE list2");
        db.execSQL("DROP TABLE list3");
        //create three tables for three lists
        db.execSQL("CREATE TABLE list1(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " noteDesc TEXT," +
                " noteStatus TEXT);");

        db.execSQL("CREATE TABLE list2(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " noteDesc TEXT," +
                " noteStatus TEXT);");

        db.execSQL("CREATE TABLE list3(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " noteDesc TEXT," +
                " noteStatus TEXT);");

        //database = db;

    }

    public void insertIntoList(String listName, ContentValues contentValues){
        database = this.getWritableDatabase();
        database.insert(listName,null,contentValues);
    }

    public void updateList(String listName, ContentValues contentValues, int noteId){
        database = this.getWritableDatabase();
        database.update(listName,contentValues,"_id = ?",new String[]{Integer.toString(noteId)});
    }

    public void deleteList(String listName, int id){
        database = this.getWritableDatabase();
        database.delete(listName,"_id = ?",new String[]{Integer.toString(id)});
    }

    public Cursor getCursor(String listName){
        database = this.getReadableDatabase();
        Cursor cursor = database.query(listName,new String[]{"_id","noteDesc"},null,null,null,null,null);
        return cursor;
    }

    public String getStatus(String listName, int id){
        database = this.getReadableDatabase();
        Cursor cursor = database.query(listName,new String[]{"noteStatus"},"_id = ?",new String[]{Integer.toString(id)},null,null,null);
        String status = null;
        if(cursor.moveToFirst()){
            status = cursor.getString(0);
        }

        return status;
    }

    public Cursor sort(String listName){
        database = this.getReadableDatabase();
        Cursor cursor = database.query(listName,new String[]{"_id","noteDesc"},null,null,null,null,"_id ASC");
        return cursor;
    }

    public Cursor sortAlpha(String listName){
        database = this.getReadableDatabase();
        Cursor cursor = database.query(listName,new String[]{"_id","noteDesc"},null,null,null,null,"noteDesc ASC");
        return cursor;
    }

    public Cursor searchWords(String listName, String regex) {

        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT _id,noteDesc FROM "+listName+" WHERE noteDesc LIKE '"+regex+"%';",null);
        return cursor;
    }
}
