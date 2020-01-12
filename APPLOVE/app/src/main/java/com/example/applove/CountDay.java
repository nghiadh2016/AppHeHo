package com.example.applove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applove.Model.Const;
import com.example.applove.Model.ImageConvert;
import com.example.applove.Model.User;

import java.util.Date;

public class CountDay extends AppCompatActivity {
    TextView tvCount;
    TextView nameMale;
    TextView ageMale;
    TextView genderMale;
    TextView nameFe;
    TextView ageFe;
    TextView genderFe;
    TextView tvBack;
    String script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdaylayout);
        tvCount = (TextView)findViewById(R.id.tvCountdown);
        tvBack = (TextView)findViewById(R.id.tvBack);


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDay.this,Profile.class);
                startActivity(intent);
            }
        });

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.avatar2);

        Bitmap circularBitmap = ImageConvert.getRoundedCornerBitmap(bitmap, 500);
        Bitmap circularBitmap2 = ImageConvert.getRoundedCornerBitmap(bitmap2, 500);


        ImageView circularImageView = (ImageView) findViewById(R.id.imgAvatarmale);
        circularImageView.setImageBitmap(circularBitmap);

        ImageView circularImageView2 = (ImageView) findViewById(R.id.imgAvatarfemale);
        circularImageView2.setImageBitmap(circularBitmap2);
        this.nameMale= findViewById(R.id.tvNameMale);
        this.ageMale= findViewById(R.id.tvAgeMale);
        this.genderMale= findViewById(R.id.tvGenderMale);
        this.nameFe=findViewById(R.id.tvNamefemale);
        this.ageFe=findViewById(R.id.tvAgeFemale);
        this.genderFe=findViewById(R.id.tvGenderFemale);
        this.getDataLove();

        CountDownTimer w= new CountDownTimer(60000*60*24, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCount.setText("Seconds remaining: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                tvCount.setText("Seconds remaining: 0");
            }
        }.start();

    }
    public void getDataLove(){
        this.nameMale.setText(Profile.user.getName());
        this.ageMale.setText(Profile.user.getBirthday().toString());
        this.genderMale.setText(Profile.user.getSex());
        script="SELECT * FROM " + Const.tableSearch;
        int id=-1;
        User user=new User();
        Cursor cursor = MainActivity.database.rawQuery(script,null);
        if(cursor.moveToFirst()){
            do {
                int idd=Integer.parseInt(cursor.getString(1));
                if(idd==MainActivity.id){
                    String status=cursor.getString(5);
                    if(status.equals(Const.SearchLove)||status.equals(Const.SearchRequest)) id=Integer.parseInt(cursor.getString(2));
                }

            }
            while (cursor.moveToNext());
        }
        script="SELECT * FROM "+Const.tableUser;
        cursor=MainActivity.database.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do{
                int idd=Integer.parseInt(cursor.getString(0));
                if(idd==id) {
                    user.setName(cursor.getString(1));
                    user.setBirthday(new Date(cursor.getString(2)));
                    user.setSex(cursor.getString(7).equals("1") ? "Nam" : "Nu");
                }
            }while(cursor.moveToNext());
        }
        this.nameFe.setText(user.getName());
        this.ageFe.setText(user.getBirthday().toString());
        this.genderFe.setText(user.getSex());



    }

}
