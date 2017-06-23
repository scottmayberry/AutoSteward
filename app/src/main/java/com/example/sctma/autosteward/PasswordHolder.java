package com.example.sctma.autosteward;

/**
 * Created by SMAYBER8 on 6/22/2017.
 */

public class PasswordHolder {
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

    public boolean isProtectFines() {
        return protectFines;
    }

    public void setProtectFines(boolean protectFines) {
        this.protectFines = protectFines;
    }

    public boolean isProtectSlots() {
        return protectSlots;
    }

    public void setProtectSlots(boolean protectSlots) {
        this.protectSlots = protectSlots;
    }

    public String getDishSlot() {
        return dishSlot;
    }

    public void setDishSlot(String dishSlot) {
        this.dishSlot = dishSlot;
    }

    public String getDishFine() {
        return dishFine;
    }

    public void setDishFine(String dishFine) {
        this.dishFine = dishFine;
    }

    public String getMidnightSlot() {
        return midnightSlot;
    }

    public void setMidnightSlot(String midnightSlot) {
        this.midnightSlot = midnightSlot;
    }

    public String getMidnightFine() {
        return midnightFine;
    }

    public void setMidnightFine(String midnightFine) {
        this.midnightFine = midnightFine;
    }

    public String getTrashSlot() {
        return trashSlot;
    }

    public void setTrashSlot(String trashSlot) {
        this.trashSlot = trashSlot;
    }

    public String getTrashFine() {
        return trashFine;
    }

    public void setTrashFine(String trashFine) {
        this.trashFine = trashFine;
    }

    public String getKitchenFine() {
        return kitchenFine;
    }

    public void setKitchenFine(String kitchenFine) {
        this.kitchenFine = kitchenFine;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
