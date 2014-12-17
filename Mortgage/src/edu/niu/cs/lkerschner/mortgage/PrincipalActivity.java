

package edu.niu.cs.lkerschner.mortgage;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*

Assignment 2
Tabbed Applications 100 points

In this assignment you will write a tab bar application to calculate information for a fixed-rate mortgage.
Program
Create a tabbed application with two tabs. 
One tab will be used to compute the monthly payment and total amount paid (principal plus interest) for a mortgage loan. 
The other will be used to compute the remaining principal after N payments have been made.
Monthly Payment and Total Amount Paid Tab

This tab will have three values that need to be entered by the user, and two computed values that will need to be displayed.
We’ll assume a typical conventional loan where the interest is compounded monthly. The following values are entered by the user:
 A = principal, the initial amount of the loan (in dollars and cents)
 I = the annual interest rate (from 1 to 100 percent, e.g. entering 4.25 means 4.25%)
 L = length, the length (in years) of the loan, or at least the length over which the loan is amortized (see Notes below)

We’ll define two more variables to make the calculations easier:
 J = monthly interest in decimal form = I / (12 * 100)
 N = number of months over which loan is amortized = L * 12 The formula to find M, the monthly payment amount, is:
 M = A * (J / (1 - (1 + J)-N))
The total amount paid is simply the monthly payment amount times the number of months over which the loan is amortized.

Remaining Principal Tab
This tab will have four values that need to be entered by the user, and will display a single computed value. 
The additional value that will need to be entered by the user is:
 P = the number of monthly payments made (integer number)

The formula to find R, the remaining amount of the principal owed on the loan, is:
 R=A*(1+J)P –(M/J)*((1+J)P –1)
 */

public class PrincipalActivity extends ActionBarActivity 
{
	// global variables
	EditText etPrinAmount, etRate, etLoanLength, etPaymentsMade;
	TextView tvHeader, tvRemainPrin;
	String Rmsg1copy;
	Button Calculate, Clear;	
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_principal);
	        	     
	     // connect java and xml definitions
	        etPrinAmount = (EditText) findViewById(R.id.etPrinAmount);
	        etRate = (EditText) findViewById(R.id.etRate);
	        etLoanLength = (EditText) findViewById(R.id.etLoanLength);
	        etPaymentsMade = (EditText) findViewById(R.id.etPaymentsMade);
	        tvRemainPrin = (TextView) findViewById(R.id.tvRemainPrin);
	        tvHeader = (TextView) findViewById(R.id.tvHeader);
	        Calculate = (Button) findViewById(R.id.calcBtn);
	        Clear = (Button) findViewById(R.id.clearBtn);
	        
	     // copy message for Clear button to restore later
	        Rmsg1copy = tvRemainPrin.getText().toString(); 	       
	        
	    }
	 
	// principalcalc is a method triggered by the Calculate button using onClick in XML.
	    
	 public void principalcalc (View v)
	 { // begin principalcalc method
		
		 // define local variables 			
			double A = 0, I = 0, L = 0, M = 0, T = 0, N = 0, J = 0, P = 0, R = 0;			
			
	 	DecimalFormat df = new DecimalFormat("#.00");	 	        
     	 		 	
	 // check for empty field, ask for values if any found
	 	
	 if (etPrinAmount.getText().toString().equals("") ||
		etRate.getText().toString().equals("") ||
		etLoanLength.getText().toString().equals("") ||
		etPaymentsMade.getText().toString().equals(""))
	 	
	 	{	
	 	tvHeader.setText(" Please enter all amounts");
	 	}
	 else
	 	{ // begin else
		 
	 // retrieve mortgage values
	 	A = Double.parseDouble(etPrinAmount.getText().toString());
	 	I = Double.parseDouble(etRate.getText().toString());
	 	L = Double.parseDouble(etLoanLength.getText().toString());
	 	P = Double.parseDouble(etPaymentsMade.getText().toString());
	 	tvHeader.setText("");
	 	
	 // calculate remaining principal R	
	 	J = I / (12 * 100);
	 	N = L * 12;
	 	M = A * (J / (1 - (Math.pow((1 + J), -N))));
	 	T = M * N;
	 	R = A * (Math.pow((1 + J), P)) - (M / J) * ((Math.pow((1 + J), P)) - 1);
	 	
	 	tvRemainPrin.setText(" Remaining Principal is " + df.format(R));
	 
	 	} // end else
	 } // end principalcalc method

	// start clearall when Clear button is pressed
	 public void clearall (View v)
	 { 
	 	etPrinAmount.setText(""); etRate.setText(""); etLoanLength.setText(""); 
	 	etPaymentsMade.setText(""); tvHeader.setText("");
	 	tvRemainPrin.setText(Rmsg1copy);
	 } 
	 //end clearall

	 // Back button is pressed
	 public void GoBack (View v)
	 {
		 finish();
	 }
	 // end GoBack
} 
// end PrincipalActivity
