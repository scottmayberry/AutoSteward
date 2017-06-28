package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    TextView slotNamesText;
    TextView fineNamesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        Intent intent = getIntent();
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        pickUpFines = intent.getBooleanExtra("FINES?",false);
        completeSlot = intent.getBooleanExtra("SLOT?", false);
        slotNamesText = (TextView) findViewById(R.id.slotNamesText);
        fineNamesText = (TextView) findViewById(R.id.fineNamesText);
        if(completeSlot) {
            singleEmail = intent.getStringExtra("SINGLEEMAIL");
            MainActivity.fullRef.child("CurrentSlots").child("" + slotnum).child("name").setValue("Done");
            String str = MainActivity.fullRef.child("Messages").push().getKey();
            MainActivity.fullRef.child("Messages").child(str).setValue(new Message(singleEmail, "" +Message.getCompletedFine(slotnum, false)));
            slotNamesText.setText("Completed "+ slotnumToString() + " Slot: " + MainActivity.slots[slotnum].getName());
        }
        else
            slotNamesText.setVisibility(View.GONE);
        if(pickUpFines)
        {
            //add the amount to the fines lists
            names = intent.getStringArrayExtra("NAMES");
            money = intent.getIntArrayExtra("MONEYSPLIT");
            emails = intent.getStringArrayExtra("EMAILS");
            String text = "Completed " + slotnumToString() + " Fine:";
            for(int i = 0; i < names.length; i++)
            {
                String str = MainActivity.fullRef.child("Messages").push().getKey();
                //send messages to the database to email
                MainActivity.fullRef.child("Messages").child(str).setValue(new Message(emails[i], "" + Message.getCompletedFine(slotnum, true)));

                //send to completed
                str = MainActivity.fullRef.child("PickedUp").push().getKey();
                MainActivity.fullRef.child("PickedUp").child(str).setValue(new PickUp(names[i], slotnum, money[i]));
                text = text + " " + names[i] + " - " + money[i] + ",";

            }
            text = text.substring(0, text.length()-1);
            MainActivity.fullRef.child("CurrentFines").child("" + slotnum).removeValue();
            fineNamesText.setText(text);
        }
        else
            fineNamesText.setVisibility(View.GONE);
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent result = new Intent();
                setResult(1, result);
                finish();
            }
        }, 10000L);
    }//on create
    private String slotnumToString()
    {
        switch(slotnum)
        {
            case 0: return "Dish";
            case 1: return "Midnight";
            case 2: return "Trash";
            case 3: return "Kitchen";
        }
        return "";
    }
}
