package com.example.hyy.wordlist;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by hyy on 2018/4/12.
 */

public class Words {
    private static ArrayList<String> wordlist;
    private static ArrayList<String> meaninglist;
    private static ArrayList<String> samplelist;
   private worddbhelper dbHelper;
    private static  SQLiteDatabase sqliteDatabase;// 创建SQLiteDatabase对象
    private Context mContext;
    public Words(Context context){
        dbHelper=new worddbhelper(context);
        this.sqliteDatabase=this.dbHelper.getWritableDatabase();
        mContext=context;
    }
    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME="words";
        public static final String COLUMN_NAME_WORD="word";//列：单词
        public static final String COLUMN_NAME_MEANING="meaning";//列：单词含义
        public static final String COLUMN_NAME_SAMPLE="sample";//单词示例
    }
    public static ArrayList<String> getword(){
        wordlist=new ArrayList<String>();
        Cursor cursor = sqliteDatabase.rawQuery("select word from words", null);
        while (cursor.moveToNext()) {
            wordlist.add(cursor.getString(0));
        }
        return wordlist;
    }
    public static ArrayList<String> getmeaning(){
        meaninglist=new ArrayList<String>();
        Cursor cursor = sqliteDatabase.rawQuery("select meaning from words", null);
        while (cursor.moveToNext()) {
            meaninglist.add(cursor.getString(0));
        }
        return  meaninglist;
    }
    public static ArrayList<String> getsample(){
        samplelist=new ArrayList<String>();
        Cursor cursor = sqliteDatabase.rawQuery("select sample from words", null);
        while (cursor.moveToNext()) {
            samplelist.add(cursor.getString(0));
        }
        return  meaninglist;
    }

    public void addword(String word,String meaning,String sample){

        String sql = "insert into words(word,meaning,sample) values (?,?,?)";

       Object bindArgs[] = new Object[] { word,meaning,sample };
        sqliteDatabase.execSQL(sql,bindArgs);
    }
    public void deleteword(String word){
        String sql = "delete from words where word=?";
        Object bindArgs[] = new Object[] { word };
        sqliteDatabase.execSQL(sql,bindArgs);
    }
    public void changeword(String word,String word5,String meaning,String sample){
        if(!meaning.equals("")){
            String sql = "update words set meaning=? where word=?";
            Object bindArgs[] = new Object[] { meaning,word };
            sqliteDatabase.execSQL(sql,bindArgs);
        }
        if(!sample.equals("")){
            String sql = "update words set sample=? where word=?";
            Object bindArgs[] = new Object[] { sample,word };
            sqliteDatabase.execSQL(sql,bindArgs);
        }

        if(!word5.equals("")){
                String sql = "update words set word=? where word=?";
                Object bindArgs[] = new Object[] { word5,word };
                sqliteDatabase.execSQL(sql,bindArgs);
            }

    }
    public void searchword(String word){

    }
    public  static long count(){
        String sql = "select count(*) from words";
        Cursor cursor = sqliteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        return count;
    }
    public boolean isexist(String word){
        Cursor cursor = sqliteDatabase.rawQuery("select word from words", null);
        int p=0;
        while (cursor.moveToNext()) {

            String s = cursor.getString(0);//获取第二列的值
          if(s.equals(word))
          {
              p=1;
          }
        }
        if(p==1){
        return true;}
        else {
            return false;
        }
    }


}
