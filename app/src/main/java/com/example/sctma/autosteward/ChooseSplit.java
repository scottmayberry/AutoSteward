package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.ArrayList;

public class ChooseSplit extends AppCompatActivity {

    LinearLayout classes[];
    int slotnum;
    boolean completeSlot;
    String name;
    int total;
    ArrayList<String> names;
    TextView namesText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_split);
        Intent intent = getIntent();
        slotnum = intent.getIntExtra("SLOTNUM", 0);
        completeSlot = intent.getBooleanExtra("SLOT?", false);
        names = new ArrayList<>();
        name = "";
        if(completeSlot) {
            name = MainActivity.slots[slotnum].getName();
            total = 1;
            names.add(name);
        }
        namesText = (TextView)findViewById(R.id.namesText);
        updateNamesText();
        classes = new LinearLayout[4];
        classes[0] = (LinearLayout)findViewById(R.id.seniorLayout);
        classes[1] = (LinearLayout)findViewById(R.id.juniorLayout);
        classes[2] = (LinearLayout)findViewById(R.id.sophomoreLayout);
        classes[3] = (LinearLayout)findViewById(R.id.freshmenLayout);
        populateClasses();
    }
    private void populateClasses()
    {
        for(int i = 0; i < MainActivity.brothers.length;i++)
            for(int g = 0; g < MainActivity.brothers[i].size();g++)
            {
                if(!MainActivity.brothers[i].get(g).getName().equals(name)) {
                    ToggleButton tb = new ToggleButton(this);
                    tb.setTextOff(MainActivity.brothers[i].get(g).getName());
                    tb.setTextOn(MainActivity.brothers[i].get(g).getName());
                    tb.setChecked(false);
                    tb.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tb.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            if(((ToggleButton)(v)).isChecked())
                            {
                                names.add(((ToggleButton)(v)).getTextOn().toString());
                            }
                            else
                                names.remove(((ToggleButton)(v)).getTextOn().toString());
                            updateNamesText();
                        }
                    });
                    classes[i].addView(tb);
                }//dont add the name of the brother who is doing the dish slot
            }
    }
    private void updateNamesText()
    {
        String str = "";
        if(names.size() == 0) {
            namesText.setText("Choose Brothers");
            return;
        }
        for(int i = 0; i < names.size()-1;i++)
        {
            str = str + names.get(i) + ", ";
        }
        str = str + names.get(names.size()-1);
        namesText.setText(str);
    }
    public void nextButtonClicked(View v)
    {
        if(names.size()==0)
            return;
        String n[] = new String[names.size()];
        for(int i = 0; i < names.size();i++)
            n[i] = names.get(i);
        Intent intent = new Intent(this,SplitMoney.class);
        intent.putExtra("SLOTNUM", slotnum);
        intent.putExtra("FINES?", true);
        intent.putExtra("SLOT?", completeSlot);
        intent.putExtra("NAMES", n);
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();

    }
}
