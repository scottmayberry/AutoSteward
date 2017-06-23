package com.example.sctma.autosteward;

import java.io.Serializable;

/**
 * Created by SMAYBER8 on 6/22/2017.
 */

public class PasswordHolder implements Serializable {
    private boolean protectFines;
    private boolean protectSlots;
    private String dishSlot;
    private String dishFine;
    private String midnightSlot;
    private String midnightFine;
    private String trashSlot;
    private String trashFine;
    private String kitchenFine;
    private String master;

    public PasswordHolder() {
    }

    public PasswordHolder(boolean protectFines, boolean protectSlots, String dishSlot, String dishFine, String midnightSlot, String midnightFine, String trashSlot, String trashFine, String kitchenFine, String master) {
        this.protectFines = protectFines;
        this.protectSlots = protectSlots;
        this.dishSlot = dishSlot;
        this.dishFine = dishFine;
        this.midnightSlot = midnightSlot;
        this.midnightFine = midnightFine;
        this.trashSlot = trashSlot;
        this.trashFine = trashFine;
        this.kitchenFine = kitchenFine;
        this.master = master;
    }

    public synchronized  boolean isProtectFines() {
        return protectFines;
    }

    public synchronized  void setProtectFines(boolean protectFines) {
        this.protectFines = protectFines;
    }

    public synchronized boolean isProtectSlots() {
        return protectSlots;
    }

    public synchronized void setProtectSlots(boolean protectSlots) {
        this.protectSlots = protectSlots;
    }

    public synchronized String getDishSlot() {
        return dishSlot;
    }

    public synchronized void setDishSlot(String dishSlot) {
        this.dishSlot = dishSlot;
    }

    public synchronized String getDishFine() {
        return dishFine;
    }

    public synchronized void setDishFine(String dishFine) {
        this.dishFine = dishFine;
    }

    public synchronized String getMidnightSlot() {
        return midnightSlot;
    }

    public synchronized void setMidnightSlot(String midnightSlot) {
        this.midnightSlot = midnightSlot;
    }

    public String getMidnightFine() {
        return midnightFine;
    }

    public synchronized void setMidnightFine(String midnightFine) {
        this.midnightFine = midnightFine;
    }

    public synchronized String getTrashSlot() {
        return trashSlot;
    }

    public synchronized void setTrashSlot(String trashSlot) {
        this.trashSlot = trashSlot;
    }

    public synchronized String getTrashFine() {
        return trashFine;
    }

    public void setTrashFine(String trashFine) {
        this.trashFine = trashFine;
    }

    public synchronized String getKitchenFine() {
        return kitchenFine;
    }

    public synchronized void setKitchenFine(String kitchenFine) {
        this.kitchenFine = kitchenFine;
    }

    public synchronized String getMaster() {
        return master;
    }

    public synchronized void setMaster(String master) {
        this.master = master;
    }
}
