package com.example.luozenglin.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.luozenglin.mymemorandum.R;

import java.util.List;

public class MemoAdapter extends ArrayAdapter {
    private int resourceId;

    public MemoAdapter(Context context, int resource, List<Item> list) {
        super(context, resource,list);
        resourceId = resource;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Item item = (Item)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView textDate = (TextView) view.findViewById(R.id.textDate);
        TextView textTime = (TextView) view.findViewById(R.id.textTime);
        TextView mainText = (TextView) view.findViewById(R.id.mainText);
        textDate.setText(Item.getDate(item.getDatetime()));
        textTime.setText(Item.getTime(item.getDatetime()));
        mainText.setText(item.getContent());
        return view;
    }
}
