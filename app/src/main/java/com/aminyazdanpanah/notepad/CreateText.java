package com.aminyazdanpanah.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Amin Yazdanpanah. -> LinkedIn.com/aminyazdanpanah
 */
public class CreateText extends Activity {
    private EditText Etext, FNAME, PNAME;
    DatabaseHelper helper = new DatabaseHelper(this);
    String text, Filename, FilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_text);
        init();
    }

    private void init() {
        Etext = (EditText) findViewById(R.id.note_text);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.save_btn) {
            text = Etext.getText().toString();
            if (text.matches("")) {
                Toast.makeText(this, "You did not type anything ...", Toast.LENGTH_SHORT).show();
                return;
            }
            AlertDialog diaBox = AskOption();
            diaBox.show();

        }
        if (v.getId() == R.id.cancel_btn) {
            finish();
        }

    }

    public Boolean write(String fname, String pname, String fcontent) {
        if (!pname.matches("")) {
            pname = pname + "/";
        }
        try {
            String fpath = Environment.getExternalStoragePublicDirectory("").toString() + "/" + pname + fname + ".txt";

            File file = new File(fpath);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw e;
                }
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void insert(String filename, String path) {
        FileName c = new FileName();
        c.setFilename(filename);
        c.setPath(path);

        helper.insertFile(c);
    }

    private AlertDialog AskOption() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialogbox, null);
        FNAME = (EditText) promptsView.findViewById(R.id.filename);
        PNAME = (EditText) promptsView.findViewById(R.id.filepath);

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)

                .setTitle("Save As ...")
                .setView(promptsView)
                .setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Filename = FNAME.getText().toString();
                        FilePath = PNAME.getText().toString();

                        if (Filename.matches("")) {
                            falseAction();
                        } else {
                            write(Filename, FilePath, text);
                            if (write(Filename, FilePath, text)) {
                                insert(Filename, FilePath);
                                Toast.makeText(getBaseContext(),"Your text was Saved.",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(CreateText.this,MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getBaseContext(),"Please check your path or sure you allow to this app to access storage.",Toast.LENGTH_LONG).show();
                            }
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void falseAction() {
        Toast.makeText(this, "Please enter a file name ...", Toast.LENGTH_SHORT).show();
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
