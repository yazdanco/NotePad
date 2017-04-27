package com.aminyazdanpanah.notepad;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Created by Amin Yazdanpanah. -> LinkedIn.com/aminyazdanpanah
 */
public class ShowText extends AppCompatActivity {
    TextView showtext;
    int FileId;
    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);
        FileId = Integer.parseInt(getIntent().getStringExtra("FileID"));
        String FileName = getIntent().getStringExtra("FileName");
        String PathFile = getIntent().getStringExtra("PathFile");
        setTitle(FileName + ".txt");
        showtext = (TextView)findViewById(R.id.showtext);
        showtext.setText(read(FileName,PathFile));
    }
    public String read(String fname,String Pname){
        if (!Pname.matches("")) {
            Pname = Pname + "/";
        }
        BufferedReader br = null;
        String response = null;

        try {

            StringBuilder output = new StringBuilder();
            String fpath = Environment.getExternalStoragePublicDirectory("").toString() + "/" + Pname + fname + ".txt";

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line).append("\n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            helper.deleteText(FileId);
            Toast.makeText(getBaseContext(),"The file was deleted by another app! ",Toast.LENGTH_LONG).show();
            Intent i = new Intent(ShowText.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            finish();
        }
        return response;

    }}
