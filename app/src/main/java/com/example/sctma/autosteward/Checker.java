package com.example.sctma.autosteward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Checker extends AppCompatActivity {

    Slot slot;
    ArrayList<Brother>[] brothers;
    PasswordHolder passwordHolder;
    ArrayList<Fine> fines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);

    }
    public void sendResultBack(View v)
    {
        Intent returnIntent = new Intent();
        setResult(2,returnIntent);
        finish();
    }
}
