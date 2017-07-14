package com.invoicing.siddharth.googledriveimplementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by siddharh on 5/7/17.
 */

public class Databasehandling extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "invoicingdatabases";

    // Contacts table name
    private static final String Clientdetails = "clients";
    // products table name
    private static final String Productdetails = "products";
    private static final String TAG = "Insert query";
    // Clients Table Columns names
    private static final String KEY_ID = "email_id";
    private static final String KEY_NAME = "users_name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_NAME = "products_name";
    private static final String KEY_PRODUCT_TYPE = "product_type";
    private static final String KEY_PRODUCT_AVAILABILITY = "product_availability";
    private static final String KEY_PRODUCT_CLIENT = "product_to_Client";
    SQLiteDatabase db;

    String CREATE_CLIENT_TABLE = "CREATE TABLE " + Clientdetails + "("
            + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_PH_NO + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PRODUCT_ID + " INTEGER)";

    String CREATE_PRODUCT_TABLE = "CREATE TABLE " + Productdetails + "("
            + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_NAME + " TEXT,"
            + KEY_PRODUCT_TYPE + " TEXT," + KEY_PRODUCT_AVAILABILITY + " BOOLEAN," + KEY_PRODUCT_CLIENT + " TEXT)";

    public Databasehandling(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLIENT_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Clientdetails);
        // Create tables again
        onCreate(db);
    }

    //IT IS FOR INSERTING THE USER IN THE DATABASE
    public void addUsers(Clients user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS, user.getAddress());
        values.put(KEY_PH_NO, user.getPhone_number());
        values.put(KEY_NAME, user.getUsers_name());
        values.put(KEY_ID, user.getEmail_id());
        // Inserting Row
        db.insert(Clientdetails, null, values);
        db.close();
        // Closing database connection

    }

    public void deleteClient(String deletingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Clientdetails, KEY_ID + " = ?",
                new String[]{deletingId});
        db.close();
    }

    // TO GET A PARTICULAR USER'S DETAILS
    public Clients getContact(String emailid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Clientdetails, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_ADDRESS}, KEY_ID + "=?",
                new String[]{String.valueOf(emailid)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Clients client = new Clients(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return client;
    }


    // Updating single contact
    public int update(Clients clients, String updatingId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, clients.getUsers_name());
        values.put(KEY_PH_NO, clients.getPhone_number());
        values.put(KEY_ADDRESS, clients.getAddress());

        // updating row
        return db.update(Clientdetails, values, KEY_ID + " = ?",
                new String[]{updatingId});
    }


    // GET THE LIST OF ALL USERS MAINTAINED IN THE DATABASE
    public List<Clients> getListofallClients() {
        List<Clients> clientslist = new ArrayList<Clients>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Clientdetails;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Clients client = new Clients();
                client.setEmail_id(cursor.getString(0));
                client.setUsers_name(cursor.getString(1));
                client.setPhone_number(cursor.getString(2));
                client.setAddress(cursor.getString(3));
                client.setProduct_id(cursor.getInt(4));
                // Adding contact to list
                clientslist.add(client);
            } while (cursor.moveToNext());
        }
        // return contact list
        return clientslist;
    }


    //IT IS FOR INSERTING THE USER IN THE DATABASE
    public void addProducts(Products products) {

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, products.getProducts_name());
        values.put(KEY_PRODUCT_TYPE, products.getProducts_type());
        values.put(KEY_PRODUCT_AVAILABILITY, products.isProducts_availability());
        values.put(KEY_PRODUCT_CLIENT, products.getProduct_to_client());
        // Inserting Row
        db.insert(Productdetails, null, values);
        /*Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
        c.moveToFirst();
        int product_id = c.getInt(0);
        product_id += 1;*/
        Cursor cursor = db.rawQuery("select * from " + Productdetails, null);
        cursor.moveToLast();
        int product_id = cursor.getInt(0);
        products.setProduct_id(product_id);
        String product_to_client = cursor.getString(4);
        ContentValues valuesforclienttable = new ContentValues();
        valuesforclienttable.put(KEY_PRODUCT_ID, product_id);
        db.update(Clientdetails, valuesforclienttable, KEY_ID + " = ?",
                new String[]{product_to_client});
        db.close();
        // Closing database connection
    }

    public HashMap<String,String> getListofProductwithClient(Products products) {
        HashMap<String,String> jointList = new HashMap<String, String>();
        String selectQuery = "SELECT  " + KEY_PRODUCT_CLIENT + "," + KEY_PRODUCT_NAME +" FROM " + Productdetails + " INNER JOIN " + Clientdetails + " ON " + Productdetails + "." +"product_id" + "=" + Clientdetails +"." +"product_id";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
               jointList.put(cursor.getString(0),cursor.getString(1));
            } while (cursor.moveToNext());

        }
        return jointList;
    }
    public List<Products> searchtheproduct(String productname,String tablename){
     List<Products> jointList = new ArrayList<>();
     String selectquery = "SELECT * From "+tablename+" where "+KEY_PRODUCT_NAME+"='"+productname+"' Or "+KEY_PRODUCT_NAME+" Like '%"+productname+"%'";
     SQLiteDatabase db  = this.getWritableDatabase();
     Cursor cursor      = db.rawQuery(selectquery,null);
        if(cursor.moveToFirst()){
            do{
                Products products = new Products();
                products.setProduct_id(cursor.getInt(0));
                products.setProducts_name(cursor.getString(1));
                products.setProducts_type(cursor.getString(2));
                products.setProducts_availability((cursor.getInt(3)>0));
                products.setProduct_to_client(cursor.getString(4));
                jointList.add(products);
            }while (cursor.moveToNext());
        }
        return jointList;
    }
}