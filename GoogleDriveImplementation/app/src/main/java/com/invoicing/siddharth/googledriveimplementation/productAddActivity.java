package com.invoicing.siddharth.googledriveimplementation;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class productAddActivity extends Activity implements View.OnClickListener {
    String Productsname = "";
    String Products_type = "";
    Button btnaddproduct;
    boolean Products_availability = false;
    String Products_to_client = "";
    EditText editText1;
    private FirebaseAnalytics mFirebaseAnalytics;
    private GoogleAnalytics googleAnalytics;
    LinearLayout ClientnamesId,Productnames ,TablelinearLayout,ProductLinearlayout;
    EditText editText2;
    ArrayAdapter<String> adapter ;
    RadioButton radioButtontrue, radioButtonfalse;
    private static final String TAG = "ERROR MESSAGE";
    Spinner spinner;
    ArrayList<String> Listofallclients = new ArrayList<String>();
    TableRow tableRow;
    TextView ClientName,ProductName;
    List<Products> arraylist =new ArrayList<Products>();
    SearchResults searchresults = new SearchResults();
    List<String>  optionselected ;
    ArrayAdapter<String> adapterforlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        optionselected = new ArrayList<String>();
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
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Listofallclients);
        spinner.setAdapter(adapter);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

  //  public void generateTableView(Products product){
      /*//*  tableRow = new TableRow(this);
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

  /*      addDatatoList(new HomePage().getListofProductwithClient(product,this));

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

    }*/
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

                // ProductLinearlayout.setVisibility(View.GONE);
                //generateTableView(products);
                startActivity(new Intent(this,MainActivity.class));
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Productsname);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, Products_type);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                AnalyticsApplication application = (AnalyticsApplication)this.getApplicationContext();
                Tracker mTracker = application.getDefaultTracker();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("app.and.vendor")
                        .setAction("show-detail")
                        .setLabel("productAdd")
                        .build());
            }
            } catch (Exception e) {
            Log.d("abc",e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void onSearchbuttonclick(View view){
        spinner.setAdapter(adapter);

        onSearchRequested();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setVisibility(View.GONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
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
    @Override
    protected void onNewIntent(final Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        String table = "products";
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            arraylist = searchresults.Searchresult(this, query, table);
            if (arraylist != null && arraylist.size() > 0) {
                    CustomizedListAdapter customizedListAdapter;
                    Iterator<Products> listofproductname = arraylist.iterator();
                    Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.listlayout);
             //     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            /*      LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = li.inflate(R.layout.listlayout, null, false);
            */      final ListView list1 = (ListView) dialog.findViewById(R.id.listinlayout);
                    customizedListAdapter = new CustomizedListAdapter(this,R.layout.activity_result,arraylist);
                    list1.setAdapter(customizedListAdapter);
                    customizedListAdapter.setNotifyOnChange(true);
                    list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    editText1.setText(arraylist.get(position).getProducts_name());
                    editText2.setText(arraylist.get(position).getProducts_type());
                    if(arraylist.get(position).isProducts_availability()){
                            radioButtontrue.setChecked(true);
                        }else {
                            radioButtonfalse.setChecked(false);
                        }
                        optionselected.add(arraylist.get(position).getProduct_to_client());
                       // spinner.se/*(arraylist.get(position).getProduct_to_client());
                        adapterforlist = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, android.R.id.text1, optionselected);
                        adapterforlist.setNotifyOnChange(true);
                        spinner.setAdapter(adapterforlist);
                        list1.setVisibility(View.GONE);
                    }
                });

                //now that the dialog is set up, it's time to show it
                dialog.show();
                } else {
                Toast.makeText(this, "RESULT NOT FOUND", Toast.LENGTH_SHORT).show();
           // }
        }
     }
    }
}

