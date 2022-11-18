package edu.iastate.code42;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;

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
    private TextView testingCodeTitleStatusTextView;
    private RelativeLayout popupRelativeLayout;
    private ProgressBar progressBar;
    User user;
    SharedPreferences userSession;

    int id;
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

        testingCodeTitleStatusTextView = testPUV.findViewById(R.id.testingCodeTitleStatusTextView);
        popupRelativeLayout = testPUV.findViewById(R.id.popupRelativeLayout);
        results = testPUV.findViewById(R.id.testResultsTextView);
        progressBar = testPUV.findViewById(R.id.progressBar);

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);


        testPopup = new PopupWindow(testPUV, width, height, true);
        id = getIntent().getIntExtra("id", -1);

        String url = String.format(Const.GET_ASSIGNMENT, id, userSession.getString("token", ""));
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    assignmentData = response;
                    try {
                        assignmentName.setText(assignmentData.getString("title"));
                        statementTextView.setText(assignmentData.getString("problemStatement"));
                        descText = assignmentData.getString("description");
                        results.setText("Loadin' doot doot doot datt...");
                        description.setText(descText);
                        assignmentNamePopupText.setText(assignmentData.getString("title"));
                        baseCode = assignmentData.getString("template");
                        ide.setText(baseCode);
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
            popupRelativeLayout.setBackgroundColor(Color.parseColor("#673AB7"));
            String loadingString = "Performing Tests...";
            results.setText(loadingString);
            progressBar.setVisibility(View.VISIBLE);
            Log.i("ok",userSession.getString("token", ""));
            String urlw = String.format(Const.SOURCE + "run/%s" + Const.TOKEN, id, userSession.getString("token", ""));
            Log.i("url", urlw);
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", "name" + ".java");
                obj.put("contents", ide.getText().toString().replaceAll("\"", ("\\" + "\"")));
                obj.put("language", "Java");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("json", obj.toString());
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, urlw,obj,
                    response -> {
                Log.i("Return", response.toString());
                        try {
                            String message;
                            if(response.getBoolean("pass")){
                                testingCodeTitleStatusTextView.setText("PASSED");
                                popupRelativeLayout.setBackgroundColor(Color.parseColor("#008000"));
                                message = "Expected : " + response.getString("expectedOutput") + " " +
                                        "Actual : " + response.getString("actualOutput");
                            }
                            else{
                                testingCodeTitleStatusTextView.setText("FAIL");
                                popupRelativeLayout.setBackgroundColor(Color.parseColor("#D2042D"));
                                message = "Expected : " + response.getString("expectedOutput") + " " +
                                        "Actual : " + response.getString("actualOutput");
                                if (response.getString("message").equals("Compilation failed")) {
                                    message = "Compile Error!";
                                }
                            }
                            results.setText(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }, error -> {
                        testingCodeTitleStatusTextView.setText("FAIL");
                        popupRelativeLayout.setBackgroundColor(Color.RED);
                        results.setText("That didn't work!");
                        progressBar.setVisibility(View.INVISIBLE);
                    });

            AppController.getInstance().addToRequestQueue(req);

            testPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            testPopup.setFocusable(true);
            testPopup.update();
            testPUV.setOnTouchListener((v, event) -> {
                if(progressBar.getVisibility() == View.INVISIBLE){
                    v.performClick();
                    testPopup.dismiss();
                    return true;
                }
                return false;
            });
        }
    }
}