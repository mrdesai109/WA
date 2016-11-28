package com.example.rushi1.plus;

/**
 * Created by rushi1 on 24-09-2016.
 */
public class Message {

    String name,msg,key;

    public Message(){}

    public Message(String name, String msg, String key){
        this.name=name;
        this.msg=msg;
        this.key=key;
    }

    public String getName(){
        return name;
    }

    public String getMsg(){
        return msg;
    }

    public String getKey(){
        return key;
    }
}
