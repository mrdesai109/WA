package com.example.rushi1.plus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatBetween extends AppCompatActivity {

    String getHisName;

    private FirebaseUser user;
    String hisName;
    String temp_key,temp_key2;

    String ourName,ourName2;
    String myName;
    String storeName;
    ListView lv;

    String superKey,superKey2;
    long n,b,n2,b2;
    ArrayList<String> list=new ArrayList<String>();

    private EditText msgfield;
    private Button b1;
    private TextView tv;


    private DatabaseReference root3 = FirebaseDatabase.getInstance().getReference().child("messages");


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_between);
        // lv=(ListView)findViewById(R.id.listView65);
        msgfield = (EditText)findViewById(R.id.editText3mes);
        getHisName = getIntent().getExtras().get("hisname").toString();
        setTitle("Chat Partner: "+getHisName);
        b1 = (Button)findViewById(R.id.button3s);
        tv=(TextView)findViewById(R.id.textView2);
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        myName = user.getDisplayName();
        ourName = myName + getHisName;
        ourName2 = getHisName + myName;
        superKey = ourName + ourName2;
        superKey2 = ourName2 + ourName;

        //root2.
      DatabaseReference root2 = root3.child(superKey);
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

        DatabaseReference root4 = root3.child(superKey2);
       root4.addChildEventListener(new ChildEventListener() {
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

    //    Toast.makeText(ChatBetween.this, superKey, Toast.LENGTH_LONG).show();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                root3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        getHisName = getIntent().getExtras().get("hisname").toString();
                        myName = user.getDisplayName();
                        ourName = myName + getHisName;
                        ourName2 = getHisName + myName;
                        superKey = ourName + ourName2;
                        n2 = dataSnapshot.getChildrenCount();
                        b2 = 0;

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String nameOfSubnode = ds.getKey();
                            if (nameOfSubnode.contains(ourName) || nameOfSubnode.contains(ourName2)) {
                                if (user.getDisplayName().contentEquals(myName) || user.getDisplayName().contentEquals(getHisName)) {
                                    DatabaseReference tempPointer = root3.child(nameOfSubnode);
                                    Map<String, Object> map60 = new HashMap<String, Object>();
                                    String x = tempPointer.push().getKey().concat(superKey);
                                    tempPointer.updateChildren(map60);

                                    DatabaseReference tempPointer2 = tempPointer.child(x);
                                    Map<String, Object> map100 = new HashMap<String, Object>();
                                    map100.put("name", myName);
                                    map100.put("msg", msgfield.getText().toString());
                                    map100.put("key", superKey);
                                    tempPointer2.updateChildren(map100);
                                    Toast.makeText(ChatBetween.this, "Child was there. Node created", Toast.LENGTH_LONG).show();
                                    break;
                                }

                            }else{
                                b2++;
                                if(n2==b2)
                                    break;

                            }



                        }
                        if(n2==b2){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put(superKey,"");

                            root3.updateChildren(map);

                            DatabaseReference keyAdder = root3.child(superKey);
                            Map<String,Object> map100=new HashMap<String,Object>();
                            keyAdder.updateChildren(map100);
                            //Main subnode created
                            DatabaseReference root4 = root3.child(superKey);
                            Map<String,Object> map60=new HashMap<String,Object>();
                            String x = root4.push().getKey().concat(superKey);

                            root4.updateChildren(map60);
                            //Node of subnode created
                            Map<String,Object> map65=new HashMap<String,Object>();
                            DatabaseReference temp_root2 = root4.child(x);
                            map65.put("name",myName);
                            map65.put("msg",msgfield.getText().toString());
                            map65.put("key",superKey);
                            temp_root2.updateChildren(map65);
                            Toast.makeText(ChatBetween.this,"Subnode",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



    }
    private String chat_msg,chat_username,chat_key;
    private void append_chatconvo(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            chat_msg= (String)((DataSnapshot)i.next()).getValue();
            chat_username= (String)((DataSnapshot)i.next()).getValue();
            chat_key= (String)((DataSnapshot)i.next()).getValue();
            tv.append(chat_key +" : "+chat_username+" \n");
        }
    }
}


