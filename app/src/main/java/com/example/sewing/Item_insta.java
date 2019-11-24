package com.example.sewing;

public class Item_insta {

    private String Id;
    private String Follow;
    private String Profile;

    public Item_insta(String id, String follow, String profile) {
        this.Id = id;
        this.Follow = follow;
        this.Profile = profile;
    }

    //Getter

    public String getId() {
        return Id;
    }

    public String getFollow() {
        return Follow;
    }

    public String getProfile() {
        return Profile;
    }

    //Setter

    public void setId(String id) {
        Id = id;
    }

    public void setFollow(String follow) {
        Follow = follow;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }
}
