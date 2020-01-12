package com.example.applove.Model;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applove.MainActivity;
import com.example.applove.Profile;
import com.example.applove.R;
import com.example.applove.RequestLove;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<User> listUser;
    private LayoutInflater inflater;
    public static int id=-1;
    public String script;

    public MyAdapter(Context context, ArrayList<User> listUser){
        this.listUser = listUser;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public User getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView =  inflater.inflate(R.layout.item_person, parent, false);
            viewHolder.tvname = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.id=(TextView) convertView.findViewById(R.id.tv_id);

            convertView.setTag(viewHolder);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestLove.denifeUser(position);
                }
            });


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = listUser.get(position);

        viewHolder.tvname.setText(user.getName() );
        viewHolder.id.setText(String.valueOf(user.getId()));

        return convertView;

    }


    private class ViewHolder{
        TextView tvname;
        TextView id;
        Button btnAccept;
        Button btnDenife;

    }
}

