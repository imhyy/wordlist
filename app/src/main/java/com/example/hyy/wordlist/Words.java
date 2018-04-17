package com.example.hyy.wordlist;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by hyy on 2018/4/12.
 */

public class Words {
    public static worddbhelper dbHelper;
    private SQLiteDatabase sqliteDatabase;// 创建SQLiteDatabase对象
    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME="words";
        public static final String COLUMN_NAME_WORD="word";//列：单词
        public static final String COLUMN_NAME_MEANING="meaning";//列：单词含义
        public static final String COLUMN_NAME_SAMPLE="sample";//单词示例
    }
    public void addword(String word,String meaning,String sample){
      dbHelper.getWritableDatabase();
        String sql = "insert into words(word,meaning,sample) values (?,?,?)";
        Object bindArgs[] = new Object[] { word, meaning,sample };
        sqliteDatabase.execSQL(sql, bindArgs);
    }
    public void deleteword(){}
    public void changeword(){}
    public void searchword(){}



}
