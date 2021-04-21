package com.example.sgp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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

public class Third_Page extends AppCompatActivity {


    Workbook workbook;
    AsyncHttpClient asyncHttpClient;
    List<String> storyTitle,storyTitle_send;
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressBar progressBar;
    TextView wait;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        Intent i1=new Intent(getApplicationContext(),MainActivity.class);
        Intent i2=new Intent(getApplicationContext(),Fourth_activity.class);

        Button b1=findViewById(R.id.submit4);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        wait = findViewById(R.id.wait);



        Intent intent1 = getIntent();
        Map<String, Double> unSortedMap = new HashMap<String, Double>();

        final String[] get1 = {intent1.getStringExtra("Movie_name1")};
        final String[] get2 = {intent1.getStringExtra(("Rating_user"))};

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                get1[0] ="";
                get2[0] =null;
                i2.putStringArrayListExtra("Reco", (ArrayList<String>) storyTitle_send);
                storyTitle.clear();
                //storyTitle_send.clear();
                unSortedMap.clear();
                startActivity(i2);
                //startActivity(i1);
                finish();
            }
        });



        String URL = "https://drive.google.com/uc?export=download&id=1ZWAXf8FovYeSnJIsc08IzInEdRLp8FvR";


        storyTitle = new ArrayList<>();
        storyTitle_send = new ArrayList<>();



        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL, new FileAsyncHttpResponseHandler(this) {



            @Override

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Third_Page.this, "Error in Downloading Excel File", Toast.LENGTH_SHORT).show();
                wait.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override

            public void onSuccess(int statusCode, Header[] headers, File file) {
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);


                if(file != null){

                    wait.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);


                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);



                        Cell[] row0=sheet.getRow(0);
                        int noofcol=0;
                        for(int i = 1; i< 255; i++)
                        {
                            if((row0[i].getContents()).equals(get1[0]))
                            {

                                noofcol=i;

                            }

                        }


                        double rating= Double.parseDouble(get2[0]);
                        double rate=(rating/10)-0.51;


                        Cell[] col=sheet.getColumn(noofcol);

                        int cnt=0;
                        int cnt2=0;

                        Double finalrate=0.0;


                            for (int i = 1; i < sheet.getRows() - 1; i++) {

                                Cell[] rows = sheet.getRow(i);


                                finalrate = rate * (Double.parseDouble((col[i].getContents())));


                                //if (rating<=10 && cnt<11 &&  finalrate>=0.09 && (Double.parseDouble((col[i].getContents())))>0.0)

                                    //storyTitle.add(rows[0].getContents());

                                    unSortedMap.put(rows[0].getContents(), finalrate);



                            }


                            Map<String, Double> sorted = unSortedMap
                                    .entrySet()
                                    .stream()
                                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                    .collect(
                                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                                    LinkedHashMap::new));



                            Set<String> keys = sorted.keySet();
                            for (String key : keys) {
                                if (cnt <= 6) {
                                    storyTitle.add(key);
                                    cnt++;
                                }

                            }


                            for (String key : keys) {
                                if (cnt2 <= 1) {
                                    storyTitle_send.add(key);
                                    cnt2++;
                                }

                            }





                            cnt = 0;
                            cnt2= 0;
                            showData();


                    }

                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    catch (BiffException e) {
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
