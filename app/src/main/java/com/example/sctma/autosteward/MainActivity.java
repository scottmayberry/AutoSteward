package com.example.sctma.autosteward;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button dishButton;
    Button dishFineButton;
    Button midnightsButton;
    Button midnightsFineButton;
    Button trashButton;
    Button trashFineButton;
    Button kitchenFineButton;

    TextView dishText;
    TextView dishFineIntro;
    TextView currentDishFineNames;
    TextView midnightText;
    TextView midnightFineIntro;
    TextView currentMidnightFineNames;
    TextView trashText;
    TextView trashFineIntro;
    TextView currentTrashFineNames;
    TextView kitchenFineIntro;
    TextView currentKitchenFineNames;

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
    static DatabaseReference fullRef;

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



    ChildEventListener dishFinesListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if(dishFines.size() == 0)
                updateDishesUI(true);
            Fine nFine = new Fine((String)dataSnapshot.getKey(),(String)dataSnapshot.child("name").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
            dishFines.add(nFine);
            updateDishFineText();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < dishFines.size();i++)
                if(dishFines.get(i).getKey().equals((String)dataSnapshot.getKey())) {
                    dishFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    dishFines.get(i).setName((String) dataSnapshot.child("name").getValue());
                }
            updateDishFineText();
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
            updateDishFineText();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        private void updateDishFineText()
        {
            if(dishFines.size() == 0)
            {
                dishFineIntro.setText("Dish Fine: ");
                currentDishFineNames.setText("None");
                return;
            }
            String text = "";
            int sum = 0;
            for(int i = 0; i < dishFines.size()-1; i++)
            {
                text = text + dishFines.get(i).getName() + " - " + dishFines.get(i).getFine() + ", ";
                sum = sum + (int)dishFines.get(i).getFine();
            }//dish fines size
            text = text + dishFines.get(dishFines.size()-1).getName() + " - " + dishFines.get(dishFines.size()-1).getFine();
            sum = sum + (int)dishFines.get(dishFines.size()-1).getFine();
            dishFineIntro.setText("Dish Fine: " + sum);
            currentDishFineNames.setText("   " + text);
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
            updateMidnightFineText();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < midnightFines.size();i++)
                if(midnightFines.get(i).getKey().equals((String)dataSnapshot.getKey()))
                {
                    midnightFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    midnightFines.get(i).setName((String)dataSnapshot.child("name").getValue());
                }
            updateMidnightFineText();
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
            updateMidnightFineText();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        private void updateMidnightFineText()
        {
            if(midnightFines.size() == 0)
            {
                midnightFineIntro.setText("Midnights Fine: ");
                currentMidnightFineNames.setText("None");
                return;
            }
            String text = "";
            int sum = 0;
            for(int i = 0; i < midnightFines.size()-1; i++)
            {
                text = text + midnightFines.get(i).getName() + " - " + midnightFines.get(i).getFine() + ", ";
                sum = sum + (int)midnightFines.get(i).getFine();
            }//midnight fines size
            text = text + midnightFines.get(midnightFines.size()-1).getName() + " - " + midnightFines.get(midnightFines.size()-1).getFine();
            sum = sum + (int)midnightFines.get(midnightFines.size()-1).getFine();
            midnightFineIntro.setText("Midnights Fine: " + sum);
            currentMidnightFineNames.setText("   " + text);
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
            updateTrashFineText();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for (int i = 0; i < trashFines.size(); i++)
                if (trashFines.get(i).getKey().equals((String) dataSnapshot.getKey())) {
                    trashFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    trashFines.get(i).setName((String) dataSnapshot.child("name").getValue());
                }
            updateTrashFineText();
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
            updateTrashFineText();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

        private void updateTrashFineText()
        {
            if(trashFines.size() == 0)
            {
                trashFineIntro.setText("Trash Fine: ");
                currentTrashFineNames.setText("None");
                return;
            }
            String text = "";
            int sum = 0;
            for(int i = 0; i < trashFines.size()-1; i++)
            {
                text = text + trashFines.get(i).getName() + " - " + trashFines.get(i).getFine() + ", ";
                sum = sum + (int)trashFines.get(i).getFine();
            }//trash fines size
            text = text + trashFines.get(trashFines.size()-1).getName() + " - " + trashFines.get(trashFines.size()-1).getFine();
            sum = sum + (int)trashFines.get(trashFines.size()-1).getFine();
            trashFineIntro.setText("Trash Fine: " + sum);
            currentTrashFineNames.setText("   " + text);
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
            updateKitchenFineText();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            for(int i = 0; i < kitchenFines.size();i++)
                if(kitchenFines.get(i).getKey().equals((String)dataSnapshot.getKey())) {
                    kitchenFines.get(i).setFine(getDoubleFromDatabase(dataSnapshot.child("fine").getValue()));
                    kitchenFines.get(i).setName((String) dataSnapshot.child("name").getValue());
                }
            updateKitchenFineText();
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
            updateKitchenFineText();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
        private void updateKitchenFineText()
        {
            if(kitchenFines.size() == 0)
            {
                kitchenFineIntro.setText("Kitchen Fine: ");
                currentKitchenFineNames.setText("None");
                return;
            }
            String text = "";
            int sum = 0;
            for(int i = 0; i < kitchenFines.size()-1; i++)
            {
                text = text + kitchenFines.get(i).getName() + " - " + kitchenFines.get(i).getFine() + ", ";
                sum = sum + (int)kitchenFines.get(i).getFine();
            }//kitchen fines size
            text = text + kitchenFines.get(kitchenFines.size()-1).getName() + " - " + kitchenFines.get(kitchenFines.size()-1).getFine();
            sum = sum + (int)kitchenFines.get(kitchenFines.size()-1).getFine();
            kitchenFineIntro.setText("Kitchen Fine: " + sum);
            currentKitchenFineNames.setText("   " + text);
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
            int i = Integer.parseInt(dataSnapshot.getKey());
            boolean turnOn = true;
            if(nSlot.getName().equals("Done") || nSlot.getName().equals("Steward"))
                turnOn = false;
            switch (i) {
                case 0: dishButton.setEnabled(turnOn);
                    dishText.setText(nSlot.getSlot() + ": " + nSlot.getName());

                    break;
                case 1: midnightsButton.setEnabled(turnOn);
                    midnightText.setText(nSlot.getSlot() + ": " + nSlot.getName());
                    break;
                case 2: trashButton.setEnabled(turnOn);
                    trashText.setText(nSlot.getSlot() + ": " + nSlot.getName());
            }
            slots[i] = nSlot;
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            int i = Integer.parseInt(dataSnapshot.getKey());
            slots[i].setOD((int) getDoubleFromDatabase(dataSnapshot.child("OD").getValue()));
            slots[i].setDayOfWeek((int) getDoubleFromDatabase(dataSnapshot.child("dayOfWeek").getValue()));
            slots[i].setExtension((String) dataSnapshot.child("extension").getValue());
            slots[i].setName((String) dataSnapshot.child("name").getValue());
            slots[i].setSlot((String) dataSnapshot.child("slot").getValue());
            boolean turnOn = true;
            if(slots[i].getName().equals("Done") || slots[i].getName().equals("Steward"))
                turnOn = false;
            switch (i) {
                case 0: dishButton.setEnabled(turnOn);
                    dishText.setText(slots[i].getSlot() + ": " + slots[i].getName());
                    break;
                case 1: midnightsButton.setEnabled(turnOn);
                    midnightText.setText(slots[i].getSlot() + ": " + slots[i].getName());
                    break;
                case 2: trashButton.setEnabled(turnOn);
                    trashText.setText(slots[i].getSlot() + ": " + slots[i].getName());
            }
        }//on child changed

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

        //disable buttons before load
        dishButton.setEnabled(false);
        midnightsButton.setEnabled(false);
        trashButton.setEnabled(false);

        //save all textviews for ease of access
        dishText = (TextView)findViewById(R.id.dishesText);
        dishFineIntro = (TextView)findViewById(R.id.dishFineCurrentOnlyText);
        currentDishFineNames = (TextView) findViewById(R.id.currentDishFineNamesText);
        midnightText = (TextView)findViewById(R.id.midnightsText);
        midnightFineIntro = (TextView)findViewById(R.id.midnightsFineCurrentOnlyText);
        currentMidnightFineNames = (TextView)findViewById(R.id.currentMidnightsFineNamesText);
        trashText = (TextView)findViewById(R.id.trashText);
        trashFineIntro = (TextView)findViewById(R.id.trashFineCurrentOnlyText);
        currentTrashFineNames = (TextView)findViewById(R.id.currentTrashFineNamesText);
        kitchenFineIntro = (TextView)findViewById(R.id.kitchenFineCurrentOnlyText);
        currentKitchenFineNames = (TextView) findViewById(R.id.currentKitchenFineNamesText);

        //set fines to bold
        dishFineIntro.setTypeface(null, Typeface.BOLD);
        midnightFineIntro.setTypeface(null, Typeface.BOLD);
        trashFineIntro.setTypeface(null, Typeface.BOLD);
        kitchenFineIntro.setTypeface(null, Typeface.BOLD);

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
        intent.putExtra("REQUEST", DISH_SLOT_REQUEST);
        intent.putExtra("SLOTNUM", 0);
        startActivityForResult(intent, DISH_SLOT_REQUEST);
    }
    public void completeDishFines(View v)
    {
        Intent intent = new Intent(this, ChooseSplit.class);
        intent.putExtra("REQUEST", DISH_FINE_REQUEST);
        intent.putExtra("SLOTNUM", 0);
        startActivityForResult(intent, DISH_FINE_REQUEST);
    }
    public void completeMidnights(View v)
    {
        Intent intent = new Intent(this, Checker.class);
        intent.putExtra("REQUEST", MIDNIGHT_SLOT_REQUEST);
        intent.putExtra("SLOTNUM", 1);
        startActivityForResult(intent, MIDNIGHT_SLOT_REQUEST);
    }
    public void completeMidnightFines(View v)
    {
        Intent intent = new Intent(this, ChooseSplit.class);
        intent.putExtra("REQUEST", MIDNIGHT_FINE_REQUEST);
        intent.putExtra("SLOTNUM", 1);
        startActivityForResult(intent, MIDNIGHT_FINE_REQUEST);
    }
    public void completeTrash(View v)
    {
        Intent intent = new Intent(this, Checker.class);
        intent.putExtra("REQUEST", TRASH_SLOT_REQUEST);
        intent.putExtra("SLOTNUM", 2);
        startActivityForResult(intent, TRASH_SLOT_REQUEST);
    }
    public void completeTrashFines(View v)
    {
        Intent intent = new Intent(this, ChooseSplit.class);
        intent.putExtra("REQUEST", TRASH_FINE_REQUEST);
        intent.putExtra("SLOTNUM", 2);
        startActivityForResult(intent, TRASH_FINE_REQUEST);
    }
    public void completeKitchenFines(View v)
    {
        Intent intent = new Intent(this, ChooseSplit.class);
        intent.putExtra("REQUEST", KITCHEN_FINE_REQUEST);
        intent.putExtra("SLOTNUM", 3);
        startActivityForResult(intent, KITCHEN_FINE_REQUEST);
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
        if(onOff) {
            vis = View.VISIBLE;
        }
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
