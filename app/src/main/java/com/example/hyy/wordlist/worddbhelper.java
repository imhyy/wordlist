package com.example.hyy.wordlist;
import android.content.Context;
import android.database.sqlite.*;
import android.widget.Toast;

public class worddbhelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "word.db";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;
    //private final static String SQL_CREATE_DATABASE = "CREATE DATABASE" + DATABASE_NAME;
    public final static String SQL_CREATE_TABLE = "CREATE TABLE " + Words.Word.TABLE_NAME + "("
            + Words.Word.COLUMN_NAME_WORD + " TEXT PRIMARY KEY,"
            + Words.Word.COLUMN_NAME_MEANING + " TEXT,"
            + Words.Word.COLUMN_NAME_SAMPLE + " TEXT"
            + ")";
     private Context mContext;
    public worddbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }

    public static void main(String[] args) {
        System.out.print(SQL_CREATE_TABLE);
    }
}
