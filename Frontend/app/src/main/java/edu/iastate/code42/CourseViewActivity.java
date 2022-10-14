package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCourseViewBinding;
import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseDrawer;
import edu.iastate.code42.utils.Const;

public class CourseViewActivity extends BaseDrawer implements View.OnClickListener {

    ActivityCourseViewBinding activityBaseDrawerBinding;
    EditText title;
    EditText description;
    EditText language;
    FloatingActionButton edit;
    boolean viewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityCourseViewBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("");

        User user = User.get(getApplicationContext());

        title = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseTitleHeader);
        description = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseDescriptionView);
        language = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseLanguagesView);
        edit = activityBaseDrawerBinding.getRoot().findViewById(R.id.floatingEditCourse);

        edit.setOnClickListener(this);

        if(user.getType().equals("student")){
            edit.setVisibility(View.INVISIBLE);
            edit.setEnabled(false);
        }else{
            edit.setVisibility(View.VISIBLE);
            edit.setEnabled(true);
        }


        if(getIntent().hasExtra("id")){
            title.setEnabled(false);
            description.setEnabled(false);
            language.setEnabled(false);
            viewState = true;

            String url = Const.SOURCE + Const.GET_COURSE + getIntent().getIntExtra("id",0);

            JsonObjectRequest loginReq = new JsonObjectRequest(Request.Method.GET, url,
                    null,  new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        title.setText(response.getString("title"));
                        description.setText(response.getString("description"));
                        language.setText(response.getString("languages"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Login Auth Error:", error.toString());

                    Toast.makeText(getApplicationContext(), R.string.login_volley_error,
                            Toast.LENGTH_LONG).show();
                }
            }){
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(loginReq, "course_get_req");
        }else{
            Intent courseList = new Intent(this, CoursesActivity.class);
            startActivity(courseList);
        }
    }

    @Override
    public void onClick(View view) {

    }
}