package com.example.sctma.autosteward;


public class Message {

    private String message;
    private String number;
    public Message()
    {}
    public Message(String number, String message)
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

