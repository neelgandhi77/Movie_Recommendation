package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class Third_Page extends AppCompatActivity {
    Workbook workbook;
    AsyncHttpClient asyncHttpClient;
    List<String> storyTitle,storyContent,thumbImages;
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressBar progressBar;
    TextView wait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        Intent i1=new Intent(getApplicationContext(),MainActivity.class);
        Button b1=findViewById(R.id.submit2);


        Intent intent1 = getIntent();

        final String[] get1 = {intent1.getStringExtra("Movie_name1")};
        final Double[] get2 = {intent1.getDoubleExtra(("Rating_user"), 5.0)};

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get1[0] ="";
                get2[0] =0.0;
                storyTitle.clear();
                startActivity(i1);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        wait = findViewById(R.id.wait);
        String URL = "https://drive.google.com/uc?export=download&id=1ZWAXf8FovYeSnJIsc08IzInEdRLp8FvR";
        //https://drive.google.com/file/d/1ZXyXBmOSCd5DfnyrFprHJ1LyyR2qnh09/view?usp=sharing
        //https://drive.google.com/file/d/1ZWAXf8FovYeSnJIsc08IzInEdRLp8FvR/view?usp=sharing

        storyTitle = new ArrayList<>();
        storyContent = new ArrayList<>();
        thumbImages = new ArrayList<>();

//
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Third_Page.this, "Error in Downloading Excel File", Toast.LENGTH_SHORT).show();
                wait.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if(file != null){
                    //text.setText("Success, DO something with the file.");
                    wait.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);




                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);
                        //int trows=sheet.getRows();




                        Cell[] row0=sheet.getRow(0);
                        int noofcol=0;
                        for(int i = 1; i< 255; i++)
                        {
                            if((row0[i].getContents()).equals(get1[0]))
                            {

                                noofcol=i;

                            }

                        }


                        double rating= get2[0];
                        double rate=(rating/10)-0.30;

                        Cell[] col=sheet.getColumn(noofcol);

                        int cnt=0;
                        Double finalrate=0.0;


                            for(int i=1;i<sheet.getRows()-1;i++) {

                                Cell[] rows = sheet.getRow(i);


                                finalrate=rate*(Double.parseDouble((col[i].getContents())));


                                    if (rating<=10 && cnt<11 &&  finalrate>=0.11 && (Double.parseDouble((col[i].getContents())))>0.0)
                                    {
                                        storyTitle.add(rows[0].getContents());
                                        cnt++;
                                    }


                            }


                        cnt=0;
                        showData();



                        /*
                        Cell[] col0=sheet.getColumn(0);
                        int noofrow=0;
                        for(int i = 1; i< 255; i++)
                        {
                            if((col0[i].getContents()).equals(get1))
                            {

                                noofrow=i;

                            }

                        }


                        double rating= get2;
                        double rate=(rating/10)-0.25;

                        Cell[] row=sheet.getRow(noofrow);


                        Double finalrate;
                        for(int i=1;i<sheet.getColumns()-1;i++) {

                            Cell[] cols = sheet.getColumn(i);


                            finalrate=(rate*(Double.parseDouble((row[i].getContents()))));
                            if (finalrate>0.09 && (Double.parseDouble((row[i].getContents())))>0)
                            {
                                storyTitle.add(cols[0].getContents());
                            }

                        }

                        showData();
*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    private void showData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,storyTitle);
        recyclerView.setAdapter(adapter);
    }
}
