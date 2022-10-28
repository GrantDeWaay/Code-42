package edu.iastate.code42.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.code42.R;
import edu.iastate.code42.objects.Course;

public class CourseListAdapter extends ArrayAdapter<Course> {

    private ArrayList<Course> courses;
    Context mContext;

    private static class ViewHolder{
        TextView title;
        TextView languages;
    }

    public CourseListAdapter(@NonNull Context context, @NonNull List<Course> objects) {
        super(context, R.layout.row_course, objects);
        this.courses = (ArrayList<Course>) objects;
        this.mContext = context;
    }
}
