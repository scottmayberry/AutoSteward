package com.example.sctma.autosteward;

/**
 * Created by SMAYBER8 on 6/22/2017.
 */

public class Slot {
    private int OD;
    private int dayOfWeek;
    private String extension;
    private String name;
    private String password;
    private String slot;
    private String key;
    public Slot(){}
    public Slot(String key, int OD, int dayOfWeek, String extension, String name, String password, String slot)
    {
        this.OD = OD;
        this.dayOfWeek = dayOfWeek;
        this.extension = extension;
        this.name = name;
        this.password = password;
        this.slot = slot;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOD() {
        return OD;
    }

    public void setOD(int OD) {
        this.OD = OD;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
