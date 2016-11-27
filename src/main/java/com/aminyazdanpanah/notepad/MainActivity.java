package com.aminyazdanpanah.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Created by Amin Yazdanpanah. -> LinkedIn.com/aminyazdanpanah
 */
public class MainActivity extends AppCompatActivity {

    String [] filename,filepath;
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
                Intent createText = new Intent(MainActivity.this,CreateText.class);
                startActivity(createText);
            }
        });
        TextView text = (TextView)findViewById(R.id.text);
        Cursor data = helper.getListContents();
        int count = 0;
        filename = new String[data.getCount()];
        filepath = new String[data.getCount()];
        while(data.moveToNext()) {
            filename[count] = data.getString(1);
            filepath[count] = data.getString(2);
            count++;
        }
        if(data.getCount() == 0){
            text.setText("There are no notes in this list! Please add one +!");
        }else{
            lv=(ListView) findViewById(R.id.listView);
            lv.setAdapter(new TextList(this, filename,filepath));
        }

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
                .setMessage("The app was created by Amin Yazdanpanah \n Please share your idea about this app to my \n http://www.LinkedIn.com/aminyazdanpanah")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
