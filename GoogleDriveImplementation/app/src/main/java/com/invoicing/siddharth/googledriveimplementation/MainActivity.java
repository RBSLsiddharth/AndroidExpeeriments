package com.invoicing.siddharth.googledriveimplementation;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by siddharh on 11/7/17.
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Google Drive Activity";
    private static final int REQUEST_CODE_RESOLUTION = 1;
    private static final  int REQUEST_CODE_OPENER = 2;
    private GoogleApiClient mGoogleApiClient;
    private boolean fileOperation = false;
    private DriveId mFileId;
    public DriveFile file;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
      /*  toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSupportActionBar(toolbar);
        }*/

    }

    /**
     * Called when the activity will start interacting with the user.
     * At this point your activity is at the top of the activity stack,
     * with user input going to it.
     */

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {

            /**
             * Create the API client and bind it to an instance variable.
             * We use this instance as the callback for connection and connection failures.
             * Since no account name is passed, the user is prompted to choose.
             */
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {

            // disconnect Google API client connection
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        // Called whenever the API client fails to connect.
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());

        if (!result.hasResolution()) {

            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }

        /**
         *  The failure has a resolution. Resolve it.
         *  Called typically when the app is not yet authorized, and an  authorization
         *  dialog is displayed to the user.
         */

        try {

            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);

        } catch (IntentSender.SendIntentException e) {

            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }

    /**
     * It invoked when Google API client connected
     * @param connectionHint
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
    }

    /**
     * It invoked when connection suspend
     * @param cause
     */
    @Override
    public void onConnectionSuspended(int cause) {

        Log.i(TAG, "GoogleApiClient connection suspended");
    }

    public void onClickCreateFile(View view){
        fileOperation = true;

        // create new contents resource
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(driveContentsCallback);

    }

    public void onClickOpenFile(View view){
        fileOperation = false;

        // create new contents resource
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(driveContentsCallback);
    }



    /**
     * This is Result result handler of Drive contents.
     * this callback method call CreateFileOnGoogleDrive() method
     * and also call OpenFileFromGoogleDrive() method,
     * send intent onActivityResult() method to handle result.
     */
    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
        @Override
        public void onResult(DriveApi.DriveContentsResult result) {

            if (result.getStatus().isSuccess()) {

                if (fileOperation == true) {

                    CreateFileOnGoogleDrive(result);

                } else {

                    OpenFileFromGoogleDrive();

                }
            }

        }
    };

    /**
     * Create a file in root folder using MetadataChangeSet object.
     * @param result
     */
    public void CreateFileOnGoogleDrive(DriveApi.DriveContentsResult result){

        final DriveContents driveContents = result.getDriveContents();

        // Perform I/O off the UI thread.
        new Thread() {
            @Override
            public void run() {
                // write content to DriveContents
                java.io.File dbFile = getApplication().getDatabasePath("invoicingdatabases");
              /*  final String inFileName = "/data/data/com.example.siddharth.loginregistration/databases/invoicingdatabases.db";
                File dbFile = new File(inFileName);*/
                FileInputStream fis = null;
                OutputStream outputStream = driveContents.getOutputStream();

                try {
                    fis = new FileInputStream(dbFile);
                    // Transfer bytes from the inputfile to the outputfile
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer))>0){
                        outputStream.write(buffer, 0, length);
                    }
                    // Close the streams
                    outputStream.flush();
                    outputStream.close();
                    fis.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG,e.getMessage());
                }
                catch (IOException i){
                    Log.d(TAG,i.getMessage());
                }


                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle("newdatabase")
                .setStarred(true).build();

                // create a file in root folder
                Drive.DriveApi.getRootFolder(mGoogleApiClient)
                        .createFile(mGoogleApiClient, changeSet, driveContents)
                        .setResultCallback(fileCallback);
            }
        }.start();
    }

    /**
     *  Open list of folder and file of the Google Drive
     */
    public void OpenFileFromGoogleDrive(){

        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setMimeType(new String[] { "text/plain", "text/html" })
                .build(mGoogleApiClient);
        try {
            startIntentSenderForResult(

                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);

        } catch (IntentSender.SendIntentException e) {

            Log.w(TAG, "Unable to send intent", e);
        }

    }

    /**
     * Handle result of Created file
     */
    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
    ResultCallback<DriveFolder.DriveFileResult>() {
        @Override
        public void onResult(DriveFolder.DriveFileResult result) {
            if (result.getStatus().isSuccess()) {

                Toast.makeText(getApplicationContext(), "file created: "+""+
                        result.getDriveFile().getDriveId(), Toast.LENGTH_LONG).show();

            }

            return;

        }
    };

    /**
     *  Handle Response of selected file
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode, final Intent data) {
        switch (requestCode) {

            case REQUEST_CODE_OPENER:

                if (resultCode == RESULT_OK) {

                    mFileId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);

                    Log.e("file id", mFileId.getResourceId() + "");

                    String url = "https://drive.google.com/open?id="+ mFileId.getResourceId();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

  /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.search) {
            *//*Toast.makeText(this, "in searchbar", Toast.LENGTH_SHORT).show();
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = item.getItem
            searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );*//*
            onSearchRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );

        return true;
    }*/
    public void onSearchbuttonclick(View view){
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
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

}