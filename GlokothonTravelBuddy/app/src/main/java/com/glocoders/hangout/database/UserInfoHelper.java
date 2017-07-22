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
    String TABLENAME = "USER_INFO";

    public UserInfoHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "(" +
                    "uid TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "passwd TEXT NOT NULL, " +
                    "detail TEXT NOT NULL, " +
                    "real_name TEXT NOT NULL, " +
                    "nick_name TEXT NOT NULL, " +
                    "profile_image TEXT NOT NULL," +
                    "age INTEGER NOT NULL," +
                    "is_auto INTEGER NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public void insert(String uid, String email, String passwd, String detail, String real_name, String nick_name,
                       String profile_image, int age, int is_auto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("uid", uid);
        values.put("email", email);
        values.put("passwd", passwd);
        values.put("detail", detail);
        values.put("real_name", real_name);
        values.put("nick_name", nick_name);
        values.put("profile_image", profile_image);
        values.put("age", age);
        values.put("is_auto", is_auto);

        db.insert(TABLENAME, null, values);
        db.close();
    }

    public void update (String before_passwd, String passwd, String detail, String profile_image, String nick_name, int age, int is_auto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("passwd", passwd);
        values.put("detail", detail);
        values.put("nick_name", nick_name);
        values.put("profile_image", profile_image);
        values.put("age", age);
        values.put("is_auto", is_auto);

        db.update(TABLENAME, values, "passwd=?", new String[]{before_passwd});
        db.close();
    }

    public List<String> select() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLENAME, null, null, null, null, null, null, null);
        List<String> user_info = new ArrayList<String>();

        try {
            user_info.add(c.getString(c.getColumnIndex("uid")));
            user_info.add(c.getString(c.getColumnIndex("email")));
            user_info.add(c.getString(c.getColumnIndex("passwd")));
            user_info.add(c.getString(c.getColumnIndex("detail")));
            user_info.add(c.getString(c.getColumnIndex("real_name")));
            user_info.add(c.getString(c.getColumnIndex("nick_name")));
            user_info.add(c.getString(c.getColumnIndex("profile_image")));
            user_info.add(Integer.toString(c.getInt(c.getColumnIndex("age"))));
            user_info.add(Integer.toString(c.getInt(c.getColumnIndex("is_auto"))));
        } catch (Exception e) {
            user_info.add("error");
        }
        return user_info;
    }
}
