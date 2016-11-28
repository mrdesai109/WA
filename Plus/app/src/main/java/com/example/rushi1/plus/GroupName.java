package com.example.rushi1.plus;

/**
 * Created by rushi1 on 24-09-2016.
 */
public class GroupName {

    String name,msg,keygrp;

    public GroupName(){}

   public GroupName(String name,String msg, String keygrp){

        this.keygrp=keygrp;
       this.name=name;
       this.msg=msg;
    }


    public String getName(){return name;}
    public String getKeygrp(){
        return keygrp;
    }

    public String getMsg(){return msg;}
}
