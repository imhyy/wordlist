package com.example.hyy.wordlist;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import  android.app.FragmentManager;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hyy.wordlist.dummy.DummyContent;
import com.example.hyy.wordlist.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

public class MainActivity extends Activity implements ItemFragment.OnListFragmentInteractionListener,fragment_detail.OnFragmentInteractionListener{
    public worddbhelper dbHelper;
    public Words w;
         private TextView adds;
  public  Context context ;
   public FragmentTransaction transaction;
    ItemFragment fragment1;
    ItemFragment fragment2;
    ItemFragment fragment3;
    ItemFragment fragment4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new worddbhelper(this);
        w=new Words(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adds=this.findViewById(R.id.l);
       this.registerForContextMenu(adds);
       context = getApplicationContext();
    fragment1=new ItemFragment();
        transaction = getFragmentManager().beginTransaction();
       transaction.add(R.id.wordlsit,fragment1);
        transaction.show(fragment1);
        //transaction.replace(R.id.wordlsit, fragment1);
        transaction.commit();
    }

    @Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id=item.getItemId();

        switch (id)
        {
            case R.id.search:
                Toast.makeText(context, "查询", Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete:
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
    @Override*/
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "添加");
        menu.add(0, 1, 0, "修改");
        menu.add(0, 2, 0, "删除");
        menu.add(0, 3, 0, "查询");
        // 将三个菜单项设为单选菜单项
    }



    @Override
    public boolean onContextItemSelected(MenuItem mi){
        switch (mi.getItemId())
        {
            case 0:
                addword();
                break;
            case 1:
                changeword();
            break;
            case 2:
                deleteword();
            break;
            case 3:
                searchword();
            break;
            default:


        }
        return super.onContextItemSelected(mi);
    }
    /*<fragment
    android:name="com.example.hyy.wordlist.ItemFragment"
    android:id="@+id/wordlists"
    android:layout_weight="1.21"
    android:layout_width="80dp"
    android:layout_height="match_parent"
            />
            */

    void addword(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_add_,null);
        builder.setView(view);
       builder.create();
        final EditText word=(EditText)view.findViewById(R.id.words1);
        final EditText example=(EditText)view.findViewById(R.id.examples1);
        final EditText meaning=(EditText)view.findViewById(R.id.meaings1);
        final Button btn_submit=(Button)view.findViewById(R.id.btn_submit1);

