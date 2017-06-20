package com.example.sctma.autosteward;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;

public class MainActivity extends AppCompatActivity {

    Button dishButton;
    Button dishFineButton;
    Button midnightsButton;
    Button midnightsFineButton;
    Button trashButton;
    Button trashFineButton;
    Button kitchenFineButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateKitchenUI(false);
        updateDishesUI(false);
        updateMidnightsUI(false);
        updateTrashUI(false);
        dishButton = (Button)findViewById(R.id.dishesButton);
        dishFineButton = (Button)findViewById(R.id.dishFineButton);
        midnightsButton = (Button)findViewById(R.id.midnightsButton);
        midnightsFineButton = (Button)findViewById(R.id.midnightsFineButton);
        trashButton = (Button)findViewById(R.id.trashButton);
        trashFineButton = (Button)findViewById(R.id.trashFineButton);
        kitchenFineButton = (Button)findViewById(R.id.kitchenFineButton);

    }
    protected void updateKitchenUI(boolean onOff)
    {
        int vis;
        if(onOff)
            vis = View.VISIBLE;
        else
            vis = View.GONE;
        ((Space)findViewById(R.id.kitchenFineSpace)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.kitchenFineButtonLayout)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.kitchenFineTextLayout)).setVisibility(vis);
    }
    protected void updateDishesUI(boolean onOff)
    {
        int vis;
        if(onOff)
            vis = View.VISIBLE;
        else
            vis = View.GONE;
        ((Space)findViewById(R.id.dishFineSpace)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.dishFineButtonLayout)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.dishFineTextLayout)).setVisibility(vis);
    }
    protected void updateTrashUI(boolean onOff)
    {
        int vis;
        if(onOff)
            vis = View.VISIBLE;
        else
            vis = View.GONE;
        ((Space)findViewById(R.id.trashFineSpace)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.trashFineButtonLayout)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.trashFineTextLayout)).setVisibility(vis);
    }
    protected void updateMidnightsUI(boolean onOff)
    {
        int vis;
        if(onOff)
            vis = View.VISIBLE;
        else
            vis = View.GONE;
        ((Space)findViewById(R.id.midnightsFineSpace)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.midnightsFineButtonLayout)).setVisibility(vis);
        ((LinearLayout)findViewById(R.id.midnightsFineTextLayout)).setVisibility(vis);
    }
}
