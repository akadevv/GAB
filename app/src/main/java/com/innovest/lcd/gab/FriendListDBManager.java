package com.innovest.lcd.gab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.innovest.lcd.gab.Friend_list.FriendData;

import java.util.ArrayList;

/**
 * Created by LCD on 2016-02-03.
 */
public class FriendListDBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GAB";
    private static final String TABLE_NAME = "Friend_List_Main";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_BOOK_MARK = "book_mark";

    public FriendListDBManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID +" INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_BOOK_MARK + " INTEGER" + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }

    public void addFriendData(FriendData friendData){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friendData.getName());
        values.put(KEY_PH_NO, friendData.getPhoneNum());
        values.put(KEY_BOOK_MARK, friendData.getBookMark());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<FriendData> getAllFriendData(){
        ArrayList<FriendData> friendDataList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_NAME + " ASC" + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                FriendData friendData = new FriendData();
                friendData.setId(cursor.getInt(0));
                friendData.setName(cursor.getString(1));
                friendData.setPhoneNum(cursor.getString(2));
                friendData.setBookMark(cursor.getInt(3));
                friendDataList.add(friendData);
            }while (cursor.moveToNext());
        }
        db.close();
        return friendDataList;
    }

    public void updateFriendData(FriendData friendData){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, friendData.getName());
        values.put(KEY_PH_NO, friendData.getPhoneNum());
        values.put(KEY_BOOK_MARK, friendData.getBookMark());

        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{Integer.toString(friendData.getId())});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE " + TABLE_NAME + ";";
        db.execSQL(query);
        db.close();
    }
}
