package com.mycompany.mavenproject3;

public class Customer {
    private int id;
    private String name;
    private String nohp;
    private String email;
    private String password;
    private boolean gender;

    // ✅ No-arg constructor (required by DAO)
    public Customer() {}

    // ✅ All-args constructor
    public Customer(int id, String name, String nohp, String email, String password, Boolean gender) {
        this.id = id;
        this.name = name;
        this.nohp = nohp;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    // ✅ Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getNohp() { return nohp; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean getGender() { return gender; }

    // ✅ Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setNohp(String nohp) { this.nohp = nohp; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setGender(boolean gender) { this.gender = gender; }

    // ✅ Optional helper
    public String getGenderString() {
        return gender ? "Male" : "Female";
    }

    // ✅ Alias for nohp to make DAO happy
    public String getPhone() {
        return nohp;
    }

    public void setPhone(String phone) {
        this.nohp = phone;
    }
}
