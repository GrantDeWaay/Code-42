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

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.objects.User;
import edu.iastate.code42.utils.Const;
import edu.iastate.code42.utils.WebSocketRunUnitTests;

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
    private final String webs = "wss://socketsbay.com/wss/v2/1/demo/";
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

        testPUV = inflater.inflate(R.layout.popup_window_submission_tests, null);

        testingCodeTitleStatusTextView = testPUV.findViewById(R.id.testingCodeTitleStatusTextView);
        popupRelativeLayout = testPUV.findViewById(R.id.popupRelativeLayout);
        results = testPUV.findViewById(R.id.testResultsTextView);
        progressBar = testPUV.findViewById(R.id.progressBar);

        user = User.get(getApplicationContext());
        userSession = getSharedPreferences(getString(R.string.session_shared_pref), MODE_PRIVATE);

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

            popupRelativeLayout.setBackgroundColor(Color.parseColor(Const.PURPLE_COLOR));
            String loadingString = "Performing Tests...";

            results.setText(loadingString);


            progressBar.setVisibility(View.VISIBLE);
            String urlRun = String.format(Const.RUN_CODE, id, userSession.getString("token", ""));
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", "name" + ".java");
                obj.put("contents", ide.getText().toString().replaceAll("\"", ("\\" + "\"")));
                obj.put("language", "Java");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /**
             * I want it to start listening to the web socket once it sends over the
             * code we want to test, and I want it to close once we get a message
             * from the server saying it sent all of the tests over or something
             */
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

            //WebSocketRunUnitTests.sendMessage();
            testPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            testPopup.setFocusable(true);
            testPopup.update();

            startTests();
            testPUV.setOnTouchListener((v, event) -> {
                v.performClick();
                testPopup.dismiss();
                cc.close();
                return true;
            });
        }
    }

    private void getAssignment() {
        id = getIntent().getIntExtra("id", -1);

        String url = String.format(Locale.ENGLISH, Const.GET_ASSIGNMENT, id, userSession.getString("token", ""));
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    assignmentData = response;
                    try {
                        assignmentName.setText(assignmentData.getString("title"));
                        statementTextView.setText(assignmentData.getString("problemStatement"));
                        descText = assignmentData.getString("description");
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
    }

    public void startTests(){
        Draft[] drafts = {
                new Draft_6455()
        };
        try{
            cc = new WebSocketClient(new URI(webs), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s = results.getText().toString();
                            results.setText(s + "\nServer:" + message);
                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                        }
                    });

                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    sendMessage("Hey there!");
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



    public int sendMessage(String msg){
        if (cc.isClosed()){
            return 1;
        }
        try {
            cc.send(msg);
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", ""+e.getMessage());
        }
        return 0;
    }
}



