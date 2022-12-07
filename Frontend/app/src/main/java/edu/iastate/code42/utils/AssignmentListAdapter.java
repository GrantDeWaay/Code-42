package edu.iastate.code42.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.code42.R;
import edu.iastate.code42.objects.Assignment;
import edu.iastate.code42.objects.Course;

/**
 * AssignmentListAdapter class
 * Extends ArrayAdapter with type of Assignment to create dynamic list view rows of Assignments
 * Layout: row_assignment
 * @author Andrew
 */
public class AssignmentListAdapter extends ArrayAdapter<Assignment> {

    private ArrayList<Assignment> assignments;
    Context mContext;
    private boolean spinner;

    /**
     * Helper class to store the elements of the view
     */
    private static class ViewHolder{
        TextView title;
    }

    /**
     * Creates a new adapter using super constructor, hardcoded the layout
     * @param context Application or activity context
     * @param objects List of Assignment objects to display
     */
    public AssignmentListAdapter(@NonNull Context context, @NonNull List<Assignment> objects) {
        super(context, R.layout.row_assignment, objects);
        this.assignments = (ArrayList<Assignment>) objects;
        this.mContext = context;
    }

    public AssignmentListAdapter(@NonNull Context context, int resource, @NonNull List<Assignment> objects) {
        super(context, resource, objects);
        this.assignments = (ArrayList<Assignment>) objects;
        this.mContext = context;
        spinner = true;
    }

    private int lastPosition = -1;

    /**
     * Create and draw the dynamic view for the list row
     * @param position Position of row in list
     * @param convertView View of the row
     * @param parent View of the ListView parent
     * @return View of the row
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Assignment a = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_assignment, parent, false);
            viewHolder.title = convertView.findViewById(R.id.row_assignmentTitle);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        if(!spinner){
            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
        }
        lastPosition = position;

        viewHolder.title.setText(a.getAssignmentName());

        // Return the completed view to render on screen
        return convertView;
    }
}
