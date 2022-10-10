package edu.iastate.code42;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import edu.iastate.code42.utils.Const;

public class CourseCreationActivity extends AppCompatActivity implements View.OnClickListener {

    Button create;
    EditText title;
    EditText description;
    EditText language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);

        create = findViewById(R.id.createCourse);
        title = findViewById(R.id.editCourseTitle);
        description = findViewById(R.id.courseDescriptionView);
        language = findViewById(R.id.courseLanguagesView);

        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url = Const.SOURCE + Const.CREATE_COURSE;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("title", title.getText());
            jsonBody.put("description", description.getText());
            jsonBody.put("languages", language.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest createReq = new JsonObjectRequest(Request.Method.POST, url,
                jsonBody,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(),
                        Toast.LENGTH_LONG).show();

                Intent view = new Intent(CourseCreationActivity.this, CourseViewActivity.class);
                try {
                    view.putExtra("id",response.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(view);
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

        AppController.getInstance().addToRequestQueue(createReq, "create_req");
    }
}
