package edu.iastate.code42.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.code42.R;
import edu.iastate.code42.objects.User;

public class UserListAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;
    Context mContext;

    private static class ViewHolder{
        TextView firstName;
        TextView lastName;
        TextView username;
        TextView email;
    }

    public UserListAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, R.layout.row_user, objects);
        this.users = (ArrayList<User>) objects;
        this.mContext = context;
    }
}
