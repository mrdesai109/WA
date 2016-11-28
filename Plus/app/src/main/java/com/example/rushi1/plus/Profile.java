package com.example.rushi1.plus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Profile extends AppCompatActivity {

    private EditText room_name;
    private ListView lv;
    private Button b1,b2logout;


    private String takesname;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private FirebaseAuth firebaseAuth;
    private TextView tvWel;
    private Button users,grp;
    String myName;
    ArrayList<ListViewItem> list;
    CustomListViewAdapterPro adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Public ChatRooms");

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(Profile.this,MainActivity.class));
        }
        list=new ArrayList<ListViewItem>();
        room_name=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);
        b2logout=(Button)findViewById(R.id.button3logout);
        lv=(ListView)findViewById(R.id.listview);
        tvWel=(TextView) findViewById(R.id.textView2wel);
        users = (Button) findViewById(R.id.button3users);
        grp=(Button)findViewById(R.id.button3grp);


        final FirebaseUser user = firebaseAuth.getCurrentUser();
        tvWel.setText("Welcome "+user.getDisplayName());


        adapter = new CustomListViewAdapterPro(Profile.this, list);
        lv.setAdapter(adapter);

        grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myName=user.getDisplayName();
                Intent g = new Intent(Profile.this,CreateGroup.class);
                g.putExtra("myname",myName);
                startActivity(g);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(Profile.this, ListUsers.class);
                startActivity(o);
            }
        });

        b2logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
                startActivity(new Intent(Profile.this,MainActivity.class));

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);
            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<ListViewItem> set = new HashSet<ListViewItem>();
                final Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                              final  String x = ((DataSnapshot)i.next()).getKey();
        if(x.contentEquals("users")||x.contentEquals("groups")||x.contentEquals("messages"))
            continue;
                                set.add(new ListViewItem(){{
                                title = x;
                                subTitle = "No new messages";
                            }
                            });


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
                Intent intent = new Intent(getApplicationContext(),Chatroom.class);
                intent.putExtra("room_name",(((TextView) view.findViewById(R.id.textViewTitle)).getText().toString()));
                intent.putExtra("user_name",user.getDisplayName().toString());
                startActivity(intent);
            }
        });

    }

    class ListViewItem {
        public String title;
        public String subTitle;
    }
}
