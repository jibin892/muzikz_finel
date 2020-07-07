package com.qboxus.musictok.Corona;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.qboxus.musictok.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Corona_virus extends AppCompatActivity {
ProgressDialog  progress;
TextView kerala_cases,kerala_recover_cases,world_cases_count,world_recover_count,india_recover_count,india_cases_count,
        tamilnadu_cases,tamilnadu_recover_cases,karnadaka_cases,karnadaka_recover_cases,delhi_cases,delhi_recover_cases,world_death,indias_death,datecorona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.BLACK);
            }
        }
        setContentView(R.layout.activity_corona_virus);

        loaddatas();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat gsdf = new SimpleDateFormat("dd-MM-yy");
       String cugrrentDay = String.valueOf(gsdf.format(new Date()));
        world_cases_count=findViewById(R.id.world_cases_count);
        world_recover_count=findViewById(R.id.world_recover_count);
        india_cases_count=findViewById(R.id.india_cases_count);
        india_recover_count=findViewById(R.id.india_recover_count);
        kerala_cases=findViewById(R.id.kerala_cases);
        kerala_recover_cases=findViewById(R.id.kerala_recover_cases);
        karnadaka_cases=findViewById(R.id.karnadaka_cases);
        karnadaka_recover_cases=findViewById(R.id.karnadaka_recover_cases);
        tamilnadu_cases=findViewById(R.id.tamilnadu_cases);
        tamilnadu_recover_cases=findViewById(R.id.tamilnadu_recover_cases);
        delhi_cases=findViewById(R.id.delhi_cases);
        delhi_recover_cases=findViewById(R.id.delhi_recover_cases);
        world_death=findViewById(R.id.world_death);
        indias_death=findViewById(R.id.indias_death);
        datecorona=findViewById(R.id.datecorona);
        datecorona.setText(cugrrentDay);

    }

    private void loaddatas() {


        progress = new ProgressDialog(Corona_virus.this);
        progress.setMessage("Wait a Moment...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        final DatabaseReference users = FirebaseDatabase.getInstance().getReference();
        Query query = users.child("Corona_details");
        final List<Coronaadapter> coronaadapter = new ArrayList<>();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    coronaadapter.add(dataSnapshot.getValue(Coronaadapter.class));
                    Coronaadapter userlogin = dataSnapshot.getValue(Coronaadapter.class);
                    String Kerala = userlogin.getKerala().toString();
                    String Tamilnadu = userlogin.getTamilnadu().toString();
                    world_cases_count.setText(userlogin.getWorld());
                    world_recover_count.setText(userlogin.getWorld_r());
                    india_cases_count.setText(userlogin.getIndia());
                    india_recover_count.setText(userlogin.getIndia_r());
                    kerala_cases.setText(userlogin.getKerala());
                    kerala_recover_cases.setText(userlogin.getKerala_r());
                    karnadaka_cases.setText(userlogin.getKarnataga());
                    karnadaka_recover_cases.setText(userlogin.getKarnataga_r());
                    delhi_cases.setText(userlogin.getDelhi());
                    delhi_recover_cases.setText(userlogin.getDelhi_r());
                    tamilnadu_cases.setText(userlogin.getTamilnadu());
                    tamilnadu_recover_cases.setText(userlogin.getTamilnadu_r());
                    delhi_cases.setText(userlogin.getDelhi());
                    delhi_recover_cases.setText(userlogin.getDelhi_r());
                    world_death.setText(userlogin.getworld_death());
                    indias_death.setText(userlogin.getIndia_deth());


                    progress.dismiss();
                 }
                else {

                    progress.dismiss();
                 }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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


    }
}