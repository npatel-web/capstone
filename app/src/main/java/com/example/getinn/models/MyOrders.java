package com.example.getinn.models;

import java.io.Serializable;

public class MyOrders implements Serializable {
    private int id,price,tickets,method;

    private String title,description,image,time,status,date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyOrders(int id, int price, int tickets, int method, String title, String description, String image, String time, String status, String date) {
        this.id = id;
        this.price = price;
        this.tickets = tickets;
        this.method = method;
        this.title = title;
        this.description = description;
        this.image = image;
        this.time = time;
        this.status = status;
        this.date = date;
    }
}
