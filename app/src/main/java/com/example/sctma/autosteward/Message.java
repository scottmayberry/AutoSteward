package com.example.sctma.autosteward;


public class Message {

    static final int DISH_PASSWORD = 1;
    static final int MIDNIGHT_PASSWORD = 2;
    static final int TRASH_PASSWORD = 3;
    static final int DISH_FINE_PASSWORD = 4;
    static final int MIDNIGHT_FINE_PASSWORD = 5;
    static final int TRASH_FINE_PASSWORD = 6;
    static final int KITCHEN_FINE_PASSWORD = 7;
    static final int MASTER_PASSWORD = 8;

    static final int DISH_COMPLETE = 21;
    static final int MIDNIGHT_COMPLETE = 22;
    static final int TRASH_COMPLETE = 23;
    static final int DISH_FINE_COMPLETE = 24;
    static final int MIDNIGHT_FINE_COMPLETE = 25;
    static final int TRASH_FINE_COMPLETE = 26;
    static final int KITCHEN_FINE_COMPLETE = 27;

    private String message;
    private String email;
    public Message()
    {}
    public Message(String email, String message)
    {
        this.email = email;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    static public int getReferenceForEmail(int slotnum, boolean fine) {
        if (!fine) {
            switch(slotnum)
            {
                case 0: return DISH_PASSWORD;
                case 1: return MIDNIGHT_PASSWORD;
                case 2: return TRASH_PASSWORD;
            }
        } else {
            switch (slotnum) {
                case 0: return DISH_FINE_PASSWORD;
                case 1: return MIDNIGHT_FINE_PASSWORD;
                case 2: return TRASH_FINE_PASSWORD;
                case 3: return KITCHEN_FINE_PASSWORD;
            }
        }
        return 0;
    }
    static public int getReferenceForEmailMaster()
    {
        return MASTER_PASSWORD;
    }
    static public int getCompletedFine(int slotnum, boolean fine)
    {
        if (!fine) {
            switch(slotnum)
            {
                case 0: return DISH_COMPLETE;
                case 1: return MIDNIGHT_COMPLETE;
                case 2: return TRASH_COMPLETE;
            }
        } else {
            switch (slotnum) {
                case 0: return DISH_FINE_COMPLETE;
                case 1: return MIDNIGHT_FINE_COMPLETE;
                case 2: return TRASH_FINE_COMPLETE;
                case 3: return KITCHEN_FINE_COMPLETE;
            }
        }
        return 0;
    }
}

