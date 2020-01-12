package com.example.applove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applove.Model.Const;
import com.example.applove.Model.CtUserFavorite;
import com.example.applove.Model.User;
import com.example.applove.Model.favorite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SearchLove extends AppCompatActivity {
    Button btnSearch;
    Button btnHome;
    Button btnAdd;
    TextView textName;
    TextView textBrithday;
    TextView textFavorite;
    TextView textColor;
    TextView textSex;
    TextView textFace;
    EditText editMess;
    List<User> listUserMale=new ArrayList<>();
    List<User> listUserFeMale=new ArrayList<>();
    User userss=new User();
    String script;
    List<favorite> listFavorite=new ArrayList<>();
    List<favorite> listFavoriteUser=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlovelayout);
        btnSearch= findViewById(R.id.btnSearch);
        btnHome=findViewById(R.id.btnBackHome);
        btnAdd=findViewById(R.id.btnAdd);
        this.textName=findViewById(R.id.showName);
        this.textBrithday=findViewById(R.id.showBirthday);
        this.textFavorite=findViewById(R.id.showInterest);
        this.textColor=findViewById(R.id.showColor);
        this.textSex=findViewById(R.id.showSex);
        this.textFace=findViewById(R.id.showFace);
        this.editMess=findViewById(R.id.editMessage);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchLove.this,Profile.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createListSearch();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLove();
            }
        });
        this.createListSearch();

    }
    public void addLove(){
        String message=this.editMess.getText().toString().trim();
        script="SELECT * FROM "+Const.tableSearch;
        Cursor cursor=MainActivity.database.rawQuery(script,null);
        boolean check=true;
        int index=0;
        if(cursor.moveToFirst()){
            do{
                index++;
                int id=Integer.parseInt(cursor.getString(0));
                int idUser= Integer.parseInt(cursor.getString(1));
                int idUserUser=Integer.parseInt(cursor.getString(2));
                String day=cursor.getString(3);
                String status= cursor.getString(5);
                if(idUser==MainActivity.id){
                    if(status.equals(Const.SearchLove)){
                        check=false;
                        break;
                    }
                    if(status.equals(Const.SearchRequest)){
                        Calendar cal = Calendar.getInstance();
                        Date date=new Date(day);
                        cal.setTime(date);
                        cal.add(Calendar.DAY_OF_MONTH,1);
                        Calendar cals= Calendar.getInstance();
                        if(cal.compareTo(cals)>0){
                            check=false;
                            break;
                        }
                        else{
                            script="UPDATE "+Const.tableSearch+" SET "+Const.tableSearchStatus+"='"+Const.SearchRefure+"'"+"WHERE "+Const.tableId+" = "+id;
                            MainActivity.database.execSQL(script);
                        }

                    }
                }
            }
            while(cursor.moveToNext());
        }
        if(check==true){
            index++;
            Calendar cel= Calendar.getInstance();
            String day=cel.get(Calendar.YEAR)+"/"+(cel.get(Calendar.MONTH)+1)+"/"+cel.get(Calendar.DAY_OF_MONTH);
            ContentValues values = new ContentValues();
            values.put(Const.tableId, index);
            values.put(Const.tableSearchIdUser, MainActivity.id);
            values.put(Const.tableSearchIdUserUser,this.userss.getId() );
            values.put(Const.tableSearchDate,day );
            values.put(Const.tableSearchMessage,this.editMess.getText().toString());
            values.put(Const.tableSearchStatus,Const.SearchRequest);
            MainActivity.database.insert(Const.tableSearch, null, values);
            Toast.makeText(SearchLove.this,"Đã thêm yêu cầu",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchLove.this,CountDay.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(SearchLove.this,"Bạn đã hẹn hò hoặc đã yêu cầu rồi",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchLove.this,CountDay.class);
            startActivity(intent);

        }
    }
    public void createListSearch(){
        script="SELECT * FROM "+ Const.tableUser;
        Cursor cursor=MainActivity.database.rawQuery(script,null);
        if(cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                String day = cursor.getString(2);

                Date date = new Date(day);

                user.setBirthday(date);
                user.setLinkAvata(cursor.getString(3));
                user.setLinkFace(cursor.getString(4));
                int sexId = Integer.parseInt(cursor.getString(7));
                if (sexId == 1) {
                    user.setSex("Nam");
                    listUserMale.add(user);
                } else {
                    user.setSex("Nu");
                    listUserFeMale.add(user);
                }
            } while (cursor.moveToNext());
        }
        if(Profile.user.getSex().equals("Nam")){
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
                    listFavoriteUser.add(fa);

                }while (cursor2.moveToNext());
            }
            cursor=MainActivity.database.rawQuery(script1,null);
            CtUserFavorite ctUserFa;
            if (cursor.moveToFirst()){
                do{
                    int idUser=Integer.parseInt(cursor.getString(1));
                    if(idUser==MainActivity.id){
                        listFavorite.get(Integer.parseInt(cursor.getString(2))).setCheck(true);
                    }
                }while (cursor.moveToNext());

            }
            for(int i=0;i<listUserFeMale.size();i++){
                int point=0;
                for(int j=0;j<listFavoriteUser.size();j++)listFavoriteUser.get(j).setCheck(false);
                cursor=MainActivity.database.rawQuery(script1,null);
                if (cursor.moveToFirst()){
                    do{
                        int idUser=Integer.parseInt(cursor.getString(1));
                        if(idUser==listUserFeMale.get(i).getId()){
                            listFavoriteUser.get(Integer.parseInt(cursor.getString(2))).setCheck(true);
                        }
                    }while (cursor.moveToNext());

                }
                String like="";
                for(int j=0;j<listFavoriteUser.size();j++){
                    if(listFavoriteUser.get(j).isCheck()==listFavorite.get(j).isCheck()){
                        point+=10;
                    }
                    if(listFavoriteUser.get(j).isCheck()==true){
                        like+=listFavoriteUser.get(j).getName()+",";
                    }
                }
                Random generator = new Random();
                point+=generator.nextInt();
                point%=100;
                if(point<0)point=-point;
                listUserFeMale.get(i).setPoint(point);
                listUserFeMale.get(i).setLiked(like);
            }
            userss.setPoint(0);
            for(int i=0;i<listUserFeMale.size();i++){
                if(userss.getPoint()<listUserFeMale.get(i).getPoint())userss=listUserFeMale.get(i);
            }
            this.textName.setText(userss.getName());
            this.textSex.setText(userss.getSex());
            this.textFace.setText("*************");
            this.textColor.setText("Xanh, Đỏ, Hồng");
            if(userss.getBirthday()!=null)this.textBrithday.setText(userss.getBirthday().toString());
            this.textFavorite.setText(userss.getLiked());
        }
        else{
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
                    listFavoriteUser.add(fa);

                }while (cursor2.moveToNext());
            }
            cursor=MainActivity.database.rawQuery(script1,null);
            CtUserFavorite ctUserFa;
            if (cursor.moveToFirst()){
                do{
                    int idUser=Integer.parseInt(cursor.getString(1));
                    if(idUser==MainActivity.id){
                        listFavorite.get(Integer.parseInt(cursor.getString(2))).setCheck(true);
                    }
                }while (cursor.moveToNext());

            }
            for(int i=0;i<listUserMale.size();i++){
                int point=0;
                for(int j=0;j<listFavoriteUser.size();j++)listFavoriteUser.get(j).setCheck(false);
                cursor=MainActivity.database.rawQuery(script1,null);
                if (cursor.moveToFirst()){
                    do{
                        int idUser=Integer.parseInt(cursor.getString(1));
                        if(idUser==listUserMale.get(i).getId()){
                            listFavoriteUser.get(Integer.parseInt(cursor.getString(2))).setCheck(true);
                        }
                    }while (cursor.moveToNext());

                }
                String like="";
                for(int j=0;j<listFavoriteUser.size();j++){
                    if(listFavoriteUser.get(j).isCheck()==listFavorite.get(j).isCheck()){
                        point+=10;
                    }
                    if(listFavoriteUser.get(j).isCheck()==true){
                        like+=listFavoriteUser.get(j).getName()+",";
                    }
                }
                Random generator = new Random();
                point+=generator.nextInt();
                point%=100;
                if(point<0)point=-point;
                listUserMale.get(i).setPoint(point);
                listUserMale.get(i).setLiked(like);
            }
            userss.setPoint(0);
            for(int i=0;i<listUserMale.size();i++){
                if(userss.getPoint()<listUserMale.get(i).getPoint())userss=listUserMale.get(i);
            }
            this.textName.setText(userss.getName());
            this.textSex.setText(userss.getSex());
            this.textFace.setText("*************");
            this.textColor.setText("Xanh, Đỏ, Hồng");
            if(userss.getBirthday()!=null)this.textBrithday.setText(userss.getBirthday().toString());
            this.textFavorite.setText(userss.getLiked());
        }
    }
}
