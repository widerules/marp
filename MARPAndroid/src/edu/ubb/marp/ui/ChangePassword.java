package edu.ubb.marp.ui;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Vizer Arnold
 * 
 */
public class ChangePassword extends View {
	/* the text views represents the text over the edit texts */
	TextView oldPassText;
	TextView newPassText;
	TextView newPassText2;
	/* in the old pass you can type the old password */
	EditText oldPass;
	/* in this 2 edit text you cant type and retype the new password */
	EditText newPass1;
	EditText newPass2;
	/* the layout */
	LinearLayout linear;

	/**
	 * 
	 * @param Context
	 *            of the view initializes the attributes
	 */
	public ChangePassword(Context context) {
		super(context);
		/* creates the layout, textViews, and EditTexts, and sets the TextViews */
		linear = new LinearLayout(context);

		oldPassText = new TextView(context);
		oldPassText.setText("Old password");
		newPassText = new TextView(context);
		newPassText.setText("New password");
		newPassText2 = new TextView(context);
		newPassText2.setText("New password again");

		oldPass = new EditText(context);
		newPass1 = new EditText(context);
		newPass2 = new EditText(context);

		oldPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		newPass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		newPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

		linear.setOrientation(LinearLayout.VERTICAL);
		linear.addView(oldPassText);
		linear.addView(oldPass);
		linear.addView(newPassText);
		linear.addView(newPass1);
		linear.addView(newPassText2);
		linear.addView(newPass2);
	}

	/**
	 * 
	 * @return the view
	 */
	public View returnView() {
		return linear;
	}

	/**
	 * 
	 * @return the old password text
	 */
	public String getOldPass() {
		return oldPass.getText().toString();
	}

	/**
	 * 
	 * @return the new password text
	 */
	public String getNewPass1() {
		return newPass1.getText().toString();
	}

	/**
	 * 
	 * @return the retyped new password text
	 */
	public String getNewPass2() {
		return newPass2.getText().toString();
	}
}
