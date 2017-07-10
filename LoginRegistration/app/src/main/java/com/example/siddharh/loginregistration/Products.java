package com.example.siddharh.loginregistration;

/**
 * Created by siddharh on 7/7/17.
 */

public class Products {
int product_id;
String products_name;
String products_type;
boolean products_availability;
String product_to_client;



    public Products(String products_name, String products_type, boolean products_availability, String product_to_client) {
   /*     this.product_id = product_id;
   */     this.products_name = products_name;
        this.products_type = products_type;
        this.products_availability = products_availability;
        this.product_to_client = product_to_client;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getProducts_type() {
        return products_type;
    }

    public void setProducts_type(String products_type) {
        this.products_type = products_type;
    }

    public boolean isProducts_availability() {
        return products_availability;
    }

    public void setProducts_availability(boolean products_availability) {
        this.products_availability = products_availability;
    }

    public String getProduct_to_client() {
        return product_to_client;
    }

    public void setProduct_to_client(String product_to_client) {
        this.product_to_client = product_to_client;
    }
}
