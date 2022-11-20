package edu.iastate.code42.utils;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.code42.R;

public class UnitTestAdapter extends RecyclerView.Adapter<UnitTestAdapter.UnitRowHolder> {

    private String[] localDataSet;
    private static List<UnitRowHolder> rows = new ArrayList<>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public static class UnitRowHolder extends RecyclerView.ViewHolder {
        private final EditText UnitTestValEditText;
        private final EditText UnitTestOutEditText;

        public UnitRowHolder(View view) {
            super(view);
            UnitTestValEditText = (EditText) view.findViewById(R.id.UnitTestValEditText);
            UnitTestOutEditText = (EditText) view.findViewById(R.id.UnitTestOutEditText);
            rows.add(this);
        }
        public String[] getText(){
            return new String[]{UnitTestValEditText.getText().toString(), UnitTestOutEditText.getText().toString()};
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public UnitTestAdapter() {
        //localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UnitRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.unit_test_row_item, viewGroup, false);

        return new UnitRowHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(UnitRowHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        /*
        viewHolder.getTextView().setText(localDataSet[position]);

        viewHolder.getTextView2().setText(localDataSet[position]);

         */
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rows.size();
    }

    public List<UnitRowHolder> getRows(){
        return rows;
    }

    public String[][] getData(){
        int num = getItemCount();
        String[][] allData = new String[2][num];
        for(int i = 0; i < num; i++){
            String[] x = rows.get(i).getText();
            allData[0][num] = x[0];
            allData[1][num] = x[1];
        }
        return allData;
    }
}
