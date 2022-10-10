package edu.iastate.code42;

import static edu.iastate.code42.utils.Const.PYTHON;
import static edu.iastate.code42.utils.Const.SOURCE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.code42.app.AppController;

public class AssignmentWorkActivity extends AppCompatActivity {
    private Button submit;
    private EditText ide;
    private TextView cons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_take);
        submit = findViewById(R.id.runCode);
        ide = findViewById(R.id.TypeIDE);
        cons = findViewById(R.id.Console);
        submit.setOnClickListener(view -> {
            try {
                runP();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
    private void runP() throws JSONException {
        // Instantiate the RequestQueue.
        JSONObject obj = new JSONObject();
        obj.put("code", ide.getText());
        String url = PYTHON;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,obj,
                response -> {
                    cons.setText(response.toString());
                }, error -> {
            cons.setText(error.toString());
        });
        AppController.getInstance().addToRequestQueue(req);
    }
}
