package edu.niu.cs.lkerschner.lines;

/*
 * Assignment 1: 
 * Write an app that calculates the midpoint of a line segment (distance between the two points). 
 * Make your output clear and only print 3 digits to the right of the decimal point. 
 * Make sure that you test your program using x and y coordinates in all 4 quadrants.
 */
import java.text.DecimalFormat;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LinesActivity extends Activity 
{ // begin class LinesActivity

// Global variables are defined here.	
Button Results,Reset;
double X1 = 0.0,X2 = 0.0,Y1 = 0.0,Y2 = 0.0;
EditText etX1, etX2, etY1, etY2;
TextView tvExplain;
String tvExplainCopy;

@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);
 //When activity Lines is created, the connection between Java and XML variables is made.       
    etX1 = (EditText) findViewById(R.id.etX1);
    etX2 = (EditText) findViewById(R.id.etX2);
    etY1 = (EditText) findViewById(R.id.etY1);
    etY2 = (EditText) findViewById(R.id.etY2);
    tvExplain = (TextView) findViewById(R.id.tvExplain);    
    Results = (Button) findViewById(R.id.btnResults);
    Reset = (Button) findViewById(R.id.btnReset); 
    // Make a copy for the Reset button to use later.
    tvExplainCopy = tvExplain.getText().toString();
    }   // end onCreate 
 
// calcline is a method that is triggered when the Results button is pressed. onClick is used in the button's XML.
public void calcline (View v)
{ //begin calcline method
	// For 3 digits to the right of the decimal point
	DecimalFormat df = new DecimalFormat("#.000"); 
	
	double distance, midpointX, midpointY;
//Check for any empty input fields. Ask for correct input if any found.	

if (etX1.getText().toString().equals("") || etX2.getText().toString().equals("") ||
  	etY1.getText().toString().equals("") || etY2.getText().toString().equals(""))
{
		Toast.makeText(this, "Please enter a number for each value.", Toast.LENGTH_SHORT).show();
}
	else
	{
// Retrieve input line points X1.Y1, X2.Y2		
	X1 = Double.parseDouble(etX1.getText().toString()); 
	X2 = Double.parseDouble(etX2.getText().toString());
	Y1 = Double.parseDouble(etY1.getText().toString());
	Y2 = Double.parseDouble(etY2.getText().toString());

// distance(p,q) = square root of ((x1 - x2) squared + (y1 - y2) squared) 	
	distance = (Math.sqrt((Math.pow((X1 - X2), 2)) + Math.pow((Y1 - Y2), 2)));
// midpoint(p,q) = ((x1 + x2)/2, (y1 + y2)/2)	
	midpointX = ((X1 + X2) / 2);
	midpointY = ((Y1 + Y2) / 2);	

	tvExplain.setText (" Distance is " + df.format(distance) + "   Midpoint X,Y: " + df.format(midpointX) + "," + df.format(midpointY));
	}
} //end calcline method
public void resetall (View v)
{ //begin method resetall triggered by XML defining the Reset button
etX1.setText(""); etY1.setText(""); etX2.setText("");etY2.setText("");tvExplain.setText(tvExplainCopy);
}// end method resetall
};
 // end class LinesActivity
            
    
    
    
    
    
    
