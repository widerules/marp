package edu.ubb.marp.ui;

import java.util.StringTokenizer;

import edu.ubb.marp.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 
 * @author Vizer Arnold
 * 
 */

public class RowElement extends View {

	/* This tool shows the used ratio of a resource */
	TextView ratioText;
	/* dateText shows the date when the resource works */
	TextView dateText;
	/* percent shows the edited ratio */
	TextView percent;
	/* needed shows the final edited ratio */
	TextView needed;
	/* button up can access the user to edit the ratio */
	Button buttonUp;
	/*
	 * button down access the user to other functionalities about applying the
	 * selected ratio
	 */
	Button buttonDown;
	/* ratioButtonUp add 10 % to the needed ratio */
	Button ratioButtonUp;
	/* ratioButtonDown remove 10% from the needed ratio */
	Button ratioButtonDown;
	/* The main layout */
	TableLayout a;
	/*
	 * row contains the add remove buttons and the editable ratio textview
	 */
	TableRow row;
	/*
	 * row 2 contains the buttonUp button. inicially the row is invisible, but
	 * when the user click on this button row1 will be visible.
	 */
	TableRow row2;
	/* row3 contains the ratioText and is visible */
	TableRow row3;
	/* row4 contains the needed TextView and is visible */
	TableRow row4;
	/* row5 contains the dateText TextView, and is visible */
	TableRow row5;
	/* row6 contains the buttonDown button, which is visible */
	TableRow row6;
	/*
	 * row7 contains the RadioButtonGroup and inicially is invisible. when the
	 * user click on buttonDown, row6 change visibility to visible
	 */
	TableRow row7;
	/*
	 * row8 contains the apply to this button, which set the ratio only to this
	 * week
	 */
	TableRow row8;
	/*
	 * row9 contains the applyToAll button, which set the edited ratio to all
	 * weeks from this week to the end week
	 */
	TableRow row9;
	/* when the acceptMyRatio is selected, the edited ratio will be set */
	RadioButton acceptMyRatio;
	/*
	 * when the accept is selected, automatically the edited ratio will be the
	 * free ratio
	 */
	RadioButton accept;
	/* when the dontAccept is selected, automatically set the needed ratio to 0 */
	RadioButton dontAccept;
	/*
	 * when the sendRequest is selected, all the weeks in red will be colored in
	 * yellow, to send request on that week
	 */
	RadioButton sendRequest;
	/*
	 * when the user click on this applyToAll button the selected ratio will be
	 * set on the other weeks started from that week, which has the button and
	 * is implemented from outer class
	 */
	Button applyToAll;
	/*
	 * when the user click on this button, the selected ratio will be set just
	 * on this week
	 */
	Button applyToThis;
	/* radio group contains the radio buttons */
	RadioGroup group;
	/* width contains the screen width */
	int width = 0;
	/* height contains the screen height */
	int height = 0;
	/*
	 * original is set when the class is called, and will be the used ratio of
	 * the resource
	 */
	int original = 0;
	int originalNeeded = 0;
	/* will be true when the button is clicked */
	boolean buttonUpClicked = false;
	/* will be true when the button is clicked */
	boolean buttonDownClicked = false;
	/* will be true when the background color is red */
	boolean isRed = false;
	/* will be true when the background color is green */
	boolean isGreen = false;
	/* will be true when the background color is yellow */
	boolean isYellow = false;
	/* other Row elements */
	boolean modify = false;
	RowElement elements[];
	/* this is the index of copy about this class */
	int myIndex = 0;
	/* number of elements */
	int columns = 0;

	/**
	 * The constructor set the initial state of the views
	 * 
	 * @param context
	 *            is the context of the view
	 * @param display
	 *            is the display of the screen
	 */

