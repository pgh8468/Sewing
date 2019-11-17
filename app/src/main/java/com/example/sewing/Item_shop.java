package com.example.sewing;

public class Item_shop {

    private int img_shop;
    private String Shop_url;

    public Item_shop() {

    }

    public Item_shop(int img_shop, String shop_url) {
        this.img_shop = img_shop;
        Shop_url = shop_url;
    }

    //Getter
    public int getImg_shop() {
        return img_shop;
    }

    public String getShop_url() {
        return Shop_url;
    }

    //Setter
    public void setImg_shop(int img_shop) {
        this.img_shop = img_shop;
    }

    public void setShop_url(String shop_url) {
        Shop_url = shop_url;
    }
}
