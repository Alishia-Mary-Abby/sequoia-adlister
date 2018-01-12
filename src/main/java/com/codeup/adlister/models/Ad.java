package com.codeup.adlister.models;

import java.util.List;

public class Ad {
    private long id;
    private long userId;
    private String imgName;
    private String title;
    private String description;
    private long price;
    private User user;
    private List<Category> categories;

    public Ad(long id, long userId, String imgName, String title, String description, long price, User user, List<Category> categories) {
        this.id = id;
        this.userId = userId;
        this.imgName = imgName;
        this.title = title;
        this.description = description;
        this.price = price;
        this.user = user;
        this.categories = categories;
    }

    public Ad(long id, String username, String title, String description, long price) {
        this.userId = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Ad(long id, long userId, String imgName, String title, String description, long price) {
        this.id = id;
        this.userId = userId;
        this.imgName = imgName;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Ad(long id, User user, String imgName, String title, String description, long price) {
        this.id = id;
        this.user = user;
        this.imgName = imgName;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
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

    public long getPrice() { return price; }

    public void setPrice(long price) {
        this.price = price;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
