package com.example.sctma.autosteward;

/**
 * Created by SMAYBER8 on 6/27/2017.
 */

public class PickUp {
    private String name;
    private int slot;
    private int fine;

    public PickUp() {
    }

    public PickUp(String name, int slot, int fine) {
        this.name = name;
        this.slot = slot;
        this.fine = fine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