	public RowElement(Context context, Display display) {
		super(context);
		// TODO Auto-generated constructor stub
		a = new TableLayout(context);
		needed = new TextView(context);
		percent = new TextView(context);
		ratioText = new TextView(context);
		dateText = new TextView(context);
		buttonUp = new Button(context);
		buttonDown = new Button(context);
		ratioButtonUp = new Button(context);
		ratioButtonDown = new Button(context);
		acceptMyRatio = new RadioButton(context);
		accept = new RadioButton(context);
		dontAccept = new RadioButton(context);
		sendRequest = new RadioButton(context);
		applyToAll = new Button(context);
		applyToThis = new Button(context);
		group = new RadioGroup(context);
		width = display.getWidth() / (2);
		height = display.getHeight() / 10;

		needed.setText("You want: 0 %");
		needed.setWidth(width);
		needed.setHeight(height);
		needed.setTextColor(Color.BLACK);
		needed.setGravity(Gravity.CENTER_HORIZONTAL);

		ratioButtonDown.setMaxWidth(width / 4);
		ratioButtonDown.setMaxHeight(height);
		ratioButtonDown.setBackgroundResource(R.drawable.buttondown48);
		ratioButtonDown.setGravity(Gravity.CENTER);

		ratioButtonUp.setMaxWidth(width / 4);
		ratioButtonUp.setMaxHeight(height);
		ratioButtonUp.setBackgroundResource(R.drawable.buttonup48);
		ratioButtonUp.setGravity(Gravity.CENTER);

		percent.setWidth(width / 2);
		percent.setText("0 %");
		percent.setHeight(height);
		percent.setGravity(Gravity.CENTER);
		percent.setTextColor(Color.BLACK);

		ratioText.setText("Total: 60 %");
		ratioText.setWidth(width);
		ratioText.setTextColor(Color.BLACK);
		ratioText.setHeight(height);
		ratioText.setGravity(Gravity.CENTER_HORIZONTAL);

		dateText.setText("Date: 2012.03.11");
		dateText.setTextColor(Color.BLACK);
		dateText.setWidth(width);
		dateText.setHeight(height);
		dateText.setGravity(Gravity.CENTER_HORIZONTAL);

		acceptMyRatio.setText("Accept my %");
		acceptMyRatio.setWidth(width);
		acceptMyRatio.setBackgroundColor(Color.TRANSPARENT);
		acceptMyRatio.setHeight(height);
		acceptMyRatio.setTextColor(Color.BLACK);
		acceptMyRatio.setId(4);

		accept.setText("Accept the Available %");
		accept.setWidth(width);
		accept.setBackgroundColor(Color.TRANSPARENT);
		accept.setHeight(height);
		accept.setTextColor(Color.BLACK);
		accept.setId(1);

		dontAccept.setText("Don't Acctept");
		dontAccept.setWidth(width);
		dontAccept.setBackgroundColor(Color.TRANSPARENT);
		dontAccept.setHeight(height);
		dontAccept.setTextColor(Color.BLACK);
		dontAccept.setId(2);

		sendRequest.setText("Send request");
		sendRequest.setBackgroundColor(Color.TRANSPARENT);
		sendRequest.setWidth(width);
		sendRequest.setHeight(height);
		sendRequest.setTextColor(Color.BLACK);
		sendRequest.setId(3);

		applyToAll.setText("Apply to all ->");
		applyToAll.setWidth(width);
		applyToAll.setHeight(height);
		applyToAll.setTextColor(Color.BLACK);

		applyToThis.setText("Apply to this");
		applyToThis.setWidth(width);
		applyToThis.setHeight(height);
		applyToThis.setTextColor(Color.BLACK);

		group.setBackgroundColor(Color.TRANSPARENT);
		group.addView(acceptMyRatio);
		group.addView(accept);
		group.addView(dontAccept);
		group.addView(sendRequest);

		buttonUp.setWidth(width);
		buttonUp.setHeight(height);
		buttonUp.setText("Change Ratio");

		buttonDown.setWidth(width);
		buttonDown.setHeight(height);
		buttonDown.setText("More Options");

		a.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));

		row = new TableRow(context);
		LinearLayout l = new LinearLayout(context);
		l.addView(ratioButtonDown);
		l.addView(percent);
		l.addView(ratioButtonUp);
		row.addView(l);
		a.addView(row);

		row2 = new TableRow(context);
		row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row2.addView(buttonUp);
		a.addView(row2);

		row3 = new TableRow(context);
		row3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row3.addView(ratioText);
		a.addView(row3);

		row4 = new TableRow(context);
		row4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row4.addView(needed);
		a.addView(row4);

		row5 = new TableRow(context);
		row5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row5.addView(dateText);
		a.addView(row5);

		row6 = new TableRow(context);
		row6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row6.addView(buttonDown);
		a.addView(row6);

		row7 = new TableRow(context);
		row7.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row7.addView(group);
		a.addView(row7);

		row8 = new TableRow(context);
		row8.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row8.addView(applyToThis);
		a.addView(row8);
		row9 = new TableRow(context);
		row9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
		row9.addView(applyToAll);
		a.addView(row9);
		setWhite();
		hideAcceptItems();
		hideRatioItems();

		buttonUp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (buttonDownClicked) {
					buttonDownShowHide();
					buttonUpShowHide();
				} else {
					buttonUpShowHide();
				}
			}
		});
		buttonDown.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (buttonUpClicked) {
					buttonUpShowHide();
					buttonDownShowHide();
				} else {
					buttonDownShowHide();
				}
			}
		});
		ratioButtonUp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				countRatio();
			}
		});
		ratioButtonDown.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				deCountRatio();
			}
		});
		applyToThis.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				applyToThisClick();
			}
		});

		row8.setBackgroundResource(R.drawable.whitemiddle48);
		row9.setBackgroundResource(R.drawable.accept48);
		row.setBackgroundResource(R.drawable.moreoptions48);
		group.setBackgroundResource(R.drawable.whitemiddle48);
		accept.setBackgroundColor(Color.TRANSPARENT);
		dontAccept.setBackgroundColor(Color.TRANSPARENT);
		sendRequest.setBackgroundColor(Color.TRANSPARENT);
		StringTokenizer st = new StringTokenizer(ratioText.getText().toString());
		st.nextToken();
		original = Integer.parseInt(st.nextToken());
	}

	/**
	 * Load the array list of the elements
	 * 
	 * @param array
	 *            is the array of the elements
	 * @param column
	 *            is the number of the elements
	 * @param index
	 *            is the index of the current element from the array
	 */
	public void setElements(RowElement array[], int column, int index) {
		myIndex = index;
		columns = column;
		elements = array;
	}

	/**
	 * set the modify attribute
	 * 
	 * @param modify
	 * 
	 */
	public void setModify(boolean modify) {
		this.modify = modify;
	}

	/**
	 * called to change the appropriate elements visibility to invisible
	 */
	private void setAllHide() {
		buttonDownClicked = false;
		buttonUpClicked = false;
		hideAcceptItems();
		hideRatioItems();
	}

	/**
	 * called to hide the Upside items
	 */
	private void hideRatioItems() {

		row.setVisibility(row.INVISIBLE);
		buttonUp.setBackgroundResource(R.drawable.moreoptions48);
	}

	/**
	 * called to hide the bottom items
	 */
	private void hideAcceptItems() {
		row7.setVisibility(group.INVISIBLE);
		row8.setVisibility(applyToThis.INVISIBLE);
		row9.setVisibility(applyToAll.INVISIBLE);
		buttonDown.setBackgroundResource(R.drawable.accept48);
	}

	/**
	 * called to set visibility on visible to the upside items
	 */
	private void showRatioItems() {
		row.setVisibility(row.VISIBLE);
		buttonUp.setBackgroundResource(R.drawable.whitetableup48);
	}

	/**
	 * called to set visibility on visible to the bottom items
	 */
	private void showAcceptItems() {
		row7.setVisibility(group.VISIBLE);
		row8.setVisibility(applyToThis.VISIBLE);
		row9.setVisibility(applyToAll.VISIBLE);
		buttonDown.setBackgroundResource(R.drawable.whitetabledown48);

	}

	/**
	 * called to set the background white
	 */
	private void setWhite() {
		needed.setBackgroundResource(R.drawable.whitemiddle48);
		ratioText.setBackgroundResource(R.drawable.whitetableup48);
		dateText.setBackgroundResource(R.drawable.whitetabledown48);
		isRed = false;
		isGreen = false;
		isYellow = false;
	}

	/**
	 * called to set the background red
	 */
	public void setRed() {
		needed.setBackgroundResource(R.drawable.redmiddle48);
		ratioText.setBackgroundResource(R.drawable.redtableup48);
		dateText.setBackgroundResource(R.drawable.redtabledown48);
		isRed = true;
		isGreen = false;
		isYellow = false;
	}

	/**
	 * called to set the background green
	 */
	public void setGreen() {
		needed.setBackgroundResource(R.drawable.greenmiddle48);
		ratioText.setBackgroundResource(R.drawable.greentableup48);
		dateText.setBackgroundResource(R.drawable.greentabledown48);
		isRed = false;
		isGreen = true;
		isYellow = false;
	}

	/**
	 * called to set the background yellow
	 */
	public void setYellow() {
		needed.setBackgroundResource(R.drawable.yellowmiddle48);
		ratioText.setBackgroundResource(R.drawable.yellowtableup48);
		dateText.setBackgroundResource(R.drawable.yellowtabledown48);
		isRed = false;
		isGreen = false;
		isYellow = true;
	}

	/**
	 * 
	 * @return the view
	 */
	public TableLayout returnView() {
		return a;
	}

	/**
	 * called to increase the ratio by 10%
	 */
	private void countRatio() {
		StringTokenizer st = new StringTokenizer(percent.getText().toString());
		Integer number = Integer.parseInt(st.nextToken());
		number = number + 10;
		if (number <= 100 && number >= 0) {
			st = new StringTokenizer(ratioText.getText().toString());
			st.nextToken();
			Integer allRatio = Integer.parseInt(st.nextToken());
			allRatio = allRatio + 10;
			percent.setText(number + " %");
			needed.setText("You want: " + number + " %");
			ratioText.setText("Total: " + allRatio + " %");
			if (allRatio <= 100 && allRatio >= 0) {
				setGreen();
			} else {
				setRed();
			}
		}
	}

	/**
	 * call to decrease the ratio by 10%
	 */
	private void deCountRatio() {
		StringTokenizer st = new StringTokenizer(percent.getText().toString());
		Integer number = Integer.parseInt(st.nextToken());
		number = number - 10;
		if (number <= 100 && number >= 0) {
			st = new StringTokenizer(ratioText.getText().toString());
			st.nextToken();
			Integer allRatio = Integer.parseInt(st.nextToken());
			allRatio = allRatio - 10;
			percent.setText(number + " %");
			needed.setText("You want: " + number + " %");
			ratioText.setText("Total: " + allRatio + " %");
			if (allRatio == original) {
				setWhite();
			} else {
				if (allRatio <= 100 && allRatio >= 0) {
					setGreen();
				} else {
					setRed();
				}
			}
		}
	}

	/**
	 * called when the user click on the Change Ratio button to show or hide the
	 * ratio items
	 */
	public void buttonUpShowHide() {
		if (buttonUpClicked) {
			hideRatioItems();
			buttonUpClicked = false;
		} else {
			for (int i = 0; i < columns; i++) {
				if (i != myIndex) {
					elements[i].setAllHide();
				}
			}
			showRatioItems();
			buttonUpClicked = true;
		}
	}

	/**
	 * Called when the user click on the More Option Button to show or hide the
	 * Radio buttons
	 */
	public void buttonDownShowHide() {

		if (buttonDownClicked) {
			hideAcceptItems();
			clearOptions();
			buttonDownClicked = false;
		} else {
			for (int i = 0; i < columns; i++) {
				if (i != myIndex) {
					elements[i].setAllHide();
				}
			}
			showAcceptItems();
			buttonDownClicked = true;
		}
	}

	/**
	 * Clear all radio button check
	 * */
	private void clearOptions() {
		group.clearCheck();
	}

	/**
	 * called when the user accepted the available ratio, and set the You want
	 * Ratio
	 */
	public void accept() {
		setGreen();
		int available = 100 - original;
		ratioText.setText("Total: 100 %");
		needed.setText("You want: " + available + " %");
		percent.setText(available + " %");
	}

	/**
	 * called when the element need to be in the initial state
	 */
	public void setInitialState() {
		setWhite();
		ratioText.setText("Total: " + original + " %");
		needed.setText("You want: " + originalNeeded + " %");
		percent.setText(originalNeeded + " %");
	}

	/**
	 * is called when the user pressed The apply To All button and set the
	 * current row item
	 */
	public void applyToThisClick() {
		int chackedItem = group.getCheckedRadioButtonId();
		if (chackedItem == 1) {
			accept();
		} else {
			if (chackedItem == 2) {
				setInitialState();
			} else {
				if (chackedItem == 3 && isRed) {
					setYellow();
				}
			}
		}
		buttonDownShowHide();
	}

	/**
	 * 
	 * @return which radio button is checked
	 */
	public int getGroupCheckedRadioButtonId() {
		return group.getCheckedRadioButtonId();
	}

	/**
	 * 
	 * @return the apply to all button
	 */
	public Button getApplyToAllButton() {
		return applyToAll;
	}

	/**
	 * 
	 * @return the radio group, with the radio buttons
	 */
	public RadioGroup getRadioGroup() {
		return group;
	}

	/**
	 * set the date and ratio
	 * 
	 * @param date
	 *            the date of the week
	 * @param ratio
	 *            the total ratio of the user on that week
	 */
	public void setText(String date, String ratio) {
		ratioText.setText("Total: " + ratio + " %");
		dateText.setText("Date: " + date);
		original = Integer.parseInt(ratio);
	}

	/**
	 * 
	 * @return the needed ratio
	 */
	public int getCurrentNeededRatio() {
		StringTokenizer st = new StringTokenizer(percent.getText().toString());
		return (Integer.parseInt(st.nextToken()));
	}

	/**
	 * apply the selected ratio to all other row elements from the array
	 * 
	 * @param ratio
	 *            is the users usage ratio number of a week
	 */
	public void applyMyRatioToAll(int ratio) {
		setInitialState();
		StringTokenizer st = new StringTokenizer(percent.getText().toString());
		StringTokenizer st2 = new StringTokenizer(ratioText.getText().toString());
		st2.nextToken();
		int number = Integer.parseInt(st.nextToken());
		int ratioT = Integer.parseInt(st2.nextToken());

		number = number + ratio;
		if (number <= 100) {
			percent.setText(number + " %");
			ratioText.setText("Total: " + (number + ratioT + " %"));
			needed.setText("You want: " + number + " %");
			if ((ratioT + number) > 100) {
				setRed();
			} else {
				setGreen();
			}
		}

	}

	/**
	 * apply the selected ratio to all other row elements from the array
	 * 
	 * @param ratiois
	 *            the users usage ratio number of a week
	 */
	public void applyMyRatioToAll2(int ratio) {
		setInitialState();
		StringTokenizer st = new StringTokenizer(percent.getText().toString());
		StringTokenizer st2 = new StringTokenizer(ratioText.getText().toString());
		st2.nextToken();
		int number = Integer.parseInt(st.nextToken());
		int ratioT = Integer.parseInt(st2.nextToken());

		number = number - ratio;
		ratioT = ratioT - number;
		number = ratio;
		if (number <= 100) {
			percent.setText(number + " %");
			ratioText.setText("Total: " + ratioT + " %");
			needed.setText("You want: " + number + " %");
			if ((ratioT) > 100) {
				setRed();
			} else {
				setGreen();
			}
		}

	}

	/**
	 * 
	 * @return true if the ratio button is clicked
	 */
	public boolean ratioButtonIsClicked() {
		return buttonUpClicked;
	}

	/**
	 * 
	 * @return true if the more options button is clicked
	 */
	public boolean optionButtonIsClicked() {
		return buttonDownClicked;
	}

	/**
	 * 
	 * @return the date of the element
	 */
	public String getDate() {
		StringTokenizer st = new StringTokenizer(dateText.getText().toString());
		st.nextToken();
		return st.nextToken();
	}

	/**
	 * 
	 * @return the total ratio
	 */
	public String getratio() {
		StringTokenizer st = new StringTokenizer(ratioText.getText().toString());
		st.nextToken();
		return st.nextToken();
	}

	/**
	 * 
	 * @return true if the background is red
	 */
	public boolean isRed() {
		return isRed;
	}

	/**
	 * 
	 * @return true if the background is green
	 */
	public boolean isGreen() {
		return isGreen;
	}

	/**
	 * 
	 * @return true if the background is yellow
	 */
	public boolean isYellow() {
		return isYellow;
	}

	/**
	 * 
	 * @return the text of the selected percent
	 */
	public String getPercentText() {
		return percent.getText().toString();
	}

	/**
	 * set the percent
	 * 
	 * @param s
	 *            is the selected percent
	 */
	public void setPercentText(String s) {
		percent.setText(s);
	}

	/**
	 * 
	 * @return the total ratio
	 */
	public String getRatioText() {
		return ratioText.getText().toString();
	}

	/**
	 * set the ratio text
	 * 
	 * @param s
	 *            is the ratio text
	 */
	public void setRatioText(String s) {
		ratioText.setText(s);
	}

	/**
	 * 
	 * @return the needed ratio
	 */
	public String getNeededText() {
		return needed.getText().toString();
	}

	/**
	 * set the needed ratio
	 * 
	 * @param s
	 *            is the ratio
	 */
	public void setNeededText(String s) {
		needed.setText(s);
	}

	/**
	 * 
	 * @return the date
	 */
	public String getDateText() {
		return dateText.getText().toString();
	}

	/**
	 * set the element's date
	 * 
	 * @param s
	 *            is the date
	 */
	public void setDateText(String s) {
		dateText.setText(s);
	}

	/**
	 * set the original needed ratio
	 * 
	 * @param number
	 *            is the ratio
	 */
	public void setOriginalNeeded(int number) {
		this.originalNeeded = number;
	}
}
