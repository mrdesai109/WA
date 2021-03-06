package com.example.rushi1.plus;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rushi1 on 07-10-2016.
 */
public class CustomListViewAdapterPro extends BaseAdapter {
    ArrayList<Profile.ListViewItem> items;
    Context context;

    public CustomListViewAdapterPro(Activity context, ArrayList<Profile.ListViewItem> items){
        super();
        this.context=context;
        this.items=items;


    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Profile.ListViewItem item = items.get(position);

        View vi = View.inflate(context,R.layout.item_row,null);
        TextView titleA = (TextView)vi.findViewById(R.id.textViewTitle);
        TextView subTitleA = (TextView)vi.findViewById(R.id.textViewsubTitle);
        titleA.setText(item.title);
        subTitleA.setText(item.subTitle);
        return vi;
    }
}
