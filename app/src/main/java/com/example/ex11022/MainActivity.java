package com.example.ex11022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView tV;
    EditText eT;
    String line;
    Intent si;
    private final String FILENAME = "intFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tV = (TextView) findViewById(R.id.tV);
        eT = (EditText) findViewById(R.id.eT);
    }

    /**
     * This function reads the text from the file, and returns it as a string.
     * <p>
     *
     * @return The text written in the file.
     */
    public String readFile() {
        String fileContent = "";
        try {
            FileInputStream fIS= openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();

            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            bR.close();
            iSR.close();
            fIS.close();

            fileContent = sB.toString();
        }
        catch (IOException e){
            Toast.makeText(this, "Error reading file", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Error reading file");
        }

        return fileContent;
    }
}