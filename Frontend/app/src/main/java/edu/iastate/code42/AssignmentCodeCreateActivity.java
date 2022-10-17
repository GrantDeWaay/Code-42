package edu.iastate.code42;

import static edu.iastate.code42.utils.Const.CREATE_ASSIGNMENT;
import static edu.iastate.code42.utils.Const.SOURCE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.code42.app.AppController;

public class AssignmentCodeCreateActivity extends AppCompatActivity {
    private Button goNext;
    private EditText teacherBaseCode;
    private EditText expectedOut;
    private EditText definedVar;
    private TableLayout t1;
    private EditText studentBaseCode;
    private int suc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_code_create);
        definedVar = findViewById(R.id.definedVar);
        expectedOut = findViewById(R.id.expectedOut);
        goNext = findViewById(R.id.goNext);
        teacherBaseCode = findViewById(R.id.teacherBaseCode);
        studentBaseCode = findViewById(R.id.studentBaseCode);

        goNext.setOnClickListener(v -> {
            try {
                makeJsonObjReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (suc == 1) {
                finish();
            }
        });
        teacherBaseCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                studentBaseCode.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void makeJsonObjReq() throws JSONException {
        // Instantiate the RequestQueue.
        JSONObject obj = new JSONObject();
        obj.put("x", definedVar.getText());
        obj.put("code", studentBaseCode.getText());
        String url = SOURCE + "CREATE_ASSIGNMENT";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, obj,
                response -> {
                    suc = 1;
                }, error -> {
            suc = 1;
        });
        AppController.getInstance().addToRequestQueue(req);
    }
}