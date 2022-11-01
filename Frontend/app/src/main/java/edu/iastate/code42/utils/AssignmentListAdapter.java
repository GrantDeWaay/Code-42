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

    private int lastPosition = -1;

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

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.title.setText(a.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }
}
