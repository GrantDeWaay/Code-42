package edu.iastate.code42.utils;

import android.annotation.SuppressLint;
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
import edu.iastate.code42.objects.Course;
import edu.iastate.code42.objects.User;

public class UserListAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;
    Context mContext;

    private static class ViewHolder{
        TextView fullName;
        TextView username;
    }

    public UserListAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, R.layout.row_user, objects);
        this.users = (ArrayList<User>) objects;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User u = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_user, parent, false);
            viewHolder.fullName = convertView.findViewById(R.id.row_fullName);
            viewHolder.username = convertView.findViewById(R.id.row_username);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.fullName.setText(u.getFullName());
        viewHolder.username.setText(u.getUsername());

        // Return the completed view to render on screen
        return convertView;
    }
}
