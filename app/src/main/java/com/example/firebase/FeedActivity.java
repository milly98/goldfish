package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {
    ListView listView;
    PostClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailfromfirebase;
    ArrayList<String> userimagefromfirebase;
    ArrayList<String> usercommentfromfirebase;
    private FirebaseAuth mAuth;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(getApplicationContext(),UploadActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.log_out){
            mAuth.signOut();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);
        mAuth=FirebaseAuth.getInstance();
        listView=findViewById(R.id.Feedactivity_listView);
        useremailfromfirebase=new ArrayList<String>();
        usercommentfromfirebase=new ArrayList<String>();
        userimagefromfirebase=new ArrayList<String>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        adapter=new PostClass(useremailfromfirebase,usercommentfromfirebase,userimagefromfirebase,this);
        listView.setAdapter(adapter);
        getFileFromDB();
    }
    public void getFileFromDB(){
        DatabaseReference databaseReference=firebaseDatabase.getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        HashMap<String,String> hashMap=(HashMap<String, String>) ds.getValue();
                        useremailfromfirebase.add(hashMap.get("useremail"));
                        usercommentfromfirebase.add(hashMap.get("usercomment"));
                        userimagefromfirebase.add(hashMap.get("downloadurl"));
                        adapter.notifyDataSetChanged();
                        //System.out.println("FBV useremail: "+hashMap.get("useremail"));
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
