package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SplitMoney extends AppCompatActivity {

    String[] names;
    int slotnum;
    boolean completeSlot;
    double totalMoney;
    TextView totalText;
    TextView splitText;
    LinearLayout moneyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_money);
        Intent intent = getIntent();
        int slotnum = intent.getIntExtra("SLOTNUM", 0);
        boolean completeSlot = intent.getBooleanExtra("SLOT?", false);
        ArrayList<Fine> f = getProperFines();
        totalMoney = 0;
        for(int i = 0; i < f.size();i++)
            totalMoney += f.get(i).getFine();
        splitText = (TextView)findViewById(R.id.splitText);
        totalText = (TextView)findViewById(R.id.totalText);
        moneyLayout = (LinearLayout)findViewById(R.id.moneyLayout);
        populateList();

    }
    private void populateList()
    {
        for(int i = 0; i < names.length;i++)
        {
            LinearLayout lay = new LinearLayout(this);
            lay.setOrientation(LinearLayout.HORIZONTAL);
            TextView nof = new TextView(this);
            nof.setText(names[i]);
            lay.addView(nof);
            nof.setGravity(2);
            EditText e = new EditText(this);
            e.setInputType(InputType.TYPE_CLASS_NUMBER);
            double add = totalMoney/names.length;
            e.setText("" + add);
            lay.addView(e);
            e.setGravity(1);
        }
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
