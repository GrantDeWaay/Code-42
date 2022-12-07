package edu.iastate.code42.objects;

import android.util.Log;
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
        return String.format("{\"input\": \"%s\", \"expectedOutput\": \"%s\"}",
                valueIn.getText().toString(),
                expectedOut.getText().toString());
    }
    public String JSONtoString(char comma){
        return String.format("{\"input\": \"%s\\n\", \"expectedOutput\": \"%s\\n\"}%c",
                valueIn.getText().toString(),
                expectedOut.getText().toString(),
                comma);
    }
}
