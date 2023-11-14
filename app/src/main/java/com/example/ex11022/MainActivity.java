package com.example.ex11022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * The main activity:
 * reads and adds text to an internal file, according to different buttons.
 * @author Ori Roitzaid <or1901 @ bs.amalnet.k12.il>
 * @version	1
 * @since 13/11/2023
 */
public class MainActivity extends AppCompatActivity {
    TextView tV;
    EditText eT;
    Intent si;
    private final String FILENAME = "intFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tV = (TextView) findViewById(R.id.tV);
        eT = (EditText) findViewById(R.id.eT);

        // Displays the file content as the app starts
        String fileContent = readFile();
        tV.setText(fileContent);
    }

    /**
     * This function reads the text from the file, and returns it as a string.
     * @return The text written in the file.
     */
    public String readFile() {
        String fileContent = "";

        try {
            // Inits the variables in order to read the file
            FileInputStream fIS= openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();

            // Reads from file
            String line = bR.readLine();
            while (line != null) {
                sB.append(line + '\n');
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

    /**
     * This function writes a given text to the file.
     * @param text the text to write to the file.
     */
    public void writeToFile(String text) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write(text);

            bW.close();
            oSW.close();
            fOS.close();
        }
        catch (IOException e) {
            Toast.makeText(this, "Error writing to file", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Error writing to file");
        }
    }

    /**
     * This function adds the input of the edit text to the file content.
     * @param view the button the was clicked to save the input to the file.
     */
    public void save(View view) {
        String prevContent = readFile();

        if(prevContent.length() > 0)
            // Removes the extra '\n'
            prevContent = prevContent.substring(0, prevContent.length() - 1);

        prevContent += eT.getText().toString();  // Adds the edit text input

        writeToFile(prevContent);
        tV.setText(prevContent);
    }

    /**
     * This function resets the content in the file, and the text view.
     * @param view the button clicked to reset the file.
     */
    public void reset(View view) {
        writeToFile("");  // Resets the file content
        tV.setText("");
    }

    /**
     * This function adds the input of the edit text to the file content, and exits the app.
     * @param view the button clicked to save and exit.
     */
    public void exit(View view) {
        save(view);  // Adds the input of the edit text to the file
        finish();
    }

    /**
     * This function presents the options menu for moving between activities.
     * @param menu the options menu in which you place your items.
     * @return true in order to show the menu, otherwise false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This function reacts to the user choice in the options menu - it moves to the chosen
     * activity from the menu.
     * @param item the menu item that was selected.
     * @return must return true for the menu to react.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuCredits){
            si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }

        return super.onOptionsItemSelected(item);
    }}