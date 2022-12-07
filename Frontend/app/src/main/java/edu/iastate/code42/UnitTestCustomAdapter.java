package edu.iastate.code42;


import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.iastate.code42.objects.UnitTestPair;

public class UnitTestCustomAdapter extends RecyclerView.Adapter<UnitTestCustomAdapter.ViewHolder> {

    private String[] localDataSet;
    private static ArrayList<UnitTestPair> UnitTests = new ArrayList<>();
    private static int instanceCount = 0;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    @SuppressLint("NotifyDataSetChanged")
    public void add(String x) {
        int n = getItemCount();
        String newarr[] = new String[n + 1];
        for (int i = 0; i < n; i++)
            newarr[i] = this.localDataSet[i];

        newarr[n] = x;

        this.localDataSet = newarr;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText valEditText;
        private final EditText outEditText;
        private final int count;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            count = instanceCount;
            instanceCount++;
            valEditText = view.findViewById(R.id.UnitTestValEditText);
            outEditText = view.findViewById(R.id.UnitTestOutEditText);

            UnitTests.add(new UnitTestPair(valEditText, outEditText));

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public UnitTestCustomAdapter(String[] dataSet) {
        localDataSet = dataSet;
        UnitTestCustomAdapter.UnitTests.clear();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.unit_test_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {}

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static String[] getUnitTests(){
        String[] UnitTestsReturn = new String[UnitTests.size()];
        for(int i = 0; i < UnitTests.size(); i++){
            UnitTestsReturn[i] = UnitTests.get(i).toString();
        }
        return UnitTestsReturn;
    }
    public static JSONArray getJSONUnitTests() throws JSONException {

        String lol = "[";
        for(int i = 0; i < UnitTests.size(); i++){
            if (i == UnitTests.size()-1){
                lol = lol + UnitTests.get(i).JSONtoString(' ');
            }
            else {
                lol = lol + UnitTests.get(i).JSONtoString(',');
            }
        }
        lol = lol + "]";
        JSONArray objT = new JSONArray(lol);
        Log.i("ohh", objT.toString());
        return objT;
    }
}