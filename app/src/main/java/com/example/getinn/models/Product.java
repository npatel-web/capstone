package com.example.getinn.models;

public class Product {
    private int p_id,category_id,p_price,total_booked,p_quantity,offer_price;
    private String p_title,p_description,p_image,pdate,ptime;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }

    public int getTotal_booked() {
        return total_booked;
    }

    public void setTotal_booked(int total_booked) {
        this.total_booked = total_booked;
    }

    public int getP_quantity() {
        return p_quantity;
    }

    public void setP_quantity(int p_quantity) {
        this.p_quantity = p_quantity;
    }

    public int getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(int offer_price) {
        this.offer_price = offer_price;
    }

    public String getP_title() {
        return p_title;
    }

    public void setP_title(String p_title) {
        this.p_title = p_title;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public Product(int p_id, int category_id, int p_price, int total_booked, int p_quantity, int offer_price, String p_title, String p_description, String p_image, String pdate, String ptime) {
        this.p_id = p_id;
        this.category_id = category_id;
        this.p_price = p_price;
        this.total_booked = total_booked;
        this.p_quantity = p_quantity;
        this.offer_price = offer_price;
        this.p_title = p_title;
        this.p_description = p_description;
        this.p_image = p_image;
        this.pdate = pdate;
        this.ptime = ptime;
    }
}

