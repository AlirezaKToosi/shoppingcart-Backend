package com.novare.shoppingcart.model;

public class TodoItemDTO {

        private String title;
        private String price;
        private String imageName;

    public TodoItemDTO(String title, String price, String imageName) {
        this.title = title;
        this.price = price;
        this.imageName = imageName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImageName() {
        return imageName;
    }
}
