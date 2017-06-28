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
import java.util.TimerTask;

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
    String[] names;
    String[] emails;
    String singleEmail;
    int[] money;
    int correct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_checker);
        Intent intent = getIntent();
        fines = intent.getBooleanExtra("FINES?", false);
        completeSlot = intent.getBooleanExtra("SLOT?", false);
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        override = intent.getBooleanExtra("OVERRIDE", false);
        money = intent.getIntArrayExtra("MONEYSPLIT");
        names = intent.getStringArrayExtra("NAMES");
        slotPass = (EditText)(findViewById(R.id.editText));
        finePass = (EditText)(findViewById(R.id.editText2));
        correct = 0;
        if(fines) {
            finePassword = getFinePassword();
            //send fine passwords to emails
            if (names != null) {
                emails = new String[names.length];
                for (int k = 0; k < emails.length; k++)
                    middleloop:for (int i = 0; i < MainActivity.brothers.length; i++)
                        for (int g = 0; g < MainActivity.brothers[i].size(); g++) {
                            if (MainActivity.brothers[i].get(g).getName().equals(names[k])) {
                                emails[k] = MainActivity.brothers[i].get(g).getEmail();
                                break middleloop;
                            }
                        }//search for brothers
                for (int i = 0; i < emails.length; i++) {
                    String str = MainActivity.fullRef.child("Messages").push().getKey();
                    if (!emails[i].contains("@"))
                        emails[i] = emails[i] + "@mit.edu";
                    if(MainActivity.passwordHolder.isProtectFines())
                        MainActivity.fullRef.child("Messages").child(str).setValue(new Message(emails[i], "" + Message.getReferenceForEmail(slotnum, true)));
                }
            }//if names is not null
        }
        else
        {
            finePass.setVisibility(View.GONE);
            ((Space)findViewById(R.id.removeSpace1)).setVisibility(View.GONE);
        }
        if(completeSlot) {
            slotPassword = getSlotPassword();
            //send password again through email
            singleEmail = "";
            outerloop: for(int i = 0; i < MainActivity.brothers.length;i++)
                for(int g = 0; g < MainActivity.brothers[i].size();g++)
                {
                    if(MainActivity.brothers[i].get(g).getName().equals(MainActivity.slots[slotnum].getName())) {
                        singleEmail = MainActivity.brothers[i].get(g).getEmail();
                        break outerloop;
                    }
                }//search for brothers
            String str = MainActivity.fullRef.child("Messages").push().getKey();
            if(MainActivity.passwordHolder.isProtectSlots())
                MainActivity.fullRef.child("Messages").child(str).setValue(new Message(singleEmail,"" + Message.getReferenceForEmail(slotnum, false)));
        }
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
            //use this to do cloud function sending email to steward
            String em = MainActivity.stewardInfo[0];
            if(!em.contains("@"))
                em = em + "@mit.edu";
            MainActivity.fullRef.child("Messages").child(str).setValue(new Message(em,"" + Message.getReferenceForEmailMaster()));
        }
        if(!MainActivity.passwordHolder.isProtectFines())
        {
            finePass.setVisibility(View.GONE);
            ((Space)findViewById(R.id.removeSpace1)).setVisibility(View.GONE);
        }
        if(!MainActivity.passwordHolder.isProtectSlots() && !override)
        {
            slotPass.setVisibility(View.GONE);
            ((Space)findViewById(R.id.removeSpace1)).setVisibility(View.GONE);
        }
        attemptSubmit(new View(this));
        ((TextView) (findViewById(R.id.wrongPasscodeText))).setVisibility(View.INVISIBLE);
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent result = new Intent();
                setResult(1, result);
                finish();
            }
        }, 120000L);
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
        if(fines)
        {
            //check fines passcode
            String str = finePass.getText().toString();
            if(str.equals(finePassword) || !MainActivity.passwordHolder.isProtectFines())
                correct++;
        }
        else
            correct++;
        if(completeSlot)
        {
            //check slot passcode
            String str = slotPass.getText().toString();
            if(str.equals(slotPassword) || (!MainActivity.passwordHolder.isProtectSlots() && !override))
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
            goToNext.putExtra("NAMES", names);
            goToNext.putExtra("MONEYSPLIT", money);
            goToNext.putExtra("EMAILS", emails);
            if(completeSlot)
                goToNext.putExtra("SINGLEEMAIL", singleEmail);
            goToNext.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(goToNext);
            finish();
        }
        else {
            ((TextView) (findViewById(R.id.wrongPasscodeText))).setVisibility(View.VISIBLE);
            correct = 0;
        }
    }//hit the submit button
}
