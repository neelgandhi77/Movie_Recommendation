package com.example.sgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class second_page extends AppCompatActivity {
    TextView t1;
    EditText rating1;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        Intent intent = getIntent();
        Intent intent0 = new Intent(getApplicationContext(),MainActivity.class);
        Intent intent1 = new Intent(getApplicationContext(),Third_Page.class);


        t1 = findViewById(R.id.sptv1);
        rating1= findViewById(R.id.ratinginput);


        String get = intent.getStringExtra("Movie_name");
        t1.setText(get);


        b1=(Button) findViewById(R.id.submit);
        b2=(Button) findViewById(R.id.submit3);

        b1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                if (rating1.getText().toString().equals("") || (TextUtils.isDigitsOnly(rating1.getText().toString()))==false)
                {

                    Toast.makeText(getApplicationContext(),"Empty / String Content : Rating",Toast.LENGTH_LONG).show();
                    //startActivity(intent0);
                    //finish();

                }
                else if ((Integer.parseInt(rating1.getText().toString()) > 0) && (Integer.parseInt(rating1.getText().toString()) <= 10)){


                    intent1.putExtra("Movie_name1",get);
                    intent1.putExtra("Rating_user",rating1.getText().toString());
                    startActivity(intent1);
                    finish();
                }

                else
                    {
                        Toast.makeText(getApplicationContext(),"Out of Range : Ratings",Toast.LENGTH_LONG).show();
                        startActivity(intent0);
                        finish();
              }


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent0);
                finish();
            }
        });

    }
}