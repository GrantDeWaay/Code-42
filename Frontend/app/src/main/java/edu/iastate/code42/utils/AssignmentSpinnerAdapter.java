package edu.iastate.code42.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.iastate.code42.objects.Assignment;

public class AssignmentSpinnerAdapter extends AssignmentListAdapter{


    public AssignmentSpinnerAdapter(@NonNull Context context, @NonNull List<Assignment> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent){
        return getView(position, convertView, parent);
    }
}
