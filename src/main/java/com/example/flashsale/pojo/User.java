package com.example.flashsale.pojo;

public class User {

    private int u_id; // User Id
    private String u_name; // User Name
    private String phone; // User phone_number
    private String password; // User password
    private String email; // Email
    private Double wallet; // Owned money

    public User() {
    }

    public User(String u_name, String phone, String password, String email, Double wallet) {
        this.u_name = u_name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.wallet = wallet;
    }

    public int getUid() {
        return u_id;
    }

    public void setUid(int u_id) {
        this.u_id = u_id;
    }

    public String getUname() {
        return u_name;
    }

    public void setUname(String u_name) {
        this.u_name = u_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", u_name='" + u_name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", wallet=" + wallet +
                '}';
    }
}
