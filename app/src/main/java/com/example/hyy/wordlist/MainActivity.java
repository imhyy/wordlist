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

public class MainActivity extends Activity implements ItemFragment.OnListFragmentInteractionListener,fragment_detail.OnFragmentInteractionListener{
    public worddbhelper dbHelper;
    public Words w;
         private TextView adds;
  public  Context context ;
   public FragmentTransaction transaction;
    ItemFragment fragment1;
    ItemFragment fragment2;
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
        final EditText example=(EditText)view.findViewById(R.id.examples2);
        final EditText meaning=(EditText)view.findViewById(R.id.meaings2);
        final Button btn_submit=(Button)view.findViewById(R.id.btn_submit2);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                if(w.isexist(word.getText().toString())) {
                    w.changeword(word.getText().toString(),meaning.getText().toString(),example.getText().toString());
                    Toast.makeText(context, "change succeeded", Toast.LENGTH_SHORT).show();
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
        final Button btn_submit=(Button)view.findViewById(R.id.btn_submit4);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view) {
                w.searchword(word.getText().toString());
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


       public void refresh() {
           finish();
           Intent intent = new Intent(MainActivity.this, MainActivity.class);
           startActivity(intent);
       }

    public void onListFragmentInteraction(DummyContent.DummyItem item){
        onFragmentInteraction(item.content);
    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
