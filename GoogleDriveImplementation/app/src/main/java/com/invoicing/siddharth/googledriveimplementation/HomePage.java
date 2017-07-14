package com.invoicing.siddharth.googledriveimplementation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage  {
    String username = "";
    String email_id = "";
    String phone_no = "";
    String address = "";
    String Productsname = "";
    String Products_type = "";
    boolean Products_availability = false;
    String Products_to_client = "";
    ArrayList<String> ClientEmails =  new ArrayList<String>();
    ArrayList<Clients> Usersname   =  new ArrayList<Clients>();
    public void addUsertoDatabase(Intent intent,Context context) {
        username = intent.getStringExtra("username");
        email_id = intent.getStringExtra("email_id");
        phone_no = intent.getStringExtra("phone_no");
        address  = intent.getStringExtra("address");
        Clients user = new Clients(email_id, username, phone_no, address);
        //now pass this user to database's method
        new Databasehandling(context).addUsers(user);
    }

    public void deletefromDatabase(Context context,String deletingId){
        new Databasehandling(context).deleteClient(deletingId);
    }

    public ArrayList<Clients> listofClients(Context context){
      List<Clients> User = new Databasehandling(context).getListofallClients();
        for (Clients user:
             User) {
         Usersname.add(user);
        }
        return Usersname;
    }
    public ArrayList<String>  listofClientsName(Context context){
        List<Clients> client = new Databasehandling(context).getListofallClients();
        for (Clients user:
                client) {
            ClientEmails.add(user.getEmail_id());
        }
        return ClientEmails;
    }


    public void updateClientDetails(Clients client,Context context,String updatingId){
        new Databasehandling(context).update(client,updatingId);
    }
    public Products addProductstoDatabase(Intent intent,Context context){
        Productsname = intent.getStringExtra("Productsname");
        Products_type = intent.getStringExtra("Products_type");
        Products_availability = intent.getBooleanExtra("Products_availability",false);
        Products_to_client  = intent.getStringExtra("Products_to_client");
        Products products = new Products(Productsname,Products_type,Products_availability,Products_to_client);
        new Databasehandling(context).addProducts(products);
        return products;
        }

    public HashMap<String, String> getListofProductwithClient(Products products,Context context) {
        HashMap<String,String> jointList =  new HashMap<String, String>();
        if (products != null) {
            jointList =  new Databasehandling(context).getListofProductwithClient(products);
            return jointList;
        }
        else{
            Toast.makeText(context, "No Products available", Toast.LENGTH_SHORT).show();
        }
            return null;
    }

}
