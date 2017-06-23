package com.example.sctma.autosteward;

import java.io.Serializable;

/**
 * Created by SMAYBER8 on 6/22/2017.
 */

public class Slot implements Serializable {
    private int OD;
    private int dayOfWeek;
    private String extension;
    private String name;
    private String slot;
    private String key;
    public Slot(){}
    public Slot(String key, int OD, int dayOfWeek, String extension, String name,  String slot)
    {
        this.OD = OD;
        this.dayOfWeek = dayOfWeek;
        this.extension = extension;
        this.name = name;
        this.slot = slot;
        this.key = key;
    }

    public synchronized String getKey() {
        return key;
    }

    public synchronized void setKey(String key) {
        this.key = key;
    }

    public synchronized  int getOD() {
        return OD;
    }

    public synchronized void setOD(int OD) {
        this.OD = OD;
    }

    public synchronized int getDayOfWeek() {
        return dayOfWeek;
    }

    public synchronized void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public synchronized String getExtension() {
        return extension;
    }

    public synchronized void setExtension(String extension) {
        this.extension = extension;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized String getSlot() {
        return slot;
    }

    public synchronized void setSlot(String slot) {
        this.slot = slot;
    }
}
