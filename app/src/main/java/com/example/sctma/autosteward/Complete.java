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
    String[] names;
    String[] emails;
    String singleEmail;
    int[] money;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        Intent intent = getIntent();
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        pickUpFines = intent.getBooleanExtra("FINES?",false);
        completeSlot = intent.getBooleanExtra("SLOT?", false);
        if(completeSlot) {
            singleEmail = intent.getStringExtra("SINGLEEMAIL");
            MainActivity.fullRef.child("CurrentSlots").child("" + slotnum).child("name").setValue("Done");
            String str = MainActivity.fullRef.child("Messages").push().getKey();
            MainActivity.fullRef.child("Messages").child(str).setValue(new Message(singleEmail, "" +Message.getCompletedFine(slotnum, false)));
        }
        if(pickUpFines)
        {
            //add the amount to the fines lists
            names = intent.getStringArrayExtra("NAMES");
            money = intent.getIntArrayExtra("MONEYSPLIT");
            emails = intent.getStringArrayExtra("EMAILS");
            for(int i = 0; i < names.length; i++)
            {
                String str = MainActivity.fullRef.child("Messages").push().getKey();
                //send messages to the database to email
                MainActivity.fullRef.child("Messages").child(str).setValue(new Message(emails[i], "" + Message.getCompletedFine(slotnum, true)));
                str = MainActivity.fullRef.child("Completed").push().getKey();
                //send to completed
                //MainActivity.fullRef.child("Completed").child(str)

                //remove fines
            }

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
