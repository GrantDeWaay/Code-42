package edu.iastate.code42;


import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UnitTestCustomAdapter extends RecyclerView.Adapter<UnitTestCustomAdapter.ViewHolder> {

    private static ArrayList<String> data1 = new ArrayList<String>();
    private static ArrayList<String> data2 = new ArrayList<String>();
    private static int instanceCount = 0;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    @SuppressLint("NotifyDataSetChanged")
    public void add(String x) {

        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final EditText UnitTestValEditText;
        private final EditText UnitTestOutEditText;
        private final int count;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            count = instanceCount;
            instanceCount++;
            data1.add("value");
            data2.add("output");
            TextWatcher updateData1 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    data1.set(count, charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            };
            TextWatcher updateData2 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    data2.set(count, charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

            UnitTestValEditText = view.findViewById(R.id.UnitTestValEditText);
            UnitTestOutEditText = view.findViewById(R.id.UnitTestOutEditText);

            UnitTestValEditText.addTextChangedListener(updateData1);
            UnitTestOutEditText.addTextChangedListener(updateData2);

        }
        public EditText[] getEditText(){
            return new EditText[]{UnitTestValEditText, UnitTestOutEditText};
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public UnitTestCustomAdapter() {
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

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
        return 3;
    }

    public String[] getDataEquals(){
        String[] temp = new String[0];
        data2.toArray(temp);
        return temp;
    }
    public String[] getDataExpect() {
        String[] temp = new String[0];
        data2.toArray(temp);
        return temp;
    }
}