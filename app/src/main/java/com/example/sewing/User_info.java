package com.example.sewing;

public class User_info {

    private String ID;
    private String pw;
    private String email;

    public User_info(String ID, String pw, String email){
        this.ID = ID;
        this.pw = pw;
        this.email = email;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
