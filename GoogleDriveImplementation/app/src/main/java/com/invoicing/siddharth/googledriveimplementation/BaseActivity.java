package com.invoicing.siddharth.googledriveimplementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnsubmit;
    EditText editText1;
    EditText editText2;
    private String username = "";
    private String password = "";
    public static final String KEY_Username = "username";
    public static final String Key_Password = "email_id";
    public static final String Key_Choice = "choice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("I CAME IN ONCREATE METHOD");
        setContentView(R.layout.activity_main);
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        btnsubmit=(Button)findViewById(R.id.button);
        btnsubmit.setOnClickListener(this);
    }

public void Login() {
    username = editText1.getText().toString().trim();
    password = editText2.getText().toString().trim();


   /* final String Register_Url = "http://192.168.56.1/processing.php";

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Register_Url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

              *//*  sendData(response);
*//*

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(SignupService.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_Username, username);
                params.put(Key_Password, password);
                params.put(Key_Choice,"login");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);*/
    }

    private void sendData(String response) {

    /*    try {
           JSONObject response1 = new JSONObject(response);
            Toast.makeText(BaseActivity.this,response1.getString("message"),Toast.LENGTH_LONG).show();
            if(response1.getString("match").equals("found")){

                Intent intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
               Intent intent = new Intent(this,SIGNUPACTIVITY.class);
                startActivity(intent);
              //  Login();
                break;
        }
        }
    }



