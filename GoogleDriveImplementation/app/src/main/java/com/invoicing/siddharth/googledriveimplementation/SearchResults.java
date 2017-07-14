package com.invoicing.siddharth.googledriveimplementation;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by siddharh on 13/7/17.
 */

public class SearchResults {
    List<String> productsname = new ArrayList<String>();

    public List<String> Searchresult(Context context, String query, String tablename) {
        Iterator<Products> listofproductname = (new Databasehandling(context).searchtheproduct(query, tablename)).iterator();
        while (listofproductname.hasNext()){
            productsname.add(listofproductname.next().getProducts_name());
        }
        return productsname;
    }

}
