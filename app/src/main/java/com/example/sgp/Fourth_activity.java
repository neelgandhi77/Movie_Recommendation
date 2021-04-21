package com.example.sgp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import static java.util.stream.Collectors.toMap;

public class Fourth_activity extends AppCompatActivity {



    List<String> storyTitle2,final1;
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressBar progressBar;
    TextView wait;
    DatabaseReference reference;
    List<String> ds;


    ArrayList<firebase_data>arrylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);

        Intent i1=new Intent(getApplicationContext(),MainActivity.class);
        Intent i2=getIntent();

        Button b1=findViewById(R.id.submit4);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        wait = findViewById(R.id.wait);

        progressBar.setVisibility(View.GONE);
        wait.setVisibility(View.GONE);


        storyTitle2 = new ArrayList<>();
        final1 = new ArrayList<>();

        storyTitle2 = i2.getStringArrayListExtra("Reco");
        final1.addAll(storyTitle2);

        reference=FirebaseDatabase.getInstance().getReference().child("Data");
        arrylist= new ArrayList<firebase_data>();


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(i1);

            }
        });

    int i=0;

    while(i<final1.size()){


        firebase_data data =new firebase_data(final1.get(i));
        arrylist.add(data);
        i++;
    }

        reference.child("List").push().setValue(arrylist);


/*
        reference.setValue(final1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                }
            }
        });


/*
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    //ds.clear();
                    for(DataSnapshot dss:dataSnapshot.getChildren()){

                            String temp= dss.getValue(String.class);
                            ds.add(temp);

                    }


            StringBuilder stringBuilder= new StringBuilder();

            for(int i=0;i<ds.size();i++){
                stringBuilder.append(ds.get(i)+",");
            }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/
            showData();
        }





    private void showData() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,final1);
        recyclerView.setAdapter(adapter);
    }
}
