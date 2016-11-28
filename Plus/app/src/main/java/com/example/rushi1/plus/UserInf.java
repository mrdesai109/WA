package com.example.rushi1.plus;

/**
 * Created by rushi1 on 24-09-2016.
 */
public class UserInf {
    String dispname,email,mobile,region,password;

    public UserInf(){}

    public UserInf(String dispname,String email,String mobile,String region,String password){
        this.dispname=dispname;
        this.email=email;
        this.mobile=mobile;
        this.region=region;
        this.password=password;
    }

    public String getDispname(){
        return dispname;
    }

    public String getEmail(){
        return email;
    }
    public String getMobile(){
        return mobile;
    }

    public String getRegion(){
        return region;
    }

    public String getPassword(){
        return password;
    }
}
