package com.example.chandra.tiaafunding;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chandra.tiaafunding.dto.UserAccounts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chandra on 9/7/2016.
 */
public class ListAdapter extends ArrayAdapter<UserAccounts> {

    Context context;
    int resource;
    List<UserAccounts> list;

    public ListAdapter(Context context, int resource, List<UserAccounts> objects){
        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        CardView view = (CardView)convertView.findViewById(R.id.card_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView type = (TextView)convertView.findViewById(R.id.type);
        TextView number = (TextView)convertView.findViewById(R.id.number);
        TextView balance = (TextView)convertView.findViewById(R.id.balance);

        type.setText(list.get(position).getTypeOfAccount());
        number.setText(list.get(position).getAccountnumber());
        balance.setText("$ " +list.get(position).getBalance());
        return convertView;
    }
}
