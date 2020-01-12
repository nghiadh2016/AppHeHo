package com.example.applove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applove.Model.Const;

import java.time.LocalDate;


public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase database;
    public String script;
    public static int id=-1;
    EditText mTextUserName;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.createDatabase();
        setContentView(R.layout.activity_main);
        mTextUserName = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mTextViewRegister = (TextView)findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent =new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUserName.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                script="SELECT * FROM "+Const.tableUser;
                boolean res=false;
                int index=0;
                Cursor cursor=database.rawQuery(script,null);
                if(cursor.moveToFirst()){
                    do{
                        int idd=Integer.parseInt(cursor.getString(0));
                        String pass=cursor.getString(6);
                        String nameuser=cursor.getString(5);
                        if(pass.equals(pwd)&&nameuser.equals(user)){
                            res=true;
                            id=idd;
                            break;
                        }
                    }
                    while(cursor.moveToNext());
                }
                if(res==true){
                    Intent intent = new Intent(MainActivity.this,Profile.class);
                    Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void createDatabase(){
        if(database==null) {
            database = this.openOrCreateDatabase("xtdb.sqlite", 0, null);

            this.createTableUser();
            this.createTableSex();
            this.createTableImg();
            this.createTableFavorite();
            this.createTableCtUserFavorite();
            this.createTableSearch();
        }

    }
    public void createTableUser(){
        database.execSQL("DROP TABLE IF EXISTS " + Const.tableUser);
        this.script = "CREATE TABLE " + Const.tableUser + "("
                + Const.tableId + " INTEGER PRIMARY KEY,"
                + Const.tableUserName + " TEXT,"
                + Const.tableUserBirthday + " Date,"
                + Const.tableUserLinkAvata + " TEXT,"
                + Const.tableUserLinkFace + " TEXT,"
                + Const.tableUserUserName + " TEXT,"
                + Const.tableUserPassword + " TEXT,"
                + Const.tableUserIdSex + " TEXT"+ ")";
        database.execSQL(script);
        this.addDataUser();
    }
    public void addDataUser(){
        ContentValues values = new ContentValues();
        values.put(Const.tableId, 1);
        values.put(Const.tableUserName, "Bui Trong Nghia");
        values.put(Const.tableUserBirthday, "1994/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "nghiadh2016");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 1);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 2);
        values.put(Const.tableUserName, "Trinh Ngoc Hieu");
        values.put(Const.tableUserBirthday, "1998/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "hieu");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 1);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 3);
        values.put(Const.tableUserName, "Le Thi Hoa");
        values.put(Const.tableUserBirthday, "1999/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "hoa");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 4);
        values.put(Const.tableUserName, "Le Thi Hong");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "hong");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 5);
        values.put(Const.tableUserName, "Le Thi Thu");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "thu");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 6);
        values.put(Const.tableUserName, "Le Thi Van");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "van");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 7);
        values.put(Const.tableUserName, "Le Thi Diep");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "diep");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 8);
        values.put(Const.tableUserName, "Le Thi Huong");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "huong");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 9);
        values.put(Const.tableUserName, "Le Thi Lan");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "lan");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 10);
        values.put(Const.tableUserName, "Le Thi Nhi");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "Nhi");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 11);
        values.put(Const.tableUserName, "Le Thi Hanh");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "hanh");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 12);
        values.put(Const.tableUserName, "Le Thi tran");
        values.put(Const.tableUserBirthday, "2000/1/1");
        values.put(Const.tableUserLinkAvata, "timt");
        values.put(Const.tableUserLinkFace, "*****");
        values.put(Const.tableUserUserName, "tran");
        values.put(Const.tableUserPassword, "123456");
        values.put(Const.tableUserIdSex, 2);
        database.insert(Const.tableUser, null, values);

    }
    public void createTableSex(){
        database.execSQL("DROP TABLE IF EXISTS " + Const.tableSex);
        this.script = "CREATE TABLE " + Const.tableSex + "("
                + Const.tableId + " INTEGER PRIMARY KEY,"
                + Const.tableSexName + " TEXT"+")";
        database.execSQL(script);
        this.addDataSex();
    }
    public  void addDataSex(){
        ContentValues values = new ContentValues();
        values.put(Const.tableId, 1);
        values.put(Const.tableSexName, "Nam");
        database.insert(Const.tableSex, null, values);
        values = new ContentValues();
        values.put(Const.tableId, 2);
        values.put(Const.tableSexName, "Nu");
        database.insert(Const.tableSex, null, values);
    }
    public void createTableFavorite(){
        database.execSQL("DROP TABLE IF EXISTS " + Const.tableFavorite);
        this.script = "CREATE TABLE " + Const.tableFavorite + "("
                + Const.tableId + " INTEGER PRIMARY KEY,"
                + Const.tableFavoriteName + " TEXT,"
                + Const.tableFavoriteNote + " TEXT"+")";
        database.execSQL(script);
        this.addDaTaFavorite();
    }
    public void addDaTaFavorite(){
        ContentValues values = new ContentValues();
        values.put(Const.tableId, 1);
        values.put(Const.tableFavoriteName, "nghe nhac");
        values.put(Const.tableFavoriteNote, "");
        database.insert(Const.tableFavorite, null, values);

        values = new ContentValues();
        values.put(Const.tableId, 2);
        values.put(Const.tableFavoriteName, "doc sach");
        values.put(Const.tableFavoriteNote, "");
        database.insert(Const.tableFavorite, null, values);

        values = new ContentValues();
        values.put(Const.tableId, 3);
        values.put(Const.tableFavoriteName, "phuot");
        values.put(Const.tableFavoriteNote, "");
        database.insert(Const.tableFavorite, null, values);

        values = new ContentValues();
        values.put(Const.tableId, 4);
        values.put(Const.tableFavoriteName, "game");
        values.put(Const.tableFavoriteNote, "");
        database.insert(Const.tableFavorite, null, values);

        values = new ContentValues();
        values.put(Const.tableId, 5);
        values.put(Const.tableFavoriteName, "chup anh");
        values.put(Const.tableFavoriteNote, "");
        database.insert(Const.tableFavorite, null, values);
    }
    public void createTableCtUserFavorite(){
        database.execSQL("DROP TABLE IF EXISTS " + Const.tableCtUserFavorite);
        this.script = "CREATE TABLE " + Const.tableCtUserFavorite + "("
                + Const.tableId + " INTEGER PRIMARY KEY,"
                + Const.tableCtUserFavoriteIdUser + " INTEGER,"
                + Const.tableCtUserFavoriteIdFavorite + " INTEGER"+")";
        database.execSQL(script);
    }

    public void createTableImg(){
        database.execSQL("DROP TABLE IF EXISTS " + Const.tableImg);
        this.script = "CREATE TABLE " + Const.tableImg + "("
                + Const.tableId + " INTEGER PRIMARY KEY,"
                + Const.tableImgLink + " TEXT,"
                + Const.tableImgNote + " TEXT,"
                + Const.tableImgIdUser + " INTEGER"+")";
        database.execSQL(script);
    }
    public void createTableSearch(){
        database.execSQL("DROP TABLE IF EXISTS " + Const.tableSearch);
        this.script = "CREATE TABLE " + Const.tableSearch + "("
                + Const.tableId + " INTEGER PRIMARY KEY,"
                + Const.tableSearchIdUser + " INTEGER,"
                + Const.tableSearchIdUserUser + " INTEGER,"
                + Const.tableSearchDate + " DATE,"
                + Const.tableSearchMessage+" TEXT,"
                + Const.tableSearchStatus + " TEXT"+")";
        database.execSQL(script);
    }
}
