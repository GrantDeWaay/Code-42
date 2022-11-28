package edu.iastate.code42.objects;

import android.widget.EditText;

public class UnitTestPair {
    private EditText valueIn;
    private EditText expectedOut;

    public UnitTestPair(EditText valueIn, EditText expectedOut){
        this.valueIn = valueIn;
        this.expectedOut = expectedOut;
    }

    public String getValueIn(){return valueIn.getText().toString();}
    public String getExpectedOut(){return expectedOut.getText().toString();}
    public String toString(){
        return String.format("{\"value\": \"%s\", \"expectedOut\": \"%s\"}",
                valueIn.getText().toString(),
                expectedOut.getText().toString());
    }
}
