package edu.ubb.marp.ui;
/*proba*/
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AssignmentsActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("This is the Assignements tab");
        setContentView(textview);
    }
}