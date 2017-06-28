package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Checker extends AppCompatActivity {

    int request;
    int slotnum;
    Button completeAll;
    Button slotOnly;
    boolean fines;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);
        Intent intent = getIntent();
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        completeAll = ((Button) findViewById (R.id.completeAllButton));
        slotOnly = ((Button) findViewById(R.id.slotOnlyButton));
        fines = true;
        int size = 0;
        switch(slotnum)
        {
            case 0:size = MainActivity.dishFines.size();
                break;
            case 1: size = MainActivity.midnightFines.size();
                break;
            case 2: size = MainActivity.trashFines.size();
                break;
        }
        if(size == 0)
        {

            completeAll.setVisibility(View.GONE);
            ((Space) findViewById(R.id.space2)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.warningText)).setVisibility(View.GONE);
            slotOnly.setText("Complete Slot");
            fines = false;
        }
        else
            slotOnly.setText("Complete Only Slot");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent ret = new Intent();
                setResult(MainActivity.OPERATION_CANCELLED, ret);
                finish();
            }
        },20000L);
    }
    public void completeAll(View v)
    {
        Intent intent = new Intent(this, ChooseSplit.class);
        intent.putExtra("SLOTNUM", slotnum);
        intent.putExtra("SLOT?", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }
    public void slotOnly(View v)
    {
        Intent intent;
        if(fines)
        {//fines, handle that
            //go to split screen, remove name for the picker
            intent = new Intent(this, PasswordChecker.class);
            intent.putExtra("SLOTNUM", slotnum);
            intent.putExtra("FINES?", false);
            intent.putExtra("SLOT?", true);
            intent.putExtra("OVERRIDE", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(intent);
            finish();
        }
        else
        {//no fines. Go to complete
            if(MainActivity.passwordHolder.isProtectSlots()){
                //password protected
                intent = new Intent(this, PasswordChecker.class);
                intent.putExtra("SLOTNUM", slotnum);
                intent.putExtra("FINES?", fines);
                intent.putExtra("SLOT?", true);
                intent.putExtra("OVERRIDE", false);
                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
            else
            {
                //not password protected
                intent = new Intent(this, Complete.class);
                intent.putExtra("SLOTNUM", slotnum);
                intent.putExtra("FINES?", fines);
                intent.putExtra("SLOT?", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        }
    }
}
