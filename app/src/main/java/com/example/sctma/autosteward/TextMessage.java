package com.example.sctma.autosteward;

/**
 * Created by SMAYBER8 on 6/21/2017.
 */

public class TextMessage {

    private String message;
    private String number;
    public TextMessage()
    {}
    public TextMessage(String number, String message)
    {
        this.number = number;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
