package edu.iastate.code42.objects;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.code42.app.AppController;
import edu.iastate.code42.utils.Const;

/**
 * @author Grant DeWaay
 *
 * Class for storage and sending of assignment data when you are creating the assignments
 */
public class AssignmentCreationDataHolder {

    /**
     * name of the assignment being created.
     */
    private static String name = "";
    /**
     * language of the assignment being created.
     */
    private static String lang = "";
    /**
     * code of the assignment being created.
     */
    private static String code = "";
    /**
     * statment of the assignment being created.
     */
    private static String statement = "";
    /**
     * description of the assignment being created.
     */
    private static String description = "";
    /**
     * expected output of the assignment being created.
     */
    private static String expectedOut = "";
    /**
     * how many points the assignment will be worth
     */
    private static int points;

    /**
     * resets values of the assignment data holder
     * you should call this whenever you start the assignment creation process starts
     */
    public static void resetValues(){
        name = "";
        lang = "";
        code = "";
        statement = "";
        description = "";
        expectedOut = "";
        points = 0;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        AssignmentCreationDataHolder.name = name;
    }

    public static void setLang(String lang) {
        AssignmentCreationDataHolder.lang = lang;
    }
    public static void setPoints(int points) {
        AssignmentCreationDataHolder.points = points;
    }

    public static void setCode(String code) {
        AssignmentCreationDataHolder.code = code;
    }

    public static void setStatement(String statement) {
        AssignmentCreationDataHolder.statement = statement;
    }

    public static void setDescription(String description) {
        AssignmentCreationDataHolder.description = description;
    }

    public static void setExpectedOut(String expectedOut) {
        AssignmentCreationDataHolder.expectedOut = expectedOut;
    }


    /**
     * send the JSONObject returned by formatJSON() to the backend via volley request, creating an assignment.
     * then that same assignment, it adds it to a class.
     * @param token
     * @param courseId
     */
    public static void sendAssignment(Context screen, String token, int courseId) {
        String url = String.format(Const.CREATE_ASSIGNMENT, token);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, formatJSON(),
                response1 -> {
                    Log.i("resp", response1.toString());

                    String addAssignmentURL = "";
                    try {
                        addAssignmentURL = String.format(Const.ADD_ASSIGNMENT_TO_COURSE, courseId, response1.get("id"),
                                token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    StringRequest addAssignmentToCourse = new StringRequest(Request.Method.PUT, addAssignmentURL,
                            response2 -> {
                                Toast.makeText(screen, "Added assignment to course!", Toast.LENGTH_LONG).show();
                            }, error -> Toast.makeText(screen, "Failed to add assignment to course", Toast.LENGTH_LONG).show()) {
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

                    AppController.getInstance().addToRequestQueue(addAssignmentToCourse, "course_get_students");
                }, error -> {
            Log.i("resp", error.toString());
            Toast.makeText(screen, "Failed to add assignment to course", Toast.LENGTH_LONG).show();
        });
        AppController.getInstance().addToRequestQueue(req);
    }


    /**
     * takes the variables from this class and compiles them in a JSONObject
     * for an assignment to be added to the backend
     * @return json object to be sent to backend via volley
     */
    public static JSONObject formatJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", name);
            obj.put("description", description);
            obj.put("problemStatement", statement);
            obj.put("lang", lang);
            obj.put("template", code);
            obj.put("expectedOutput", expectedOut);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
