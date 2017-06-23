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
        request = intent.getIntExtra("REQUEST", 0);
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        completeAll = ((Button) findViewById (R.id.completeAllButton));
        slotOnly = ((Button) findViewById(R.id.slotOnlyButton));
        fines = true;
        if(MainActivity.dishFines.size() == 0)
        {

            completeAll.setVisibility(View.GONE);
            ((Space) findViewById(R.id.space2)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.warningText)).setVisibility(View.GONE);
            fines = false;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent ret = new Intent();
                setResult(MainActivity.OPERATION_CANCELLED, ret);
                finish();
            }
        },30000L);
    }
    public void slotOnly(View v)
    {
        Intent intent;
        if(fines)
        {//fines, handle that
            //go to split screen, remove name for the picker

        }
        else
        {//no fines. Go to complete
            if(MainActivity.passwordHolder.isProtectSlots()){
                //password protected

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
