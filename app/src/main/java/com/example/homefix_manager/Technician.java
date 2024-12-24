package com.example.homefix_manager;

import java.io.Serializable;

public class Technician  implements Serializable {
    private int id;
    private String name;
    private int age;
    private int experience;
    private String phone;
    private String address;
    private boolean available;
    private String category;


    public Technician(String name, int age, int experience, String phone, String address, boolean available, String category) {
        this.name = name;
        this.age = age;
        this.experience = experience;
        this.phone = phone;
        this.address = address;
        this.available = available;
        this.category = category;
    }

    public Technician(int id, String name, int age, int experience, String phone, String address, boolean available, String category) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.experience = experience;
        this.phone = phone;
        this.address = address;
        this.available = available;
        this.category = category;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getCategory() {
        return category;
    }
}
