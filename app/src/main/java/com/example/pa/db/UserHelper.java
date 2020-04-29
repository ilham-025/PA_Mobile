package com.example.pa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pa.Model.User;

import java.util.ArrayList;

import static com.example.pa.db.DatabaseContract.UserColumns.EMAIL;
import static com.example.pa.db.DatabaseContract.UserColumns.NAMA;
import static com.example.pa.db.DatabaseContract.UserColumns.PASSWORD;
import static com.example.pa.db.DatabaseContract.UserColumns.ROLE;
import static com.example.pa.db.DatabaseContract.UserColumns.USER_ID;

public class UserHelper {
    private static DatabaseHelper databaseHelper;
    private static UserHelper INSTANCE;
    private static SQLiteDatabase database;

    private UserHelper(Context context){
        databaseHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public static synchronized UserHelper getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new UserHelper(context);
        }
        return INSTANCE;
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if(database.isOpen()){
            database.close();
        }
    }


    public User all(){
        ArrayList<User> arrayList =  new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ DatabaseContract.TABLE_USER,null);
        cursor.moveToFirst();
        Log.d("panjang curosr",String.valueOf(cursor.getCount()));
        User user = new User();
        if(cursor.getCount()>0){
            do{
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD)));
                user.setNama(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(ROLE)));
//                arrayList.add(user);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return user;
    }

//    public Cursor queryAll(){
//        Cursor cursor = database.rawQuery("SELECT * FROM "+ DatabaseContract.TABLE_FAVOURITE_MOVIE_NAME,null);
//        return cursor;
//    }

    public long insert(User user){
        ContentValues arg = new ContentValues();
        long result = 0;
        arg.put(USER_ID,user.getId());
        arg.put(EMAIL,user.getEmail());
        arg.put(PASSWORD,user.getPassword());
        arg.put(NAMA,user.getNama());
        arg.put(ROLE,user.getRole());
        result = database.insert(DatabaseContract.TABLE_USER, null, arg);
        return result;
    }

    public Boolean checkIfExists(){
        Boolean exits = false;
        Cursor cursor = database.rawQuery("SELECT * FROM "+ DatabaseContract.TABLE_USER,null);

        if(cursor.getCount()>0){
            exits = true;
        }
        return  exits;
    }

    public int delete(int userID){
        return database.delete(DatabaseContract.TABLE_USER,USER_ID+"='"+userID+"'",null);
    }
}
