package ru.nsu.ccfit.alex.travki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Map;

/**
 * Created by alexandra on 13.10.17.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private String[] contentArray;
    private Integer[] imageArray;

    public SpinnerAdapter(Context context, int resource, Map<String, Integer> hashMap) {
        super(context,  resource, hashMap.keySet().toArray(new String[hashMap.size()]));
        ctx = context;
        contentArray = hashMap.keySet().toArray(new String[hashMap.size()]);
        imageArray = hashMap.values().toArray(new Integer[hashMap.size()]);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_with_img, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.elname);
        textView.setText(contentArray[position]);


        ImageView imageView = (ImageView) row.findViewById(R.id.icon);
        if(null != imageArray[position]) imageView.setImageResource(imageArray[position]);
        else imageView.setImageResource(0);

        return row;
    }

    private void setImg(ImageView iv, String path){
        Uri otherPath = Uri.parse(path);

        iv.setImageURI(otherPath);
    }

    private void setImage(ImageView iv, String path){
        File imgFile = new File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv.setImageBitmap(myBitmap);
        }
    }
}