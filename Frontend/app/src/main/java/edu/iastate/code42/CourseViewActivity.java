package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCourseViewBinding;
import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.utils.BaseDrawer;
import edu.iastate.code42.utils.Const;

public class CourseViewActivity extends BaseDrawer {

    ActivityCourseViewBinding activityBaseDrawerBinding;
    EditText title;
    EditText description;
    EditText language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivityCourseViewBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("");

        title = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseTitleHeader);
        description = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseDescriptionView);
        language = activityBaseDrawerBinding.getRoot().findViewById(R.id.courseLanguagesView);

        if(getIntent().hasExtra("id")){
            title.setEnabled(false);
            description.setEnabled(false);
            language.setEnabled(false);

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
}