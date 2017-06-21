package com.example.sctma.autosteward;

import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button dishButton;
    Button dishFineButton;
    Button midnightsButton;
    Button midnightsFineButton;
    Button trashButton;
    Button trashFineButton;
    Button kitchenFineButton;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference fullRef;
    ChildEventListener messageListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            TextMessage textMessage = new TextMessage((String)dataSnapshot.child("number").getValue(),
                    (String)dataSnapshot.child("message").getValue());
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("+17134193527", null, textMessage.getMessage(), null, null);
            //dataSnapshot.getRef().setValue(null);


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateKitchenUI(false);
        updateDishesUI(false);
        updateMidnightsUI(false);
        updateTrashUI(false);
        dishButton = (Button) findViewById(R.id.dishesButton);
        dishFineButton = (Button) findViewById(R.id.dishFineButton);
        midnightsButton = (Button) findViewById(R.id.midnightsButton);
        midnightsFineButton = (Button) findViewById(R.id.midnightsFineButton);
        trashButton = (Button) findViewById(R.id.trashButton);
        trashFineButton = (Button) findViewById(R.id.trashFineButton);
        kitchenFineButton = (Button) findViewById(R.id.kitchenFineButton);
        fullRef = FirebaseDatabase.getInstance().getReference();
        fullRef.child("CurrentFines");
        fullRef.child("CurrentSlots");
        fullRef.child("Messages").addChildEventListener(messageListener);
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
