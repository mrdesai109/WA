package com.example.rushi1.plus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ListUsers extends AppCompatActivity {

    ListView lv;

    CustomListViewAdapter adapter;
    DatabaseReference pointer = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference root4 = FirebaseDatabase.getInstance().getReference().child("messages");
    ArrayList<ListViewItem> listToAdd;
    ArrayList<String> listForReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    HashMap<String,Long> messageMap = new HashMap<String,Long>();
    String myName;
    long tempCount;
    Button b;
    String keyName;
    int i=0;
    HashSet<ListViewItem> set = new HashSet<ListUsers.ListViewItem>();
    Button bAllUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        setTitle("Registered Users");
        firebaseAuth=FirebaseAuth.getInstance();
        listToAdd=new ArrayList<ListViewItem>();
        listForReference=new ArrayList<String>();
        bAllUsers=(Button)findViewById(R.id.button3all);
        lv=(ListView)findViewById(R.id.listUsers);
        adapter = new CustomListViewAdapter(ListUsers.this, listToAdd);
        lv.setAdapter(adapter);
        user = firebaseAuth.getCurrentUser();
        myName=user.getDisplayName();
        b=(Button)findViewById(R.id.button3refresh);

        bAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(ListUsers.this,AllUsers.class);
                startActivity(x);
            }
        });


        pointer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   final String myName = user.getDisplayName();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    final UserInf userObj = ds.getValue(UserInf.class);
                    String hisName = userObj.getDispname();
                   if(!userObj.getDispname().contentEquals(myName)){
                        listForReference.add(userObj.getDispname());
                       // adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        root4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myName=user.getDisplayName();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                     keyName = ds.getKey();
                    final String secondDS = keyName;
                    if(keyName.contains(myName)){
                        DatabaseReference temp = root4.child(secondDS);
                        temp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                tempCount = dataSnapshot.getChildrenCount();
                               for(int x = 0;x<listForReference.size();x++){
                                  final String name = listForReference.get(x);
                                   if(secondDS.contains(name)){
                                       set.add(new ListViewItem(){{
                                           title = name;
                                           subTitle = tempCount+" total messages";
                                       }
                                       });

                                   }
                               }
                             //   messageMap.put(keyName+i,tempCount);
                             //   i++;

                                Toast.makeText(getApplicationContext(),tempCount+" new",Toast.LENGTH_LONG).show();
                                listToAdd.clear();
                                listToAdd.addAll(set);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = ((TextView) view.findViewById(R.id.textViewTitle)).getText().toString();
                Intent n = new Intent(ListUsers.this, ChatBetween.class);
                n.putExtra("hisname",x);
                //Toast.makeText(getApplicationContext(),namex,Toast.LENGTH_LONG).show();
                startActivity(n);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toShow =listForReference.get(2);
                Toast.makeText(getApplicationContext(),toShow,Toast.LENGTH_LONG).show();
            }
        });
    }
    class ListViewItem {
        public String title;
        public String subTitle;
    }
}
