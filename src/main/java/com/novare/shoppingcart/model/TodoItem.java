package com.novare.shoppingcart.model;

import jakarta.persistence.*;

@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email") // Use user_email as the foreign key column
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "image")
    private Image image;


    public TodoItem(String title, double price, Image image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TodoItem() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Image getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
