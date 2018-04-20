package com.example.hyy.wordlist;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
        Cursor cursor = sqliteDatabase.rawQuery("select * from words", null);
        while (cursor.moveToNext()) {
            wordlist.add(cursor.getString(0));
        }
        return wordlist;
    }
    public static ArrayList<String> getmeaning(){
        meaninglist=new ArrayList<String>();
        Cursor cursor = sqliteDatabase.rawQuery("select * from words", null);
        while (cursor.moveToNext()) {
            meaninglist.add(cursor.getString(1));
        }
        return  meaninglist;
    }
    public static ArrayList<String> getsample(){
        samplelist=new ArrayList<String>();
        Cursor cursor = sqliteDatabase.rawQuery("select * from words", null);
        while (cursor.moveToNext()) {
            samplelist.add(cursor.getString(2));
        }
        return  samplelist;
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
    public String[] searchword(String word){
        String [] s=new String[2];

        String sql = "select * from words where word ='"+word+"'";
        Cursor cursor = sqliteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext()) {
         s[0]=cursor.getString(1);
         s[1]=cursor.getString(2);
        }
        return s;
    }
    public String[] youdaosearch(String word){
        String[] meaning_example=new String[3];
        wllj wlljj=new wllj(word);
        wlljj.start();
        try
        {
            Thread.currentThread().sleep(500);//毫秒
        }
        catch(Exception e){}
        meaning_example[0]=word;
        meaning_example[1]=wlljj.getmeaning();
        meaning_example[2]=wlljj.getsample();

        return  meaning_example;
    }



    public static ArrayList<String> Fuzzy_search(String word){
        ArrayList<String> meaning_example=new ArrayList<String>();
        String sql = "select * from words where word like '"+word+"%'";
        Cursor cursor = sqliteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            meaning_example.add(cursor.getString(0));
            meaning_example.add(cursor.getString(1));
            meaning_example.add(cursor.getString(2));
        }

        return  meaning_example;
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
    class wllj extends Thread
    {
        String word;
        String wordmeaning;
        String wordsample;
        wllj(String word)
        {
            this.word=word;
        }
        public String getword(){
            return word;
        }
        public  String getmeaning(){
            return wordmeaning;
        }
        public  String getsample(){
            return  wordsample;
        }
        public void run()
        {
            Map<String, String> params = new HashMap<String, String>();
            String appKey ="4d9128f7cf104b21";
            String query = word;
            String salt = String.valueOf(System.currentTimeMillis());
            String from = "EN";
            String to ="zh-CHS" ;
            String sign = md5(appKey + query + salt+ "0AsD7OrpfGh06elsBWki55xF5NV3bqkR");
            params.put("q", query);
            params.put("from", from);
            params.put("to", to);
            params.put("sign", sign);
            params.put("salt", salt);
            params.put("appKey", appKey);
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);
                String sendUrl = getUrlWithQueryString("http://openapi.youdao.com/api", params);
                System.out.println(sendUrl);
                URL uri =new URL("http://dict-co.iciba.com/api/dictionary.php?w="+word+"&key=27ED27166918C664C90438B501855561"); // 创建URL对象
                HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
                if (conn instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
                }

                conn.setConnectTimeout(10000); // 设置相应超时
                conn.setRequestMethod("GET");
                int statusCode = conn.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    System.out.println("Http错误码：" + statusCode);
                }

                // 读取服务器的数据
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                String text = builder.toString();

                Log.i("123",text);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputStream iStream=new ByteArrayInputStream(text.getBytes());
                Document document = db.parse(iStream);  ;

                NodeList acceptation=document.getElementsByTagName("acceptation");
                NodeList orig=document.getElementsByTagName("orig");
                wordmeaning=acceptation.item(0).getFirstChild().getNodeValue();
                wordsample=orig.item(0).getFirstChild().getNodeValue();
               /* Log.i("123",wordmeaning);
                Log.i("123",wordsample);
                f=1;
                settext settext=new settext();
                settext.start();*/
                //JSONObject json= new JSONObject(text);
                //String translation=json.getString("translation");
                //JSONArray jArray=new JSONArray(translation);
                //Log.i("123",jArray.getString(0));
                //isampletext.setText(""+wordmeaning+"");
                br.close(); // 关闭数据流
                is.close(); // 关闭数据流
                conn.disconnect(); // 断开连接


            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (java.io.IOException e)
            {

            }
            catch(java.security.KeyManagementException e)
            {

            }
            /*catch(org.json.JSONException e)
            {
            }*/
            catch (javax.xml.parsers.ParserConfigurationException e)
            {

            }
            catch(org.xml.sax.SAXException e)
            {

            }

        }
    }
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }
    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };
}


