package com.example.rushi1.plus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class AllUsers extends AppCompatActivity {

    ListView lv;
    ArrayList<ListViewItem> list;
    CustomListViewAdapter3 adapter;
    DatabaseReference pointer = FirebaseDatabase.getInstance().getReference().child("users");
    HashSet<ListViewItem> set = new HashSet<AllUsers.ListViewItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        setTitle("All Other Users");
        lv=(ListView)findViewById(R.id.listViewall);
        list=new ArrayList<AllUsers.ListViewItem>();
        adapter=new CustomListViewAdapter3(AllUsers.this,list);
        lv.setAdapter(adapter);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        pointer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String myName = user.getDisplayName();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    final UserInf userObj = ds.getValue(UserInf.class);
                    String hisName = userObj.getDispname();
                    if(!userObj.getDispname().contentEquals(myName)){
                        set.add(new ListViewItem(){{
                            nameList = userObj.getDispname();
                            regionList="Region- "+userObj.getRegion();
                            emailList = "Email- "+userObj.getEmail();
                            phoneNumberList = "Contact No- "+userObj.getMobile();
                        }
                        });
                    }
                }
                list.clear();
                list.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = ((TextView) view.findViewById(R.id.textView7name)).getText().toString();
                Intent n = new Intent(AllUsers.this, ChatBetween.class);
                n.putExtra("hisname",x);
                //Toast.makeText(getApplicationContext(),namex,Toast.LENGTH_LONG).show();
                startActivity(n);
            }
        });


    }
    class ListViewItem {
        public String nameList;
        public String regionList;
        public String emailList;
        public String phoneNumberList;

    }
}
