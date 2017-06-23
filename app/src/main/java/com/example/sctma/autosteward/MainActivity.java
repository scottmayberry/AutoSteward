package com.example.sctma.autosteward;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.Manifest;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button dishButton;
    Button dishFineButton;
    Button midnightsButton;
    Button midnightsFineButton;
    Button trashButton;
    Button trashFineButton;
    Button kitchenFineButton;
    static ArrayList<Fine> dishFines;
    static ArrayList<Fine> midnightFines;
    static ArrayList<Fine> trashFines;
    static ArrayList<Fine> kitchenFines;
    static ArrayList<Brother>[] brothers;
    static Slot[] slots;
    static String[] stewardInfo;
    static PasswordHolder passwordHolder;
    static ArrayList<Fine> pickUp;
    //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference fullRef;

    static final int DISH_SLOT_REQUEST = 1;
    static final int DISH_FINE_REQUEST = 2;
    static final int MIDNIGHT_SLOT_REQUEST = 3;
    static final int MIDNIGHT_FINE_REQUEST = 4;
    static final int TRASH_SLOT_REQUEST = 5;
    static final int TRASH_FINE_REQUEST = 6;
    static final int KITCHEN_FINE_REQUEST = 7;

    static final int OPERATION_CANCELLED = 0;
    static final int SLOT_ONLY_RESPONSE = 1;
    static final int SLOT_AND_FINE_RESPONSE = 2;
    static final int FINE_RESPONSE = 3;



    ChildEventListener messageListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            TextMessage textMessage = new TextMessage((String)dataSnapshot.child("number").getValue(),
                    (String)dataSnapshot.child("message").getValue());


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
    ChildEventListener dishFinesListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(dishFines.size() == 0)
                updateDishesUI(true);
            Fine nFine = new Fine((String)dataSnapshot.getKey(),(String)dataSnapshot.child("name").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
            dishFines.add(nFine);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < dishFines.size();i++)
                if(dishFines.get(i).getKey().equals((String)dataSnapshot.getKey())) {
                    dishFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    dishFines.get(i).setName((String) dataSnapshot.child("name").getValue());
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = 0; i < dishFines.size();i++)
                if(dishFines.get(i).getKey().equals((String)dataSnapshot.getKey()))
                {
                    dishFines.remove(i);
                    break;
                }
            if(dishFines.size() == 0)
                updateDishesUI(false);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener midnightFinesListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(midnightFines.size() == 0)
                updateMidnightsUI(true);
            Fine nFine = new Fine((String)dataSnapshot.getKey(),(String)dataSnapshot.child("name").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
            midnightFines.add(nFine);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < midnightFines.size();i++)
                if(midnightFines.get(i).getKey().equals((String)dataSnapshot.getKey()))
                {
                    midnightFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    midnightFines.get(i).setName((String)dataSnapshot.child("name").getValue());
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = 0; i < midnightFines.size();i++)
                if(midnightFines.get(i).getKey().equals((String)dataSnapshot.getKey()))
                {
                    midnightFines.remove(i);
                    break;
                }
            if(midnightFines.size() == 0)
                updateMidnightsUI(false);

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener trashFinesListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (trashFines.size() == 0)
                updateTrashUI(true);
            Fine nFine = new Fine((String) dataSnapshot.getKey(), (String) dataSnapshot.child("name").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
            trashFines.add(nFine);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for (int i = 0; i < trashFines.size(); i++)
                if (trashFines.get(i).getKey().equals((String) dataSnapshot.getKey())) {
                    trashFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    trashFines.get(i).setName((String) dataSnapshot.child("name").getValue());
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for (int i = 0; i < trashFines.size(); i++)
                if (trashFines.get(i).getKey().equals((String) dataSnapshot.getKey())) {
                    trashFines.remove(i);
                    break;
                }
            if (trashFines.size() == 0)
                updateTrashUI(false);

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener kitchenFinesListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(kitchenFines.size() == 0)
                updateKitchenUI(true);
            Fine nFine = new Fine((String)dataSnapshot.getKey(),(String)dataSnapshot.child("name").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
            kitchenFines.add(nFine);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < kitchenFines.size();i++)
                if(kitchenFines.get(i).getKey().equals((String)dataSnapshot.getKey())) {
                    kitchenFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    kitchenFines.get(i).setName((String) dataSnapshot.child("name").getValue());
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = 0; i < kitchenFines.size();i++)
                if(kitchenFines.get(i).getKey().equals((String)dataSnapshot.getKey()))
                {
                    kitchenFines.remove(i);
                    break;
                }
            if(kitchenFines.size() == 0)
                updateKitchenUI(false);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener currentSlotsListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Slot nSlot = new Slot((String)dataSnapshot.getKey(),
                    (int) getDoubleFromDatabase(dataSnapshot.child("OD").getValue()),
                    (int) getDoubleFromDatabase(dataSnapshot.child("dayOfWeek").getValue()),
                    (String) dataSnapshot.child("extension").getValue(),
                    (String) dataSnapshot.child("name").getValue(),
                    (String) dataSnapshot.child("slot").getValue());
            slots[(int)getDoubleFromDatabase(nSlot.getKey())] = nSlot;

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            int i = (int)getDoubleFromDatabase(dataSnapshot.getKey());
            slots[i].setOD((int) getDoubleFromDatabase(dataSnapshot.child("OD").getValue()));
            slots[i].setDayOfWeek((int) getDoubleFromDatabase(dataSnapshot.child("dayOfWeek").getValue()));
            slots[i].setExtension((String) dataSnapshot.child("extension").getValue());
            slots[i].setName((String) dataSnapshot.child("name").getValue());
            slots[i].setSlot((String) dataSnapshot.child("slot").getValue());
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
    ChildEventListener stewardInfoListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            switch (dataSnapshot.getKey())
            {
                case "email": stewardInfo[0] = (String)dataSnapshot.getValue();
                    break;
                case "phone": stewardInfo[1] = (String)dataSnapshot.getValue();
                    break;
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            switch (dataSnapshot.getKey())
            {
                case "email": stewardInfo[0] = (String)dataSnapshot.getValue();
                    break;
                case "phone": stewardInfo[1] = (String)dataSnapshot.getValue();
                    break;
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            switch (dataSnapshot.getKey())
            {
                case "email": stewardInfo[0] = "";
                    break;
                case "phone": stewardInfo[1] = "";
                    break;
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener passwordListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            setPasswordInfo(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            setPasswordInfo(dataSnapshot);
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
    ChildEventListener seniorListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Brother bro = new Brother((String)dataSnapshot.child("email").getValue(),
                    (String) dataSnapshot.child("name").getValue(),
                    (String)dataSnapshot.child("phone").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()),
                    (int)getDoubleFromDatabase(dataSnapshot.getKey()));
            brothers[0].add(bro);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < brothers[0].size();i++)
                if(brothers[0].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[0].get(i).setName((String) dataSnapshot.child("name").getValue());
                    brothers[0].get(i).setEmail((String) dataSnapshot.child("email").getValue());
                    brothers[0].get(i).setPhone((String) dataSnapshot.child("phone").getValue());
                    brothers[0].get(i).setSlotsMissed(getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()));
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = brothers[0].size()-1; i >= 0;i--)
                if(brothers[0].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[0].remove(i);
                    break;
                }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener juniorListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Brother bro = new Brother((String)dataSnapshot.child("email").getValue(),
                    (String) dataSnapshot.child("name").getValue(),
                    (String)dataSnapshot.child("phone").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()),
                    (int)getDoubleFromDatabase(dataSnapshot.getKey()));
            brothers[1].add(bro);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < brothers[1].size();i++)
                if(brothers[1].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[1].get(i).setName((String) dataSnapshot.child("name").getValue());
                    brothers[1].get(i).setEmail((String) dataSnapshot.child("email").getValue());
                    brothers[1].get(i).setPhone((String) dataSnapshot.child("phone").getValue());
                    brothers[1].get(i).setSlotsMissed(getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()));
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = brothers[1].size()-1; i >= 0;i--)
                if(brothers[1].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[1].remove(i);
                    break;
                }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener sophomoreListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Brother bro = new Brother((String)dataSnapshot.child("email").getValue(),
                    (String) dataSnapshot.child("name").getValue(),
                    (String)dataSnapshot.child("phone").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()),
                    (int)getDoubleFromDatabase(dataSnapshot.getKey()));
            brothers[2].add(bro);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < brothers[2].size();i++)
                if(brothers[2].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[2].get(i).setName((String) dataSnapshot.child("name").getValue());
                    brothers[2].get(i).setEmail((String) dataSnapshot.child("email").getValue());
                    brothers[2].get(i).setPhone((String) dataSnapshot.child("phone").getValue());
                    brothers[2].get(i).setSlotsMissed(getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()));
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = brothers[2].size()-1; i >= 0;i--)
                if(brothers[2].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[2].remove(i);
                    break;
                }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener freshmenListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Brother bro = new Brother((String)dataSnapshot.child("email").getValue(),
                    (String) dataSnapshot.child("name").getValue(),
                    (String)dataSnapshot.child("phone").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()),
                    (int)getDoubleFromDatabase(dataSnapshot.getKey()));
            brothers[3].add(bro);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < brothers[3].size();i++)
                if(brothers[3].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[3].get(i).setName((String) dataSnapshot.child("name").getValue());
                    brothers[3].get(i).setEmail((String) dataSnapshot.child("email").getValue());
                    brothers[3].get(i).setPhone((String) dataSnapshot.child("phone").getValue());
                    brothers[3].get(i).setSlotsMissed(getDoubleFromDatabase(dataSnapshot.child("slotsmissed").getValue()));
                }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            for(int i = brothers[3].size()-1; i >= 0;i--)
                if(brothers[3].get(i).getKey() == (int)getDoubleFromDatabase(dataSnapshot.getKey()))
                {
                    brothers[3].remove(i);
                    break;
                }
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

        //theses are to be determined if there are fines currently out
        updateKitchenUI(false);
        updateDishesUI(false);
        updateMidnightsUI(false);
        updateTrashUI(false);

        //save all possible buttons for easy access.
        dishButton = (Button) findViewById(R.id.dishesButton);
        dishFineButton = (Button) findViewById(R.id.dishFineButton);
        midnightsButton = (Button) findViewById(R.id.midnightsButton);
        midnightsFineButton = (Button) findViewById(R.id.midnightsFineButton);
        trashButton = (Button) findViewById(R.id.trashButton);
        trashFineButton = (Button) findViewById(R.id.trashFineButton);
        kitchenFineButton = (Button) findViewById(R.id.kitchenFineButton);

        //Instantiate database reference
        fullRef = FirebaseDatabase.getInstance().getReference();

        //instantiate all data storage
        brothers = (ArrayList<Brother>[])new ArrayList[4];
        for(int i = 0; i < brothers.length;i++)
            brothers[i] = new ArrayList<Brother>();
        passwordHolder = new PasswordHolder();
        dishFines = new ArrayList<>();
        midnightFines = new ArrayList<>();
        kitchenFines = new ArrayList<>();
        trashFines = new ArrayList<>();
        slots = new Slot[3];
        stewardInfo = new String[2];
        pickUp = new ArrayList<>();

        //add listeners for new information
        //listeners for brothers
        fullRef.child("BrotherInfo").child("Seniors").addChildEventListener(seniorListener);
        fullRef.child("BrotherInfo").child("Juniors").addChildEventListener(juniorListener);
        fullRef.child("BrotherInfo").child("Sophomores").addChildEventListener(sophomoreListener);
        fullRef.child("BrotherInfo").child("Freshmen").addChildEventListener(freshmenListener);

        //listener for fines
        fullRef.child("CurrentFines").child("0").addChildEventListener(dishFinesListener);
        fullRef.child("CurrentFines").child("1").addChildEventListener(midnightFinesListener);
        fullRef.child("CurrentFines").child("2").addChildEventListener(trashFinesListener);
        fullRef.child("CurrentFines").child("3").addChildEventListener(kitchenFinesListener);

        //listener for current slots
        fullRef.child("CurrentSlots").addChildEventListener(currentSlotsListener);
        fullRef.child("StewardInfo").addChildEventListener(stewardInfoListener);

        //listener for passwords
        fullRef.child("Passwords").addChildEventListener(passwordListener);
    }
    public void completeDishes(View v) {
        Intent intent = new Intent(this, Checker.class);

        startActivityForResult(intent, DISH_SLOT_REQUEST);
    }
    public void completeDishFines(View v)
    {

    }
    public void completeMidnights(View v)
    {

    }
    public void completeMidnightFines(View v)
    {

    }
    public void completeTrash(View v)
    {

    }
    public void completeTrashFines(View v)
    {

    }
    public void completeKitchenFines(View v)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        System.out.println(resultCode);
        System.out.println(brothers);
    }

    public void setPasswordInfo(DataSnapshot dataSnapshot)
    {
        switch(dataSnapshot.getKey())
        {
            case "dishFine": passwordHolder.setDishFine((String)dataSnapshot.getValue());
                break;
            case "dishSlot": passwordHolder.setDishSlot((String)dataSnapshot.getValue());
                break;
            case "kitchenFine": passwordHolder.setKitchenFine((String)dataSnapshot.getValue());
                break;
            case "master": passwordHolder.setMaster((String)dataSnapshot.getValue());
                break;
            case "midnightFine": passwordHolder.setMidnightFine((String)dataSnapshot.getValue());
                break;
            case "midnightSlot": passwordHolder.setMidnightSlot((String)dataSnapshot.getValue());
                break;
            case "protectFines":
                if(((String)dataSnapshot.getValue()).toLowerCase().equals("yes"))
                    passwordHolder.setProtectFines(true);
                else
                    passwordHolder.setProtectFines(false);
                break;
            case "protectSlots":
                if(((String)dataSnapshot.getValue()).toLowerCase().equals("yes"))
                    passwordHolder.setProtectSlots(true);
                else
                    passwordHolder.setProtectSlots(false);
                break;
            case "trashFine": passwordHolder.setTrashFine((String)dataSnapshot.getValue());
                break;
            case "trashSlot": passwordHolder.setTrashSlot((String)dataSnapshot.getValue());
                break;
        }
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
    public double getDoubleFromDatabase(Object o)
    {
        if(o.getClass().getName().toLowerCase().equals("java.lang.long")) {
            return (new Long((Long) o).doubleValue());
        }else if(o.getClass().getName().toLowerCase().equals("java.lang.double")){
            return (double)(o);
        }
        else
            return 0;
    }
}
