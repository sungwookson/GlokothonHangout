package com.glocoders.hangout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsm2016 on 2017-07-23.
 */

public class UserInfoHelper extends SQLiteOpenHelper{
    String LOGIN_TABLE = "LOGIN_TABLE";
    String USER_TABLE = "USER_TABLE";

    public UserInfoHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // is_auto == 1 > auto , is_auto == 0 -> not auto
        db.execSQL("CREATE TABLE " + LOGIN_TABLE + "(" +
                   "email TEXT NOT NULL, " +
                   "passwd TEXT NOT NULL, " +
                   "is_auto INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + USER_TABLE + "(" +
                   "uid TEXT NOT NULL, " +
                   "detail TEXT NOT NULL, " +
                   "real_name TEXT NOT NULL, " +
                   "nick_name TEXT NOT NULL, " +
                   "profile_image TEXT NOT NULL," +
                   "age INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void loginSetting(String email, String passwd, int is_auto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("is_auto", is_auto);

        if (getCount(LOGIN_TABLE) == 1)
            db.update(LOGIN_TABLE, values, "email=? and passwd=?", new String[]{email, passwd});

        else {
            values.put("email", email);
            values.put("passwd", passwd);
            db.insert(LOGIN_TABLE, null, values);
        }
        db.close();
    }

    public List<String> readLogin() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(LOGIN_TABLE, null, null, null, null, null, null);
        List<String> login_data = new ArrayList<String>();
        c.moveToNext();

        try {
            login_data.add(c.getString(c.getColumnIndex("email")));
            login_data.add(c.getString(c.getColumnIndex("passwd")));
            login_data.add(c.getString(c.getColumnIndex("is_auto")));
        } catch (Exception e) {
            login_data.add(e.toString());
        }
        return login_data;
    }

    public int getCount(String tablename) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Count(*) FROM " + tablename, null);
        c.moveToNext();
        return c.getInt(0);

    }

}
