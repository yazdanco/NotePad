package com.aminyazdanpanah.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Created by Amin Yazdanpanah. -> LinkedIn.com/aminyazdanpanah
 */
public class MainActivity extends AppCompatActivity {

    String[] filename, filepath, fileId, FilePN;
    DatabaseHelper helper = new DatabaseHelper(this);
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createText = new Intent(MainActivity.this, CreateText.class);
                startActivity(createText);
            }
        });
        getList();
    }
    public void getList() {
        TextView text = (TextView) findViewById(R.id.text);
        Cursor data = helper.getListContents();
        int count = 0;
        filename = new String[data.getCount()];
        filepath = new String[data.getCount()];
        fileId = new String[data.getCount()];
        while (data.moveToNext()) {
            fileId[count] = data.getString(0);
            filename[count] = data.getString(1);
            filepath[count] = data.getString(2);
            count++;
        }
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(null);
        if (data.getCount() == 0) {
            text.setText("There are no notes in this list! Please create one +!");
        } else {
            lv.setAdapter(new TextList(this, filename, filepath, fileId));
        }
    }
    public void onDeleteButtonClick(View v) {
        String File = v.getTag().toString();
        FilePN = File.split("--");
        AlertDialog diaBox = delete();
        diaBox.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            AlertDialog diaBox = about();
            diaBox.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog about() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)

                .setTitle("About")
                .setMessage("The app was created by Amin Yazdanpanah \n Please share your idea about this app to my website \n http://www.aminyazdanpanah.com")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private AlertDialog delete() {

        AlertDialog deleteMessage = new AlertDialog.Builder(this)

                .setTitle("Delete File")
                .setMessage("Are you sure you want to permanently delete " + FilePN[1] + "?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFile(FilePN[0], FilePN[1], FilePN[2]);
                    }
                })

                .create();
        return deleteMessage;

    }

    public void deleteFile(String FileID, String FileName, String FilePath) {
        if (!FilePath.matches("")) {
            FilePath = FilePath + "/";
        }
        String fpath = Environment.getExternalStoragePublicDirectory("").toString() + "/" + FilePath + FileName + ".txt";
        File file = new File(fpath);
        boolean deleted = file.delete();
        if (deleted) {
            Toast.makeText(getBaseContext(), FileName + " was deleted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Already your file was deleted!", Toast.LENGTH_LONG).show();
        }
        helper.deleteText(Integer.parseInt(FileID));
        getList();
    }



    public void onBackPressed() {
        AlertDialog diaBox = exit();
        diaBox.show();
    }

    private AlertDialog exit()
    {
        AlertDialog exitM =new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you want to exit NotePad?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return exitM;

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
