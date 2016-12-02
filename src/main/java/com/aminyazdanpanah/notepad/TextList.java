package com.aminyazdanpanah.notepad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Amin Yazdanpanah. -> LinkedIn.com/aminyazdanpanah
 */
public class TextList extends BaseAdapter{
    String [] Filename,Pathfile,FileId;
    Context context;
    private static LayoutInflater inflater=null;
    public TextList(MainActivity mainActivity, String[] filename, String[] pathfile, String[] fileId) {
        Filename = filename;
        Pathfile = pathfile;
        FileId = fileId;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return Filename.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView file,path;
        ImageView img;
        Button delete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.text_list, null);
        holder.file=(TextView) rowView.findViewById(R.id.textView1);
        holder.path=(TextView) rowView.findViewById(R.id.textView2);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.delete=(Button) rowView.findViewById(R.id.delete);
        holder.delete.setTag(FileId[position]+ "--" +Filename[position] + "--"+Pathfile[position] +"--END");
        holder.file.setText(Filename[position]+".txt");
        holder.path.setText("Path: /"+Pathfile[position]);
        holder.img.setImageResource(R.drawable.notepaper);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (context, ShowText.class);
                i.putExtra("FileID", FileId[position]);
                i.putExtra("FileName", Filename[position]);
                i.putExtra("PathFile", Pathfile[position]);
                context.startActivity(i);
            }
        });
        return rowView;
    }

}
