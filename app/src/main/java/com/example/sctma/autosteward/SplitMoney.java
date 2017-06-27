package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;

public class SplitMoney extends AppCompatActivity {

    String[] names;
    int slotnum;
    boolean completeSlot;
    int totalMoney;
    static int currentMoney;
    int[] money;
    TextView totalText;
    static TextView splitText;
    LinearLayout moneyLayout;
    EditTextMoneyListener[] editTextMoneyListeners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_money);
        Intent intent = getIntent();
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        completeSlot = intent.getBooleanExtra("SLOT?", false);
        names = intent.getStringArrayExtra("NAMES");
        money = new int[names.length];
        ArrayList<Fine> f = getProperFines();
        totalMoney = 0;
        for(int i = 0; i < f.size();i++)
            totalMoney += (int)f.get(i).getFine();
        splitMoneyEqually();
        currentMoney = totalMoney;
        splitText = (TextView)findViewById(R.id.splitText);
        totalText = (TextView)findViewById(R.id.totalText);
        totalText.setText("Text to Split: " + totalMoney);
        splitText.setText("Total: " + totalMoney);
        moneyLayout = (LinearLayout)findViewById(R.id.moneyLayout);
        populateList();

    }
    private void splitMoneyEqually()
    {
        int split = (int)Math.round(totalMoney/names.length);
        int adjuster = split;
        if(split * money.length != totalMoney) {
            adjuster += totalMoney - split * money.length;
            adjuster = Math.round(adjuster);
        }
        for(int i = 0; i < money.length-1;i++)
        {
            money[i] = split;
        }
        money[money.length-1] = adjuster;
    }
    public void completeButtonPressed(View v)
    {
        if(currentMoney != totalMoney)
            return;
        else
        {
            Intent intent = new Intent(this, PasswordChecker.class);
            for(int i = 0; i < money.length;i++)
                money[i] = editTextMoneyListeners[i].getMoney();

            intent.putExtra("SLOTNUM", slotnum);
            intent.putExtra("FINES?", true);
            intent.putExtra("SLOT?", completeSlot);
            intent.putExtra("NAMES", names);
            intent.putExtra("MONEYSPLIT", money);
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            startActivity(intent);
            finish();

        }


    }
    private void populateList()
    {
        editTextMoneyListeners = new EditTextMoneyListener[money.length];
        for(int i = 0; i < names.length;i++)
        {
            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.HORIZONTAL);
            TextView nof = new TextView(this);
            nof.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nof.setTextSize(30);
            nof.setText(names[i]);
            lay.addView(nof);
            nof.setGravity(2);
            Space space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            lay.addView(space);
            EditText e = new EditText(this);
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
            double add = totalMoney/names.length;
            e.setText("" + (int)money[i]);
            editTextMoneyListeners[i] = new EditTextMoneyListener((int) money[i]);
            e.addTextChangedListener(editTextMoneyListeners[i]);
            e.setTextSize(30);
            lay.addView(e);
            e.setGravity(1);
            moneyLayout.addView(lay);
        }

    }
    static void updateSplitText(int dif)
    {
        currentMoney += dif;
        splitText.setText("Total: " + currentMoney);
    }
    public ArrayList<Fine> getProperFines()
    {
        switch(slotnum)
        {
            case 0: return MainActivity.dishFines;
            case 1: return MainActivity.midnightFines;
            case 2: return MainActivity.trashFines;
            case 3: return MainActivity.kitchenFines;
        }
        return null;
    }
}