       btn_submit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
           if(!w.isexist(word.getText().toString())) {
               w.addword(word.getText().toString(), meaning.getText().toString(), example.getText().toString());
               Toast.makeText(context, "add succeeded", Toast.LENGTH_SHORT).show();
              fragment2=new ItemFragment();
               getFragmentManager().beginTransaction().hide(fragment1);
               getFragmentManager().beginTransaction().hide(fragment2);
               getFragmentManager().beginTransaction().hide(fragment3);
               getFragmentManager().beginTransaction().hide(fragment4);
              getFragmentManager().beginTransaction().replace(R.id.wordlsit,fragment2).commit();

           }
                else {
               Toast.makeText(context, "word is already exist", Toast.LENGTH_SHORT).show();
           }

                }
        });
        builder.show();
    }
    void deleteword(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_deletefragment, null);
        builder.setView(view);
        builder.create();
        final EditText word=(EditText)view.findViewById(R.id.words3);
        final Button btn_submit=(Button)view.findViewById(R.id.btn_submit3);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                if(w.isexist(word.getText().toString())) {
                    w.deleteword(word.getText().toString());
                    Toast.makeText(context, "delete succeeded", Toast.LENGTH_SHORT).show();
                    fragment3=new ItemFragment();
                    getFragmentManager().beginTransaction().hide(fragment1);
                    getFragmentManager().beginTransaction().hide(fragment2);
                    getFragmentManager().beginTransaction().hide(fragment3);
                    getFragmentManager().beginTransaction().hide(fragment4);
                    getFragmentManager().beginTransaction().replace(R.id.wordlsit,fragment3).commit();

                }
                else {
                    Toast.makeText(context, "word is not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.show();
    }
    void changeword(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_changefragment, null);
        builder.setView(view);
       builder.create();
        final EditText word=(EditText)view.findViewById(R.id.words2);
        final EditText word5=(EditText)view.findViewById(R.id.words5);
        final EditText example=(EditText)view.findViewById(R.id.examples2);
        final EditText meaning=(EditText)view.findViewById(R.id.meaings2);
        final Button btn_submit=(Button)view.findViewById(R.id.btn_submit2);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                if(w.isexist(word.getText().toString())) {
                    w.changeword(word.getText().toString(),word5.getText().toString(),meaning.getText().toString(),example.getText().toString());
                    Toast.makeText(context, "change succeeded", Toast.LENGTH_SHORT).show();
                    fragment4=new ItemFragment();
                    getFragmentManager().beginTransaction().hide(fragment1);
                    getFragmentManager().beginTransaction().hide(fragment2);
                    getFragmentManager().beginTransaction().hide(fragment3);
                    getFragmentManager().beginTransaction().hide(fragment4);
                    getFragmentManager().beginTransaction().replace(R.id.wordlsit,fragment4).commit();
                }
                else {
                    Toast.makeText(context, "word is not exist", Toast.LENGTH_SHORT).show();
                }


            }
        });
        builder.show();
    }


    void searchword(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_searchfragment, null);
        builder.setView(view);
        builder.create();
        final EditText word=(EditText)view.findViewById(R.id.words4);
        final EditText word2=(EditText)view.findViewById(R.id.words6);
        final Button btn_submit=(Button)view.findViewById(R.id.btn_submit4);
        final Button btn_submit2=(Button)view.findViewById(R.id.btn_submit5);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                if(w.isexist(word.getText().toString())) {
                    String words1=word.getText().toString();
                    String [] s=new String[2];
                   s=w.searchword(word.getText().toString());
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    View view1= LayoutInflater.from(MainActivity.this).inflate(R.layout.fuzzysearch, null);
                    TextView word3=(TextView)view1.findViewById(R.id.textword1);
                    TextView meaning3=(TextView)view1.findViewById(R.id.textmeaning1);
                    TextView sample3=(TextView)view1.findViewById(R.id.textsample1);
                    word3.setText(words1);
                    meaning3.setText(s[0]);
                    sample3.setText(s[1]);
                    builder1.setView(view1);
                    builder1.show();
                }
                else {
                    String [] s=new String[3];
                  s=w.youdaosearch(word.getText().toString());
                  w.addword(s[0],s[1],s[2]);
                  final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    View view1= LayoutInflater.from(MainActivity.this).inflate(R.layout.fuzzysearch, null);
                    TextView word3=(TextView)view1.findViewById(R.id.textword1);
                    TextView meaning3=(TextView)view1.findViewById(R.id.textmeaning1);
                    TextView sample3=(TextView)view1.findViewById(R.id.textsample1);
                    word3.setText(s[0]);
                    meaning3.setText(s[1]);
                    sample3.setText(s[2]);
                    builder1.setView(view1);
                    builder1.show();
                    fragment2=new ItemFragment();
                    getFragmentManager().beginTransaction().hide(fragment1);
                    getFragmentManager().beginTransaction().hide(fragment2);
                    getFragmentManager().beginTransaction().hide(fragment3);
                    getFragmentManager().beginTransaction().hide(fragment4);
                    getFragmentManager().beginTransaction().replace(R.id.wordlsit,fragment2).commit();


                }

            }
        });
        btn_submit2.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                String words1="";
                String meaning1="";
                String sample1="";
                ArrayList<String> s=new ArrayList<>();
                s=w.Fuzzy_search(word2.getText().toString());
                int i=0;
              while(i<s.size()){
                    words1=words1+s.get(i)+"\n";
                    meaning1=meaning1+s.get(i+1)+"\n";
                    sample1=sample1+s.get(i+2)+"\n";
                    i=i+3;
                }

               // Toast.makeText(context,s.get(1), Toast.LENGTH_SHORT).show();
              final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                View view1= LayoutInflater.from(MainActivity.this).inflate(R.layout.fuzzysearch, null);
                TextView word3=(TextView)view1.findViewById(R.id.textword1);
                TextView meaning3=(TextView)view1.findViewById(R.id.textmeaning1);
                TextView sample3=(TextView)view1.findViewById(R.id.textsample1);
                word3.setText(words1);
                meaning3.setText(meaning1);
                sample3.setText(sample1);
                builder1.setView(view1);
                builder1.show();

            }
        });
        builder.show();
    }
    public void onFragmentInteraction(String id){
        Bundle arguments=new Bundle();
            arguments.putString("id",id);
            fragment_detail fragment=new fragment_detail();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().replace(R.id.worddetail,fragment).commit();

    }


    public void onListFragmentInteraction(DummyContent.DummyItem item){
        onFragmentInteraction(item.content);
    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
