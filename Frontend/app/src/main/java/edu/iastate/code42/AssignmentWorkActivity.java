package edu.iastate.code42;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.code42.app.AppController;

public class AssignmentWorkActivity extends AppCompatActivity implements View.OnClickListener{
    private Button submit;
    private Button info;
    private Button loadDefault;
    private EditText ide;
    private TextView statementTextView;
    private TextView description;
    private TextView results;
    private PopupWindow testPopup;
    private PopupWindow infoPopup;
    private View testPUV;
    private View infoPUV;
    private TextView assignmentName;
    private TextView assignmentNamePopupText;
    private JSONObject assignmentData;
    private String descText;
    private String baseCode;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;


        setContentView(R.layout.activity_assignment_take);

        ide = findViewById(R.id.codeEditText);
        statementTextView = findViewById(R.id.statementTextView);
        assignmentName = findViewById(R.id.assignmentNameTextView);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        infoPUV = inflater.inflate(R.layout.popup_window_info, null);
        description = infoPUV.findViewById(R.id.descriptionTextViewPopup);
        assignmentNamePopupText = infoPUV.findViewById(R.id.assignmentNamePopupText);
        infoPopup = new PopupWindow(infoPUV, width, height, true);

        testPUV = inflater.inflate(R.layout.popup_window, null);
        results = testPUV.findViewById(R.id.testResultsTextView);


        testPopup = new PopupWindow(testPUV, width, height, true);

        String url = "http://coms-309-028.class.las.iastate.edu:8080/assignment/37?token=test";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    assignmentData = response;
                    try {
                        assignmentName.setText(assignmentData.getString("title"));
                        statementTextView.setText(assignmentData.getString("problemStatement"));
                        descText = assignmentData.getString("description");
                        Log.i("testing", descText);
                        results.setText("Loadin' doot doot doot datt...");
                        description.setText(descText);
                        assignmentNamePopupText.setText(assignmentData.getString("title"));
                        ide.setText(assignmentData.getString("template"));
                        baseCode = assignmentData.getString("template");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.i("resp", error.toString());
        });
        AppController.getInstance().addToRequestQueue(req);
        info = findViewById(R.id.infoButton);
        submit = findViewById(R.id.submitButton);
        loadDefault = findViewById(R.id.loadDefaultButton);
        loadDefault.setOnClickListener(view -> {
            ide.setText(baseCode);
        });





        info.setOnClickListener(this);
        submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        int infoId = info.getId();
        int submitId = submit.getId();
        if (view.getId() == infoId) {
            infoPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            infoPUV.setOnTouchListener((v, event) -> {
                v.performClick();
                infoPopup.dismiss();
                return true;
            });
        }
        else if (view.getId() == submitId) {
            testPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            testPUV.setOnTouchListener((v, event) -> {
                v.performClick();
                testPopup.dismiss();
                return true;
            });
        }
    }
}