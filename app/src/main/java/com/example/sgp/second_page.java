package com.example.sgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class second_page extends AppCompatActivity {
    TextView t1;
    EditText rating1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        Intent intent = getIntent();
        t1 = findViewById(R.id.sptv1);
        rating1= findViewById(R.id.ratinginput);
        String get = intent.getStringExtra("Movie_name");

        t1.setText(get);


        b1=(Button) findViewById(R.id.submit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(),Third_Page.class);
                intent1.putExtra("Movie_name1",get);
                intent1.putExtra("Rating_user",rating1.getText());

                startActivity(intent1);
                finish();
            }
        });



    }
}