package com.example.sewing;

public class URL_make {

    public String URL ="http://210.119.32.187/test/";
    public String parameter;

    public URL_make(String parameter){
        this.parameter = parameter;
    }

    public String make_url(){
        return URL+parameter;
    }
}
