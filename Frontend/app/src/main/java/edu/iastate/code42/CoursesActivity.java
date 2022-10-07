package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.databinding.ActivityDashboardBinding;
import edu.iastate.code42.utils.BaseDrawer;

public class CoursesActivity extends BaseDrawer {

    ActivityCoursesBinding activityBaseDrawerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Courses");


    }
}