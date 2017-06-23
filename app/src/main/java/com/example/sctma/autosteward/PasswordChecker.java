package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import java.util.Timer;

public class PasswordChecker extends AppCompatActivity {

    int slotnum;
    String finePassword;
    String slotPassword;
    EditText slotPass;
    EditText finePass;
    boolean fines;
    boolean completeSlot;
    boolean override;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_checker);
        Intent intent = getIntent();
        fines = intent.getBooleanExtra("FINES?", false);
        completeSlot = intent.getBooleanExtra("SLOT?", false);
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        override = intent.getBooleanExtra("OVERRIDE", false);
        slotPass = (EditText)(findViewById(R.id.editText));
        finePass = (EditText)(findViewById(R.id.editText2));
        ((TextView)(findViewById(R.id.wrongPasscodeText))).setVisibility(View.GONE);
        if(fines)
            finePassword = getFinePassword();
        else
        {
            finePass.setVisibility(View.GONE);
            ((Space)findViewById(R.id.removeSpace1)).setVisibility(View.GONE);
        }
        if(completeSlot)
            slotPassword = getSlotPassword();
        else
        {
            slotPass.setVisibility(View.GONE);
            ((Space)findViewById(R.id.removeSpace1)).setVisibility(View.GONE);
        }

        if(!override)
            ((TextView)(findViewById(R.id.overrideText))).setVisibility(View.GONE);
        else
        {
            ((TextView)(findViewById(R.id.overrideText))).setVisibility(View.VISIBLE);
            slotPassword = MainActivity.passwordHolder.getMaster();
            String str = MainActivity.fullRef.child("Messages").push().getKey();
            MainActivity.fullRef.child("Messages").child(str).setValue(new Message(MainActivity.stewardInfo[0],"override"));
            //use this to do cloud function sending email to steward
        }


    }
    private String getFinePassword()
    {
        switch(slotnum)
        {
            case 0: return MainActivity.passwordHolder.getDishFine();
            case 1: return MainActivity.passwordHolder.getMidnightFine();
            case 2: return MainActivity.passwordHolder.getTrashFine();
            case 3: return MainActivity.passwordHolder.getKitchenFine();
        }
        return null;
    }
    private String getSlotPassword()
    {
        switch(slotnum)
        {
            case 0: return MainActivity.passwordHolder.getDishSlot();
            case 1: return MainActivity.passwordHolder.getMidnightSlot();
            case 2: return MainActivity.passwordHolder.getTrashSlot();
        }
        return null;
    }
    public void attemptSubmit(View v)
    {
        int correct = 0;
        if(fines)
        {
            //check fines passcode
            String str = finePass.getText().toString();
            if(str.equals(finePassword))
                correct++;
        }
        else
            correct++;
        if(completeSlot)
        {
            //check slot passcode
            String str = slotPass.getText().toString();
            if(str.equals(slotPassword))
                correct++;
        }
        else
            correct++;
        if(correct == 2)
        {
            //go to next thing
            Intent goToNext = new Intent(this, Complete.class);
            goToNext.putExtra("SLOTNUM", slotnum);
            goToNext.putExtra("FINES?",fines);
            goToNext.putExtra("SLOT?", completeSlot);
            goToNext.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(goToNext);
            finish();
        }
        else
            ((TextView)(findViewById(R.id.wrongPasscodeText))).setVisibility(View.VISIBLE);
    }//hit the submit button
}
