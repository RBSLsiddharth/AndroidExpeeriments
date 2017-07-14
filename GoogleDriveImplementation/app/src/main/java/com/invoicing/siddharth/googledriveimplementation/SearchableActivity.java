package com.invoicing.siddharth.googledriveimplementation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddharh on 12/7/17.
 */
public class SearchableActivity extends ListActivity {

    List<String> arraylist = new ArrayList<String>();
    SearchResults searchresults = new SearchResults();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        handleIntent(getIntent());
    }

   

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            //use the query to search
           /* Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
            if (appData != null) {
           */
                String table ="products";
                arraylist  = searchresults.Searchresult(this,query, table);
                if(arraylist.size()>0){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arraylist);
                setListAdapter(adapter);
            }else {
                Toast.makeText(this, "RESULT NOT FOUND", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("SearchDomain", "Product");
        startSearch(null, false, appData, false);
        return true;
    }



}
