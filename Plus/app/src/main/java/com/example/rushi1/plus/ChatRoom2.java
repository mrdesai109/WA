package com.example.rushi1.plus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoom2 extends AppCompatActivity {

    private Button b2;
    private TextView tv1;
    private EditText et1;
    private String user_name, room_name,keygrp;
    private DatabaseReference root,root2;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room2);
        et1=(EditText)findViewById(R.id.editText3);
        tv1=(TextView)findViewById(R.id.textView3msgrp);
        b2 = (Button)findViewById(R.id.button3sendgrp);

        user_name=getIntent().getExtras().get("username25").toString();
        room_name=getIntent().getExtras().get("roomname25").toString();
        keygrp=getIntent().getExtras().get("keygrp25").toString();

        setTitle(""+room_name);

        root = FirebaseDatabase.getInstance().getReference().child("groups");
        root2 = root.child(room_name);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String,Object>();
                temp_key=root2.push().getKey();
                root2.updateChildren(map);

                DatabaseReference message_root = root2.child(temp_key);
                Map<String,Object> map2=new HashMap<String,Object>();
                map2.put("name",user_name);
                map2.put("msg",et1.getText().toString());
                map2.put("keygrp",keygrp);
                message_root.updateChildren(map2);

            }
        });

        root2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chatconvo(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chatconvo(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private String name,msg,keygrp2;
    private void append_chatconvo(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            keygrp2= (String)((DataSnapshot)i.next()).getValue();
            msg= (String)((DataSnapshot)i.next()).getValue();
            name= (String)((DataSnapshot)i.next()).getValue();

            tv1.append(name +" : "+msg+" \n");
        }
    }
}
