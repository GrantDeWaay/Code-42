package edu.iastate.code42.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.code42.R;
import edu.iastate.code42.objects.Grade;

public class GradeTeacherListAdapter extends ArrayAdapter<Grade> implements View.OnClickListener {

    private ArrayList<Grade> grades;
    private boolean edit = false;
    Context mContext;
    ViewHolder viewHolder; // view lookup cache stored in tag

    private static class ViewHolder{
        TextView name;
        EditText grade;
        TextView gradeTotal;
        Button editGrade;
    }

    public GradeTeacherListAdapter(@NonNull Context context, @NonNull List<Grade> objects) {
        super(context, R.layout.row_grade, objects);
        this.grades = (ArrayList<Grade>) objects;
        this.mContext = context;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Grade g = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view


        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_grade, parent, false);

            viewHolder.editGrade = convertView.findViewById(R.id.row_gradeEdit);
            viewHolder.grade = convertView.findViewById(R.id.row_gradeTeacherScore);
            viewHolder.gradeTotal = convertView.findViewById(R.id.row_gradeTeacherTotal);
            viewHolder.name = convertView.findViewById(R.id.row_gradeTeacherStudentName);

            viewHolder.grade.setEnabled(edit);

            viewHolder.editGrade.setOnClickListener(this);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.name.setText(g.getUser().getFullName());
        viewHolder.grade.setText(String.format("%lf", g.getGrade()));
        viewHolder.gradeTotal.setText(String.format("%lf", g.getGradeTotal()));

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onClick(View view) {
        if(edit){
            edit = false;
            //TODO PUT updated grade
        }else{
            edit = true;
        }

        viewHolder.grade.setEnabled(edit);
    }
}
