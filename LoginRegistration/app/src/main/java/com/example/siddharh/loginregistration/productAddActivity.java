package com.example.siddharh.loginregistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.RasterizerSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class productAddActivity extends AppCompatActivity implements View.OnClickListener {
    String Productsname = "";
    String Products_type = "";
    Button btnaddproduct;
    boolean Products_availability = false;
    String Products_to_client = "";
    EditText editText1;
    LinearLayout ClientnamesId,Productnames ,TablelinearLayout,ProductLinearlayout;
    EditText editText2;
    RadioButton radioButtontrue, radioButtonfalse;
    private static final String TAG = "ERROR MESSAGE";
    Spinner spinner;
    ArrayList<String> Listofallclients = new ArrayList<String>();
    TableRow tableRow;
    TextView ClientName,ProductName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        btnaddproduct = (Button) findViewById(R.id.btnProductadd);
        btnaddproduct.setOnClickListener(this);
        ProductLinearlayout = (LinearLayout)findViewById(R.id.productlinearlayout);
        ClientnamesId = (LinearLayout)findViewById(R.id.ClientnamesId);
        Productnames = (LinearLayout)findViewById(R.id.Productnames);
        spinner = (Spinner)findViewById(R.id.Clientname) ;
        editText1 = (EditText) findViewById(R.id.productName);
        editText2 = (EditText) findViewById(R.id.productType);
        radioButtontrue = (RadioButton) findViewById(R.id.productAvailabilityTrue);
        TablelinearLayout= (LinearLayout)findViewById(R.id.tablelayout);
        radioButtonfalse = (RadioButton) findViewById(R.id.productAvailabilityFalse);
        Listofallclients = new HomePage().listofClientsName(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Listofallclients);
        spinner.setAdapter(adapter);
    }

    public void generateTableView(Products product){/*
      *//*  tableRow = new TableRow(this);
        tableRow.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
      *//*  ClientName = new TextView(this);
        ClientName.setPadding(5,5,5,0);
        ClientName.setText("ClientNames");
        ClientName.setTextColor(getResources().getColor(R.color.colorPrimary));
        ClientName.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
       *//* tableRow.addView(ClientName);
      *//*  ProductName= new TextView(this);
        ProductName.setPadding(5,5,5,0);
        ProductName.setText("ProductNames");
        ProductName.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
      *//*  tableRow.addView(ProductName);
      *//*  TablelinearLayout.addView(tableRow,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addDatatoList(new HomePage().getListofProductwithClient(product,this));
   */

        addDatatoList(new HomePage().getListofProductwithClient(product,this));
    }

    public void addDatatoList(HashMap<String,String> ClientProductName){
        Iterator iterator = ClientProductName.entrySet().iterator();
        while(iterator.hasNext()){
            TextView ClientName = new TextView(this);
            TextView ProductName = new TextView(this);
            Map.Entry pair =  (Map.Entry)iterator.next();
            ClientName.setText(pair.getKey().toString());
            ProductName.setText(pair.getValue().toString());
            Productnames.addView(ProductName);
            ClientnamesId.addView(ClientName);
            }
            TablelinearLayout.addView(Productnames);
            TablelinearLayout.addView(ClientnamesId);
    }
    public void submit() {
        Productsname = editText1.getText().toString().trim();
        Products_type = editText2.getText().toString().trim();
        Products_to_client = spinner.getSelectedItem().toString();
        Intent intent1 = new Intent(this, HomePage.class);
        intent1.putExtra("Productsname", Productsname);
        intent1.putExtra("Products_type", Products_type);
        intent1.putExtra("Products_availability", Products_availability);
        intent1.putExtra("Products_to_client", Products_to_client);


            Products products = new HomePage().addProductstoDatabase(intent1, this);
            Toast.makeText(this, "done inserting", Toast.LENGTH_LONG).show();
        try {
            if(products!=null) {

                ProductLinearlayout.setVisibility(View.GONE);
                generateTableView(products);

            }
            } catch (Exception e) {
            Log.d("abc",e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProductadd:
                submit();
                break;


        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.productAvailabilityTrue:
                if (checked)
                    // Available
                    Products_availability = true;
                break;
            case R.id.productAvailabilityFalse:
                if (checked)
                    // Not available
                    Products_availability = false;
                break;
        }
    }
}

