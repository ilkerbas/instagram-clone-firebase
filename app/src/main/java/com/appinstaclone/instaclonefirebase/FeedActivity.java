package com.appinstaclone.instaclonefirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    FeedRecyclerAdapter feedRecyclerAdapter;

    ArrayList<String> emails, expressions, images;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options, menu);

        firebaseAuth = FirebaseAuth.getInstance();

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.upload)
        {
            Intent intentUpload = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intentUpload);
        }
        else if(item.getItemId() == R.id.signOut){
            firebaseAuth.signOut();
            Intent intentUpload = new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intentUpload);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        emails = new ArrayList<>(); images = new ArrayList<>(); expressions = new ArrayList<>();


        firebaseFirestore = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedRecyclerAdapter = new FeedRecyclerAdapter(emails, expressions, images);
        recyclerView.setAdapter(feedRecyclerAdapter);

        getSnapData();
    }

    public void getSnapData(){
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){

                        Map<String, Object> map = snapshot.getData();

                        String email = (String) map.get("email");
                        String url = (String) map.get("url");
                        String expression = (String) map.get("expression");

                        emails.add(email); expressions.add(expression); images.add(url);

                        feedRecyclerAdapter.notifyDataSetChanged();
                    }
                }


            }
        });
    }
}
