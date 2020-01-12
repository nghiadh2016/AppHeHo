package com.example.applove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applove.Model.Const;
import com.example.applove.Model.CtUserFavorite;
import com.example.applove.Model.User;
import com.example.applove.Model.favorite;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Profile extends AppCompatActivity {
    String script;
    EditText editSexUser;
    EditText editName;
    EditText editDate;
    CheckBox checkMusic;
    CheckBox checkBook;
    CheckBox checkMove;
    CheckBox checkPhoto;
    CheckBox checkGame;
    Button buttonSave;
    Button buttonNext;
    Button btnRequest;
    Button btnLogout;
    int indexCT=0;

    public static User user=new User();
    List<favorite> listFavorite=new ArrayList<>();
    List<CtUserFavorite> listCtUserGavorite=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        editSexUser=findViewById(R.id.sex);
        editName=findViewById(R.id.tv_name);
        editDate=findViewById(R.id.tv_date);
        checkMusic=findViewById(R.id.checkmusic);
        checkBook=findViewById(R.id.checkBook);
        checkMove=findViewById(R.id.checkMove);
        checkPhoto=findViewById(R.id.checkPhoto);
        checkGame=findViewById(R.id.checkGame);
        buttonNext=findViewById(R.id.buttonnext);
        this.btnRequest=findViewById(R.id.buttonViewRequest);
        this.btnLogout=findViewById(R.id.buttonlogout);
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, RequestLove.class);
                startActivity(intent);
            }
        });
        this.getDataUserProfile();
        buttonSave=findViewById(R.id.buttonsave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateDataUser();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage();
            }
        });

    }
    public void changePage(){
        script="SELECT * FROM " + Const.tableSearch;
        int id=-1;
        Cursor cursor = MainActivity.database.rawQuery(script,null);
        if(cursor.moveToFirst()){
            do {
                int idd=Integer.parseInt(cursor.getString(1));
                if(idd==MainActivity.id) {
                    String status = cursor.getString(5);
                    String day = cursor.getString(3);
                    if (status.equals(Const.SearchLove)) {
                        id = 0;
                        break;
                    }
                    if (status.equals(Const.SearchRequest)) {
                        Calendar cal = Calendar.getInstance();
                        Date date = new Date(day);
                        cal.setTime(date);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Calendar cals = Calendar.getInstance();
                        if (cal.compareTo(cals) > 0) {
                            id = 0;
                            break;
                        }
                    }
                }
            }
            while (cursor.moveToNext());
        }
        if(id==-1) {
            Intent intent = new Intent(Profile.this, SearchLove.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Profile.this, CountDay.class);
            startActivity(intent);
        }

    }

    public void updateDataUser(){
        String sexUser;
        if(editSexUser.getText().equals("Nu"))sexUser="2";
        else sexUser="1";
        script="UPDATE "+Const.tableUser+" SET "+Const.tableUserName+" = '"+editName.getText()+"',"+Const.tableUserIdSex+" = '"+sexUser+"' WHERE "+Const.tableId+" = "+user.getId();
        MainActivity.database.execSQL(script);
        if(checkMusic.isChecked()){
            listFavorite.get(0).setCheck(true);
        }
        if(checkBook.isChecked()){
            listFavorite.get(1).setCheck(true);
        }
        if(checkMove.isChecked()){
            listFavorite.get(2).setCheck(true);
        }
        if(checkPhoto.isChecked()){
            listFavorite.get(4).setCheck(true);
        }

        if(checkGame.isChecked()){
            listFavorite.get(3).setCheck(true);
        }
        for(int i=0;i<listFavorite.size();i++){
            if(listFavorite.get(i).isCheck()==true){
                ContentValues values = new ContentValues();
                indexCT++;
                values.put(Const.tableId, indexCT);
                values.put(Const.tableCtUserFavoriteIdUser, MainActivity.id);
                values.put(Const.tableCtUserFavoriteIdFavorite, i);
                MainActivity.database.insert(Const.tableCtUserFavorite, null, values);
            }
        }
        Toast.makeText(Profile.this,"Data Saved ",Toast.LENGTH_SHORT).show();
        getDataUserProfile();
    }

    public void getDataUserProfile(){
        checkGame.setChecked(false);
        checkPhoto.setChecked(false);
        checkMove.setChecked(false);
        checkBook.setChecked(false);
        checkMusic.setChecked(false);
        script="SELECT * FROM "+ Const.tableUser;
        Cursor cursor=MainActivity.database.rawQuery(script,null);
        if(cursor.moveToFirst()){
            do{
                int id=Integer.parseInt(cursor.getString(0));
                if(id==MainActivity.id){

                    user.setId(id);
                    user.setName(cursor.getString(1));
                    String day=cursor.getString(2);
                    String dayUser="";
                    for(int i=0;i<day.length();i++){
                        if(day.charAt(i)=='-'){
                            dayUser+='/';
                        }
                        else dayUser+=day.charAt(i);
                    }
                    Date date=new Date(dayUser);

                    user.setBirthday(date);
                    user.setLinkAvata(cursor.getString(3));
                    user.setLinkFace(cursor.getString(4));
                    int sexId=Integer.parseInt(cursor.getString(7));
                    if(sexId==1)user.setSex("Nam");
                    else user.setSex("Nu");
//                    script="SELECT * FROM "+Const.tableSex;
//                    Cursor cursors=MainActivity.database.rawQuery(script,null);
//                    if(cursors.moveToFirst()){
//                        do{
//                            int idd=Integer.parseInt(cursors.getString(0));
//                            if(idd==sexId){
//                                user.setSex(cursors.getString(1));
//                                break;
//                            }
//                        }while(cursors.moveToNext());
//                    }
                    break;
                }
            }while(cursor.moveToNext());
        }
        this.editSexUser.setText(user.getSex());
        this.editName.setText(user.getName());
        this.editDate.setText(user.getBirthday().toString());
        script="SELECT * From "+Const.tableFavorite;
        String script1=" SELECT * FROM "+Const.tableCtUserFavorite;
        Cursor cursor2=MainActivity.database.rawQuery(script,null);
        if (cursor2.moveToFirst()){
            do{
                favorite fa=new favorite();
                fa.setId(Integer.parseInt(cursor2.getString(0)));
                fa.setName(cursor2.getString(1));
                fa.setNote(cursor2.getString(2));
                listFavorite.add(fa);

            }while (cursor2.moveToNext());
        }
        cursor=MainActivity.database.rawQuery(script1,null);
        CtUserFavorite ctUserFa;
        if (cursor.moveToFirst()){
            do{
                indexCT++;
                int idUser=Integer.parseInt(cursor.getString(1));
                if(idUser==MainActivity.id){
                    listFavorite.get(Integer.parseInt(cursor.getString(2))).setCheck(true);
                }
            }while (cursor.moveToNext());

        }
        String like="";
        for(int j=0;j<listFavorite.size();j++){

            if(listFavorite.get(j).isCheck()==true){
                like+=listFavorite.get(j).getName()+",";
            }
        }
        user.setLiked(like);
        if(listFavorite.get(0).isCheck()){
            checkMusic.setChecked(true);
        }
        if(listFavorite.get(1).isCheck()){
            checkBook.setChecked(true);
        }
        if(listFavorite.get(2).isCheck()){
            checkMove.setChecked(true);
        }
        if(listFavorite.get(4).isCheck()){
            checkPhoto.setChecked(true);
        }
        if(listFavorite.get(3).isCheck()){
            checkGame.setChecked(true);
        }

    }


}
