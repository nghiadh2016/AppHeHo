package com.example.applove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.applove.Model.Const;
import com.example.applove.Model.User;
import com.example.applove.Model.MyAdapter;

import java.util.ArrayList;

public class RequestLove extends AppCompatActivity {
    private ListView lvListUser;
    private static  ArrayList<User> listUser=new ArrayList<>();
    private ArrayList<Integer> listIdUser=new ArrayList<>();
    private MyAdapter myAdapter;
    String script;
    public static Button btnAccept;
    public static Button btnDenife;
    Button backHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestlovelayout);
        getDataUser();
        this.backHome=findViewById(R.id.btnBackHome);
        initView();

        this.backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestLove.this, Profile.class);
                startActivity(intent);
            }
        });
    }
    public void addAcceptUser(int pos){
        script="UPDATE "+Const.tableSearch+" SET "+Const.tableSearchStatus+" ='"+Const.SearchLove+"' WHERE "+Const.tableId+" = "+listUser.get(pos).getId();
        MainActivity.database.execSQL(script);
        Toast.makeText(RequestLove.this,"Accept ",Toast.LENGTH_SHORT).show();

    }
    public static void denifeUser(int pos){
        String scripts="UPDATE "+Const.tableSearch+" SET "+Const.tableSearchStatus+" ='"+Const.SearchRefure+"' WHERE "+Const.tableSearchIdUser+" = "+listUser.get(pos).getId();
        MainActivity.database.execSQL(scripts);

    }
    public void getDataUser() {
        script = "SELECT * FROM " + Const.tableSearch;
        Cursor cursor = MainActivity.database.rawQuery(script, null);
        listIdUser=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(1));
                int iduser=Integer.parseInt(cursor.getString(2));
                String status = cursor.getString(5);
                if (status.equals(Const.SearchRequest)&&iduser==MainActivity.id) {
                    listIdUser.add(id);
                }
            } while (cursor.moveToNext());
        }
        script = "SELECT * FROM " + Const.tableUser;
        cursor = MainActivity.database.rawQuery(script, null);
        listUser=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                boolean check=false;
                User users=new User();
                users.setId(Integer.parseInt(cursor.getString(0)));
                users.setName(cursor.getString(1));
                int id=Integer.parseInt(cursor.getString(0));
                for(int i=0;i<listIdUser.size();i++){
                    if(listIdUser.get(i)==id){
                        listUser.add(users);
                        break;
                    }
                }

            }while (cursor.moveToNext());
        }
    }
    private void initView(){
        lvListUser = (ListView) findViewById(R.id.lv_list_person);
        myAdapter = new MyAdapter(this, listUser);

        lvListUser.setAdapter(myAdapter);
    }
}
