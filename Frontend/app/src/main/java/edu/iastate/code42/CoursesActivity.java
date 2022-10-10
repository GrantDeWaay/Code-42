package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.databinding.ActivityDashboardBinding;
import edu.iastate.code42.utils.BaseDrawer;

public class CoursesActivity extends BaseDrawer implements AdapterView.OnItemClickListener, View.OnClickListener {

    ActivityCoursesBinding activityBaseDrawerBinding;
    ListView courses;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Courses");

        courses = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseList);

        courses.setOnItemClickListener(this);

        add = activityBaseDrawerBinding.getRoot().findViewById(R.id.addCourse);
        add.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {
        Intent creation = new Intent(CoursesActivity.this, CourseCreationActivity.class);
        startActivity(creation);
    }
}