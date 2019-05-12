package com.example.pinpointer.Adapter;



public class HomeItems{
    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public int getImage() {
        return image;
    }

    public HomeItems(String category, int image) {
        this.category = category;
        this.image = image;
    }

    String category;
    int image;
}
