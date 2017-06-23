package com.example.sctma.autosteward;

/**
 * Created by SMAYBER8 on 6/22/2017.
 */

public class Brother {
    private String email;
    private String name;
    private String phone;
    private double slotsMissed;
    private int key;
    public Brother(){}

    public Brother(String email, String name, String phone, double slotsMissed, int key) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.slotsMissed = slotsMissed;
        this.key = key;
    }

    public synchronized int getKey() {
        return key;
    }

    public synchronized  void setKey(int key) {
        this.key = key;
    }

    public synchronized String getEmail() {
        return email;
    }

    public synchronized void setEmail(String email) {
        this.email = email;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized String getPhone() {
        return phone;
    }

    public synchronized void setPhone(String phone) {
        this.phone = phone;
    }

    public synchronized double getSlotsMissed() {
        return slotsMissed;
    }

    public synchronized void setSlotsMissed(double slotsMissed) {
        this.slotsMissed = slotsMissed;
    }
}
