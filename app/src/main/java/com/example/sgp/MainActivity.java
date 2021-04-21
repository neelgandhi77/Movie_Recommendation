package com.example.sgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView sebox;
    ListView list1;
    Button b1;
    ArrayList<getsetclass_data> List = new ArrayList<>();
    ArrayAdapter<getsetclass_data> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readdata();


        sebox = (SearchView) findViewById(R.id.s1);
        list1 = (ListView) findViewById(R.id.l1);



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, List);
        list1.setAdapter(adapter);

       // b1=findViewById(R.id.submit5);
        Intent i1 = new Intent(getApplicationContext(),Fourth_activity.class);
        /*b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(i1);

            }
        });
*/
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemvalue=list1.getItemAtPosition(position).toString();
                sebox.setQuery(itemvalue,false);

                //String temp= (String) sebox.getQuery();

                //Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_SHORT).show();

                //startActivity(intent);

               /* if(List.contains(itemvalue) || itemvalue.equals("")==false)
                {
                    //Toast.makeText(MainActivity.this,"Match Found "+query,Toast.LENGTH_LONG).show();

                        //list1.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this,
                                "Match Found " + itemvalue,
                                Toast.LENGTH_LONG).show();

                }
               */





            }
        });


        sebox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override

            public boolean onQueryTextSubmit(String query) {

                if(query.equals(""))
                {
                    list1.setVisibility(View.INVISIBLE);

                }
                else{
                    list1.setVisibility(View.VISIBLE);

                }

                senddata(query);


                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter.getCount()==0)
                {
                    list1.setVisibility(View.INVISIBLE);

                }



                if(newText.equals(""))
                {
                    list1.setVisibility(View.INVISIBLE);
                }
                else{
                    if(adapter.getCount()==0)
                    {
                        list1.setVisibility(View.INVISIBLE);

                    }
                    else{
                        list1.setVisibility(View.VISIBLE);
                    }


                }

                adapter.getFilter().filter(newText);

                return false;
            }

        });


    }


private void senddata(String s)
{
    Intent intent = new Intent(getApplicationContext(),second_page.class);
    intent.putExtra("Movie_name",s);
    //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();

    startActivity(intent);
    finish();
}


    private void readdata() {
        InputStream i=getResources().openRawResource(R.raw.titles3);
        BufferedReader reader=  new BufferedReader (
            new InputStreamReader(i, Charset.forName("UTF-8"))
        );
        String line="";
        try {

            reader.readLine();
            while((line=reader.readLine())!=null) {

                String[] sep=line.split(",");
                getsetclass_data d1=new getsetclass_data();
                d1.setTitle(sep[0]);
                List.add(d1);

            }
        }

        catch (IOException e) {
            Log.d("Error ", line,e);
            e.printStackTrace();

        }

    }
}