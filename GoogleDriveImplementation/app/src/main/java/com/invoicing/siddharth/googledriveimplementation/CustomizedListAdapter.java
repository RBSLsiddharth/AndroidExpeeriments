package  com.invoicing.siddharth.googledriveimplementation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.invoicing.siddharth.googledriveimplementation.Products;
import com.invoicing.siddharth.googledriveimplementation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/4/17.
 */
public class CustomizedListAdapter extends ArrayAdapter<Products> {
    Context context;
    static  final String TAG = "trandingAdapter";
    int layoutResourceId;
    List<String>favorites;
    List<Products> data ;
    public CustomizedListAdapter(Context context, int layoutResourceId, List<Products> data) {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProductHolder holder = null;
        ArrayList<String> url1=new ArrayList<String>();
        //Toast.makeText(context,String.valueOf(favorites.size()),Toast.LENGTH_SHORT).show();
        if (row == null) {
            // url1.clear();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ProductHolder();
            // holder.textName = (TextView) row.findViewById(R.id.txtName);
            holder.product_name = (TextView)row.findViewById(R.id.Listitem);
            row.setTag(holder);
        } else {
            holder = (ProductHolder) row.getTag();
        }
        final Products products = data.get(position);
            holder.product_name.setText(products.getProducts_name());
            Log.d("countcalltime",String.valueOf(1));
        

        return row;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.d(TAG,String.valueOf(data.size()));
        return data.size();

    }

    static class ProductHolder {
        TextView product_name;
        

    }

}
