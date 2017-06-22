package com.example.sctma.autosteward;

/**
 * Created by SMAYBER8 on 6/22/2017.
 */

public class Fine {
    private String key;

    private String name;
    private double fine;
    public Fine(){}
    public Fine(String key, String name, double fine)
    {
        this.key = key;
        this.name = name;
        this.fine = fine;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }


}
