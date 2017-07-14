package com.invoicing.siddharth.googledriveimplementation;

import android.app.backup.BackupAgentHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by siddharh on 10/7/17.
 */

public class MyBackUpplace extends BackupAgentHelper {
    static final String File_Name_Of_Prefrences = "myPrefrences";
    static final String PREFS_BACKUP_KEY = "backup";
    static final String TAG = "backup";

    @Override
    public void onCreate() {
        final String inFileName = "/data/data/com.example.siddharh/databases/invoicingdatabases.db";
        File dbFile = new File(inFileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()+"/database_copy.db";

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        // Close the streams
        output.flush();
        output.close();
        fis.close();
        } catch (FileNotFoundException e) {
        Log.d(TAG,e.getMessage());
        }
        catch (IOException i){
            Log.d(TAG,i.getMessage());
        }

    }
}
