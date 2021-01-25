package com.example.getinn.models;

public class Category {
    int category_id;
    String cat_title,cat_description,cat_image;

    public Category(int category_id,
                    String cat_title, String cat_description, String cat_image) {
        this.category_id = category_id;
        this.cat_title = cat_title;
        this.cat_description = cat_description;
        this.cat_image = cat_image;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }

    public String getCat_description() {
        return cat_description;
    }

    public void setCat_description(String cat_description) {
        this.cat_description = cat_description;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }
}
