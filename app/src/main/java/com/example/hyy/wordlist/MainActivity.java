package com.example.hyy.wordlist;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import  android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyy.wordlist.dummy.DummyContent;

public class MainActivity extends Activity implements ItemFragment.OnListFragmentInteractionListener,fragment_detail.OnFragmentInteractionListener{
        private worddbhelper dbHelper;
         private TextView adds;
  public  Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adds=this.findViewById(R.id.l);
       this.registerForContextMenu(adds);
       context = getApplicationContext();
      //  dbHelper = new worddbhelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "添加");
        menu.add(0, 1, 0, "修改");
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
            default:

        }
        return super.onContextItemSelected(mi);
    }

    void addword(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_add_, null);
        builder.setView(view);
       builder.create();

        builder.show();
    }
    void changeword(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_changefragment, null);
        builder.setView(view);
       builder.create();

        builder.show();
    }

    public void onFragmentInteraction(String id){
        Bundle arguments=new Bundle();
            arguments.putString("id",id);
            fragment_detail fragment=new fragment_detail();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().replace(R.id.worddetail,fragment).commit();

    }

       /* Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//创建或打开现有的数据库
                dbHelper.getWritableDatabase();
            }
        });*/
    public void onListFragmentInteraction(DummyContent.DummyItem item){
        onFragmentInteraction("1");
    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
