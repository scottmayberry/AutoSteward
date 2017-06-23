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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSlotsMissed() {
        return slotsMissed;
    }

    public void setSlotsMissed(double slotsMissed) {
        this.slotsMissed = slotsMissed;
    }
}
