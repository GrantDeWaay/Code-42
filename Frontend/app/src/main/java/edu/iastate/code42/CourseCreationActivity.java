package edu.iastate.code42;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.databinding.ActivityCourseCreationBinding;
import edu.iastate.code42.databinding.ActivityCoursesBinding;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.BaseBack;
import edu.iastate.code42.utils.Const;


/**
 * CourseCreationActivity class
 * Create course screen
 * Layout: activity_course_creation
 * @author Andrew
 */

public class CourseCreationActivity extends BaseBack implements View.OnClickListener {
    ActivityCourseCreationBinding activityBaseBackBinding;

    Button create;
    EditText title;
    EditText description;
    EditText language;

    User user;
    SharedPreferences userSession;

    /**
     * Creates and draws the view; initializes the objects
     * @param savedInstanceState Application Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseBackBinding = ActivityCourseCreationBinding.inflate(getLayoutInflater());
        setContentView(activityBaseBackBinding.getRoot());
        allocateActivityTitle("");

        setPreviousScreen(new Intent(this, CoursesActivity.class));
        setSave(true);

        create = activityBaseBackBinding.getRoot().findViewById(R.id.createCourse);
        title = activityBaseBackBinding.getRoot().findViewById(R.id.editCourseTitle);
        description = activityBaseBackBinding.getRoot().findViewById(R.id.courseDescriptionView);
        language = activityBaseBackBinding.getRoot().findViewById(R.id.courseLanguagesView);

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

        if(!userSession.contains("token")){
            Intent login = new Intent(CourseCreationActivity.this, MainActivity.class);
            startActivity(login);
        }

        create.setOnClickListener(this);
    }

    /**
     * Event handler for when Create button pressed; Performs CREATE_COURSE HTTP Request
     * @param view Button View that is Pressed
     */
    @Override
    public void onClick(View view) {
        if (title.getText() != null && !(title.getText().toString().isEmpty()) &&
                description.getText() != null && !(description.getText().toString().isEmpty()) &&
                language.getText() != null && !(language.getText().toString().isEmpty())) {
            String url = String.format(Const.CREATE_COURSE, userSession.getString("token", ""));
            JSONObject jsonBody = new JSONObject();

            try {
                jsonBody.put("title", title.getText());
                jsonBody.put("description", description.getText());
                jsonBody.put("languages", language.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest createReq = new JsonObjectRequest(Request.Method.POST, url,
                    jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Intent view = new Intent(CourseCreationActivity.this, CourseViewActivity.class);
                    try {
                        view.putExtra("courseId", response.getInt("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(view);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Course Creation Error", error.toString());

                    Toast.makeText(getApplicationContext(), R.string.course_create_error,
                            Toast.LENGTH_LONG).show();
                }
            }) {
                /**
                 * Passing some request headers
                 */
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

            AppController.getInstance().addToRequestQueue(createReq, "create_course_req");
        }
    }
}
