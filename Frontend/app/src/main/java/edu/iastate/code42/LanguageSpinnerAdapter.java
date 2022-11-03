package edu.iastate.code42;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class LanguageSpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private String[] contentArray;
    private Integer[] imageArray;

    public LanguageSpinnerAdapter(Context context, String[] objects,
                          Integer[] imageArray) {
        super(context,  R.layout.lang_row, R.id.weekofday, objects);
        this.ctx = context;
        this.contentArray = objects;
        this.imageArray = imageArray;
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
        View row = inflater.inflate(R.layout.lang_row, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.weekofday);
        textView.setText(contentArray[position]);

        ImageView imageView = (ImageView)row.findViewById(R.id.lang_icon);
        imageView.setImageResource(imageArray[position]);

        return row;
    }
}