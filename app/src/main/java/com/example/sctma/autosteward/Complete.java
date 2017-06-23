package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class Complete extends AppCompatActivity {

    int slotnum;
    boolean pickUpFines;
    boolean completeSlot;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        Intent intent = getIntent();
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        pickUpFines = intent.getBooleanExtra("FINES?",false);
        completeSlot = intent.getBooleanExtra("SLOT?", true);
        if(completeSlot)
            MainActivity.fullRef.child("CurrentSlots").child("" + slotnum).child("name").setValue("Done");
        if(pickUpFines)
        {
            //add the amount to the fines lists
        }
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent result = new Intent();
                setResult(1, result);
                finish();
            }
        }, 5000L);
    }//on create
}
