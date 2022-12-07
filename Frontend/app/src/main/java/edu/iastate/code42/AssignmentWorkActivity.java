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

import org.intellij.lang.annotations.Language;
import org.json.JSONException;
import org.json.JSONObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.AssignmentCreationDataHolder;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;

public class AssignmentWorkActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submit, info, loadDefault;
    private EditText ide;
    TextView statementTextView, description, results, testingCodeTitleStatusTextView,
            assignmentName, assignmentNamePopupText;
    private PopupWindow testPopup, infoPopup;
    private View testPUV, infoPUV;
    private JSONObject assignmentData;
    private String descText, baseCode;
    private RelativeLayout popupRelativeLayout;
    private ProgressBar progressBar;
    User user;
    SharedPreferences userSession;
    private WebSocketClient cc;
    private String WS_URL;
    private int id;
    private String language;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        setContentView(R.layout.activity_assignment_take);
        id = getIntent().getIntExtra("id", -1);
        ide = findViewById(R.id.codeEditText);
        statementTextView = findViewById(R.id.statementTextView);
        assignmentName = findViewById(R.id.assignmentNameTextView);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        infoPUV = inflater.inflate(R.layout.popup_window_info, null);
        description = infoPUV.findViewById(R.id.descriptionTextViewPopup);
        assignmentNamePopupText = infoPUV.findViewById(R.id.assignmentNamePopupText);
        infoPopup = new PopupWindow(infoPUV, width, height, true);

        testPUV = inflater.inflate(R.layout.popup_window_submission_tests, null);

        testingCodeTitleStatusTextView = testPUV.findViewById(R.id.testingCodeTitleStatusTextView);
        popupRelativeLayout = testPUV.findViewById(R.id.popupRelativeLayout);
        results = testPUV.findViewById(R.id.testResultsTextView);
        progressBar = testPUV.findViewById(R.id.progressBar);

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);
        WS_URL = String.format(Const.WS_RUN, id, userSession.getString("token", ""));
        Log.i("w", WS_URL);
        testPopup = new PopupWindow(testPUV, width, height, true);

        info = findViewById(R.id.infoButton);
        submit = findViewById(R.id.submitButton);
        loadDefault = findViewById(R.id.loadDefaultButton);
        loadDefault.setOnClickListener(view -> {
            ide.setText(baseCode);
        });

        info.setOnClickListener(this);
        submit.setOnClickListener(this);

        getAssignment();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAssignment();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == info.getId()) {
            infoPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            infoPUV.setOnTouchListener((v, event) -> {
                v.performClick();
                infoPopup.dismiss();
                return true;
            });
        } else if (view.getId() == submit.getId()) {
            startTests(view);
            /*

            popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.PURPLE_COLOR));
            String loadingString = "Performing Tests...";

            results.setText(loadingString);
            progressBar.setVisibility(View.VISIBLE);
            String urlRun = String.format(Const.RUN_CODE, id, userSession.getString("token", ""));
            JSONObject obj = new JSONObject();
            String className = AssignmentCreationDataHolder.getName()
                    .replaceAll(" ", "_").toLowerCase();
            try {
                obj.put("name", "name" + ".java");
                obj.put("contents", ide.getText().toString().replaceAll("\"", ("\\" + "\"")));
                obj.put("language", "Java");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("", obj.toString());
             * I want it to start listening to the web socket once it sends over the
             * code we want to test, and I want it to close once we get a message
             * from the server saying it sent all of the tests over or something
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, urlRun, obj,
                    res -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            String message;
                            if (res.getBoolean("pass")) {
                                testingCodeTitleStatusTextView.setText("PASSED");
                                popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.GREEN_COLOR));
                                message = "Expected : " + res.getString("expectedOutput") + " " +
                                        "Actual : " + res.getString("actualOutput");
                            } else if (res.getString("message").equals("Compilation failed")) {
                                popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.RED_COLOR));
                                message = "Compile Error!";
                            } else {
                                testingCodeTitleStatusTextView.setText("FAIL");
                                popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.RED_COLOR));
                                message = "Expected : " + res.getString("expectedOutput") + " " +
                                        "Actual : " + res.getString("actualOutput");
                            }
                            results.setText(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, err -> {
                testingCodeTitleStatusTextView.setText("FAIL");
                popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.RED_COLOR));
                results.setText("That didn't work!");
                progressBar.setVisibility(View.INVISIBLE);
            });
            AppController.getInstance().addToRequestQueue(req);
            testPopup.setTouchable(true);
            testPopup.setFocusable(true);
            testPopup.setOnDismissListener(() -> {
                if (cc.isOpen()){
                    cc.close();
                }});
            testPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            startTests();

             */
        }
    }

    private void getAssignment() {
        String url = String.format(Locale.ENGLISH, Const.GET_ASSIGNMENT, id, userSession.getString("token", ""));
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    assignmentData = response;
                    Log.i("DATA", assignmentData.toString());
                    try {
                        assignmentName.setText(assignmentData.getString("title"));
                        statementTextView.setText(assignmentData.getString("problemStatement"));
                        descText = assignmentData.getString("description");
                        description.setText(descText);
                        assignmentNamePopupText.setText(assignmentData.getString("title"));
                        ide.setText(assignmentData.getString("template"));
                        baseCode = assignmentData.getString("template");
                        language = assignmentData.getString("language");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Log.i("resp", error.toString());
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    public void startTests(View view){
        Draft[] drafts = {
                new Draft_6455()
        };
        testPopup.setTouchable(true);
        testPopup.setFocusable(true);
        testPopup.setOnDismissListener(() -> {
            if (cc.isOpen()){
                cc.close();

            }});
        testPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
        Log.d("WS", "Attempting Contact...");
        results.setText("Waiting for WebSocket server...");
        popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.PURPLE_COLOR));
        try{
            cc = new WebSocketClient(new URI(WS_URL), drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    try {
                        JSONObject jsonMsg = new JSONObject(message);
                        if (jsonMsg.getString("message").equals("Compilation failed")){
                            runOnUiThread(() -> {
                                results.setText("Compilation Failed!");
                                popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.RED_COLOR));

                            });
                        }
                        else {
                            int testId = jsonMsg.getJSONObject("unitTest").getInt("id");
                            String expected = jsonMsg.getJSONObject("unitTest").getString("expectedOutput");
                            String actual = jsonMsg.getString("actualOutput");
                            boolean pass = jsonMsg.getBoolean("passed");
                            runOnUiThread(() -> {
                                String s = results.getText().toString();
                                if (pass) {
                                    results.setText(s + "\nTest " + testId + " passed");
                                } else {
                                    results.setText(s + "\nTest " + testId + " failed, expected: " + expected + " got: " + actual);
                                    popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.RED_COLOR));
                                }
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                            });
                        }
                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                    }



                }

                @Override
                public void onOpen(ServerHandshake handshake) {

                    Log.d("OPEN", "run() returned: " + "is connecting");
                    runOnUiThread(() -> results.setText("Starting tests..."));
                    JSONObject obj2 = new JSONObject();
                    String ext = "";
                    switch (language){
                        case "Java":
                            ext = "CodeJava.java";
                            break;
                        case "Python":
                            ext = "CodePython.py";
                            break;
                        case "C":
                            ext = "CodeC.c";
                            break;
                        case "Go":
                            ext = "CodeGo.go";
                            break;
                        default:
                            Log.i("no lang","nah");
                            ext = "CodeJava.java";
                            break;
                    }
                    try {
                        obj2.put("name", ext);
                        obj2.put("contents", ide.getText().toString().replaceAll("\"", ("\\" + "\"")));
                        obj2.put("language", language);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sendMessage(obj2.toString());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage());
            e.printStackTrace();
        }
        cc.connect();
    }
    public void sendMessage(String msg){
        try {
            cc.send(msg);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", ""+e.getMessage());
        }
    }
}