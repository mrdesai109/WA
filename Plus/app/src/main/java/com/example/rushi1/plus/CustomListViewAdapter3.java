package com.example.rushi1.plus;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rushi1 on 17-10-2016.
 */
public class CustomListViewAdapter3 extends BaseAdapter{

    List<AllUsers.ListViewItem> items;
    Context context;

    public CustomListViewAdapter3(Activity context, List<AllUsers.ListViewItem> items){
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AllUsers.ListViewItem item = items.get(position);

        View vi = View.inflate(context,R.layout.item_row2,null);
        TextView name = (TextView)vi.findViewById(R.id.textView7name);
        TextView region = (TextView)vi.findViewById(R.id.textView8region);
        TextView email = (TextView)vi.findViewById(R.id.textView9email);
        TextView phone = (TextView)vi.findViewById(R.id.textView10phone);
        name.setText(item.nameList);
        region.setText(item.regionList);
        email.setText(item.emailList);
        phone.setText(item.phoneNumberList);


        return vi;
    }
}
