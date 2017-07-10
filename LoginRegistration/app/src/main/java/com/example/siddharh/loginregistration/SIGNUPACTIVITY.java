package com.example.siddharh.loginregistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class SIGNUPACTIVITY extends AppCompatActivity implements View.OnClickListener {
    Button btnsubmit;
    Button btnlistofallusers , backpress,btnupdate,updatebutton,btndeleteClient;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editTextUpdateId;
    String username="";
    String email_id ="";
    String phone_no ="";
    String address ="";
    String updatingid="";
    ArrayList<Clients> Listofallclients = new ArrayList<Clients>();
    LinearLayout Clientlayout , ClientListlayout, UpdateLinearlayout ;
    ListView ClientListview ;
    public Context getContextofsignupactivity(){
        return this;
    }
   @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signupactivity);
            btnsubmit         = (Button) findViewById(R.id.signupbutton);
            btnlistofallusers = (Button) findViewById(R.id.viewoflist);
            backpress         = (Button) findViewById(R.id.btnbacktohome);
            btnupdate         = (Button) findViewById(R.id.btnupdate);
            updatebutton      = (Button) findViewById(R.id.updatebutton);
            btndeleteClient   = (Button) findViewById(R.id.deleteClient);
            updatebutton.setOnClickListener(this);
            btndeleteClient.setOnClickListener(this);
            btnsubmit.setOnClickListener(this);
            Clientlayout      = (LinearLayout)findViewById(R.id.clientinfolayout);
            ClientListlayout  = (LinearLayout)findViewById(R.id.listlayout);
            ClientListview    = (ListView)findViewById(R.id.clientlist) ;
            UpdateLinearlayout= (LinearLayout)findViewById(R.id.updateEdittext);
            btnlistofallusers.setOnClickListener(this);
            btnupdate.setOnClickListener(this);
            backpress.setOnClickListener(this);
            editText1         = (EditText) findViewById(R.id.editText4);
            editText2         = (EditText) findViewById(R.id.editText5);
            editText3         = (EditText) findViewById(R.id.editText3);
            editText4         = (EditText) findViewById(R.id.editText6);
            editTextUpdateId  = (EditText) findViewById(R.id.updatingId);

   }
    public void submit() {
            username = editText1.getText().toString().trim();
            email_id = editText2.getText().toString().trim();
            phone_no = editText3.getText().toString().trim();
            address = editText4.getText().toString().trim();
            Intent intent1 = new Intent(this,HomePage.class);
            intent1.putExtra("username", username);
            intent1.putExtra("email_id", email_id);
            intent1.putExtra("phone_no",phone_no);
            intent1.putExtra("address", address);
           try {
               new HomePage().addUsertoDatabase(intent1, this);
               Toast.makeText(this,"done inserting",Toast.LENGTH_LONG).show();
               startActivity(new Intent(this,MainActivity.class));
           }catch (Exception e){
               Toast.makeText(this,"GOT AN EXCEPTION",Toast.LENGTH_LONG).show();
           }
    }
    public void listofClients(){
        Listofallclients =new HomePage().listofClients(this);
        if(!(Listofallclients==null)) {
            ClientListlayout.setVisibility(View.VISIBLE);
            ArrayAdapter<Clients> adapter = new ArrayAdapter<Clients>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Listofallclients);
            ClientListview.setAdapter(adapter);
        }
           ClientListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   final Clients Clientname = (Clients) parent.getItemAtPosition(position);
                   btndeleteClient.setVisibility(View.VISIBLE);
                   btndeleteClient.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           delete(Clientname.getEmail_id());
                       }
                   });
               }
           });
    }
    public void update(String updatingid){
        editText2.setText(updatingid);
        editText2.setEnabled(false);
        Clients clients = new Clients();
        clients.setUsers_name(editText1.getText().toString().trim());
        clients.setEmail_id(updatingid);
        clients.setPhone_number(editText3.getText().toString().trim());
        clients.setAddress(editText4.getText().toString().trim());
        new HomePage().updateClientDetails(clients,this,updatingid);

    }
    public void delete(String deletingId){
       try {
           new HomePage().deletefromDatabase(this, deletingId);
           Toast.makeText(this, "DELETED ..nOw Go Back Urself,There's no Spoon feeding", Toast.LENGTH_SHORT).show();
           btndeleteClient.setVisibility(View.GONE);
       }catch (Exception e){
           Toast.makeText(this, "COULD NOT DELETE ,,GOBACK AND COME BACK", Toast.LENGTH_SHORT).show();
       }
       }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupbutton:
                submit();
                break;
            case R.id.viewoflist:
                Clientlayout.setVisibility(View.GONE);
                listofClients();
                break;
            case R.id.btnbacktohome: Clientlayout.setVisibility(View.VISIBLE);
                ClientListlayout.setVisibility(View.GONE);
                break;
            case R.id.btnupdate:
                Clientlayout.setVisibility(View.GONE);
                UpdateLinearlayout.setVisibility(View.VISIBLE);
                break;
            case R.id.updatebutton:
                updatingid =  editTextUpdateId.getText().toString().trim();
                UpdateLinearlayout.setVisibility(View.GONE);
                Clientlayout.setVisibility(View.VISIBLE);
                btnsubmit.setText(" UpdateYourself");
                btnsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        update(updatingid);
                    }
                });
                }

    }
}