package com.example.siddharh.loginregistration;

/**
 * Created by siddharth on 5/7/17.
 */

public class Clients {
    String email_id;
    String users_name;
    String phone_number;
    String address;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    int product_id;

    public Clients(){
    }

    public Clients(String email_id, String users_name, String phone_number, String address){
    this.email_id       = email_id;
    this.users_name     = users_name;
    this.phone_number   = phone_number;
    this.address        = address;
    }

    public String getEmail_id() {
        return email_id;
    }


    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
