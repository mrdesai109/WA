package com.example.rushi1.plus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.Map;

public class CreateGroup extends AppCompatActivity {
    String getMyName;
    ListView lv;
    ArrayList<String> list=new ArrayList<String>();
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter adapter;
    DatabaseReference pointer = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference pointer2 = FirebaseDatabase.getInstance().getReference().child("groups");
    int count=0;
    String enterGN;
    String y="";
    private Button b1;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        getMyName=getIntent().getExtras().get("myname").toString();
        setTitle("Select users to create group");
        lv=(ListView)findViewById(R.id.listViewgrp);
        adapter=new ArrayAdapter<String>(CreateGroup.this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        b1 = (Button)findViewById(R.id.button3sg);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(CreateGroup.this, MyGroups.class);
                startActivity(x);
            }
        });


        pointer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String myName = user.getDisplayName();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    UserInf userObj = ds.getValue(UserInf.class);
                    String hisName = userObj.getDispname();
                    if(!userObj.getDispname().contentEquals(myName)){
                        adapter.add(hisName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                count++;
                mode.setTitle(count+" users selected");
                listItems.add(list.get(position));

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.my_context_menu,menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.add_id:

                        for(int i=0;i<listItems.size();i++){
                            y = y +listItems.get(i).toString() + listItems.get(i).toString() + listItems.get(i).toString();
                        }
                        y = y+user.getDisplayName()+user.getDisplayName()+user.getDisplayName();
                        Toast.makeText(CreateGroup.this,y,Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                        builder.setTitle("Enter group name");
                        final EditText input = new EditText(CreateGroup.this);
                        builder.setView(input);
                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                enterGN=input.getText().toString();

                               pointer2.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       y = y+user.getDisplayName()+user.getDisplayName()+user.getDisplayName();
                                       enterGN=input.getText().toString();
                                       long n = dataSnapshot.getChildrenCount();

                                           Map<String,Object> map22 = new HashMap<String, Object>();
                                           map22.put(enterGN,"") ;
                                           pointer2.updateChildren(map22);

                                       Map<String,Object> map1 = new HashMap<String, Object>();
                                       DatabaseReference tempPointer = pointer2.child(enterGN);
                                       String x = tempPointer.push().getKey();
                                       tempPointer.updateChildren(map1);

                                       DatabaseReference tempPointer2 = tempPointer.child(x);
                                       Map<String,Object> map2 = new HashMap<String, Object>();
                                       map2.put("name",user.getDisplayName());
                                       map2.put("msg","Hello all");
                                       map2.put("keygrp",y);
                                       tempPointer2.updateChildren(map2);
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

                                   }
                               });

                                Toast.makeText(CreateGroup.this,"'"+enterGN+"' "+"created",Toast.LENGTH_LONG).show();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        builder.show();
                        mode.finish();
                        count=0;
                        listItems.clear();
                        Toast.makeText(CreateGroup.this,"Okay",Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

    }
}
