package com.example.orderingapp.Domain;

public class CategoryModel {
    private String title;
    private int id;

    // Default constructor
    public CategoryModel() {
        this.title = " ";
        this.id = 0;
    }

    // Constructor with parameters
    public CategoryModel(String title, int id) {
        this.title = title;
        this.id = id;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }
}
