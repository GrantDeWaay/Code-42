package edu.iastate.code42.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.code42.R;
import edu.iastate.code42.objects.Assignment;

public class AssignmentListAdapter extends ArrayAdapter<Assignment> {

    private ArrayList<Assignment> assignments;
    Context mContext;

    private static class ViewHolder{
        TextView title;
    }

    public AssignmentListAdapter(@NonNull Context context, @NonNull List<Assignment> objects) {
        super(context, R.layout.row_assignment, objects);
        this.assignments = (ArrayList<Assignment>) objects;
        this.mContext = context;
    }
}
