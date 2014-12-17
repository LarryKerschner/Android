package edu.niu.cs.lkerschner.mortgage;
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

import java.text.DecimalFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MortgageActivity extends ActionBarActivity 
{  //begin class MortgageActivity
	
	// define global variables 
	Button Calculate, Clear;
	double A = 0, I = 0, L = 0, M = 0, T = 0, N = 0, J = 0;
	TextView tvMonthlyPayment, tvTotalAmt, tvHeader;
	EditText etPrinAmount, etRate, etLoanLength;
	String Tmsg1copy, Mmsg1copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage);
        
// connect java and xml definitions
        etPrinAmount = (EditText) findViewById(R.id.etPrinAmount);
        etRate = (EditText) findViewById(R.id.etRate);
        etLoanLength = (EditText) findViewById(R.id.etLoanLength);
        tvMonthlyPayment = (TextView) findViewById(R.id.tvMonthlyPayment);
        tvTotalAmt = (TextView) findViewById(R.id.tvTotalAmt);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        Calculate = (Button) findViewById(R.id.calcBtn);
        Clear = (Button) findViewById(R.id.clearBtn);
        
// copy messages for Clear button to restore later 
        Mmsg1copy = tvMonthlyPayment.getText().toString();
        Tmsg1copy = tvTotalAmt.getText().toString();
        
    } // end onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mortgage, menu);        
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {   // start MenuItem
        // Handle action bar item clicks here.  The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
   
    	// Go to PrincipalActivity when menu item is selected
        	switch (item.getItemId())
        	{
        	case R.id.prin:
        	
        	Intent principalIntent = new Intent (this, PrincipalActivity.class);
        	startActivity(principalIntent);
            return true;
        	}
        return super.onOptionsItemSelected(item);
    } // end MenuItem
    
    
// mortgagecalc is a method triggered by the Calculate button using onClick in XML.
    
public void mortgagecalc (View v)
{ // begin mortgagecalc method
	
	DecimalFormat df = new DecimalFormat("#.00");
	
// check for empty field, ask for values if any found
	
if (etPrinAmount.getText().toString().equals("") ||
etRate.getText().toString().equals("") ||
etLoanLength.getText().toString().equals(""))
	
	{	
	tvHeader.setText(" Please enter all amounts");
	}
else
	{ // begin else
// retrieve mortgage values
	A = Double.parseDouble(etPrinAmount.getText().toString());
	I = Double.parseDouble(etRate.getText().toString());
	L = Double.parseDouble(etLoanLength.getText().toString());
	tvHeader.setText(""); // clear warning msg even if Clear was not pressed
	
// 	calculate monthly payment amount and total amount
	J = I / (12 * 100);
	N = L * 12;
	M = A * (J / (1 - (Math.pow((1 + J), -N))));
	T = M * N;
//  display calculated amounts
	
	tvMonthlyPayment.setText(" Monthly Payment is " + df.format(M));
	tvTotalAmt.setText("Total Amount is " + df.format(T));
	
	} // end else
} // end mortgagecalc method

public void clearall (View v)
{ // start clearall when Clear button is pressed
	etPrinAmount.setText(""); etRate.setText(""); etLoanLength.setText(""); tvHeader.setText("");
	tvMonthlyPayment.setText(Mmsg1copy); tvTotalAmt.setText(Tmsg1copy);
} //end clearall	

} // end MortgageActivity
