package com.example.applove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applove.Model.Const;

public class RegisterActivity extends AppCompatActivity {
    String script;
    EditText mTextUserName;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    public static SQLiteDatabase database=MainActivity.database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mTextUserName = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextCnfPassword= (EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister = (Button)findViewById(R.id.button_register);
        mTextViewLogin = (TextView)findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(LoginIntent);
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUserName.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();
                if(pwd.equals(cnf_pwd)){
                    long val = 1;
                    long index=0;
                    script="SELECT * FROM "+Const.tableUser;
                    Cursor cursor=database.rawQuery(script,null);
                    if(cursor.moveToFirst()){
                        do{
                            index++;
                            int idd=Integer.parseInt(cursor.getString(0));
                            String pass=cursor.getString(6);
                            String nameuser=cursor.getString(5);
                            if(nameuser.equals(user)){
                                val=0;
                            }
                        }
                        while(cursor.moveToNext());
                    }
                    if(!pwd.equals(cnf_pwd)){
                        val=0;
                    }

                    if(val > 0){
                        ContentValues values = new ContentValues();
                        values.put(Const.tableId, index+1);
                        values.put(Const.tableUserName, "");
                        values.put(Const.tableUserBirthday, "2000/1/1");
                        values.put(Const.tableUserLinkAvata, "");
                        values.put(Const.tableUserLinkFace, "");
                        values.put(Const.tableUserUserName,user );
                        values.put(Const.tableUserPassword, pwd);
                        values.put(Const.tableUserIdSex, 1);
                        database.insert(Const.tableUser, null, values);
                        Toast.makeText(RegisterActivity.this,"You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
