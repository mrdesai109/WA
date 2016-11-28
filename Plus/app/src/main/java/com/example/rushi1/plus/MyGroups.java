package com.example.rushi1.plus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyGroups extends AppCompatActivity {

    ListView lv;
    ArrayList<String> list=new ArrayList<String>();
    ArrayAdapter adapter;
    DatabaseReference pointer = FirebaseDatabase.getInstance().getReference().child("groups");
    FirebaseUser user;
    Set<String>  set = new HashSet<String>();

    String keygrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        setTitle("You Are Part Of...");

        lv=(ListView)findViewById(R.id.listView10);
        adapter=new ArrayAdapter<String>(MyGroups.this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        user = FirebaseAuth.getInstance().getCurrentUser();

       pointer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String myName = user.getDisplayName();


                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    final String nodeTitle = ds.getKey().toString();
                    final String keyCheck = myName+myName+myName;
                    DatabaseReference tempPointer = pointer.child(nodeTitle);


                         tempPointer.addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                 for(DataSnapshot ds2 : dataSnapshot.getChildren()){
                                     GroupName obj = ds2.getValue(GroupName.class);
                                     if(obj.getName().contentEquals(user.getDisplayName())){
                                         keygrp=obj.getKeygrp();
                                     }
                                     if(obj.getKeygrp().contains(keyCheck)){

                                         adapter.add(nodeTitle);
                                         set.add(nodeTitle);

                                     }else{
                                         Toast.makeText(MyGroups.this,"No Groups",Toast.LENGTH_LONG).show();
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


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChatRoom2.class);
                intent.putExtra("roomname25",((TextView)view).getText().toString());
                intent.putExtra("username25",user.getDisplayName().toString());
                intent.putExtra("keygrp25",keygrp);
                startActivity(intent);
            }
        });

    }
}
