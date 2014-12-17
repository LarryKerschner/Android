package edu.niu.cs.csci.lkerschner.gradecalc;
/*
 * NIU CSCI 522 Android Final Exam Fall 2014
 * 
 * Larry Kerschner z1756333
 * 
 * Grade Calculator for Android, C++, Computer Architecture and any other class. Include known grading patterns for 
 * NIU classes. Allow for all parameters to be entered for any other class.
 * Student total points can not exceed total class points.
 * 
 * 1. Choose one of four classes using RadioButtons.
 * 2. Choose from three to nine metrics to enter.
 * 3. Each number is chosen from a pickerView dialog. The range of numbers is adjusted for each item. 
 *    Numbers roll either direction completely around the range.
 * 4. An ongoing Status dialog is both automatically and manually displayed.
 * 5. The picker dialog displays the item chosen from the main view.
 * 6. The Grade is displayed at the top of the main view when it has been calculated.
 * 
 * In practice, the picker is a bit tedious to work on the emulator. A real Android should be easier.
 * The pickerView requires version 11 and the grid layout for the status display requires version 14. 
 * Need a way to handle numbers less than one. 
 * 
 * Thanks to Raghunandan and Stackoverflow for the picker dialog. 
 * http://stackoverflow.com/questions/17805040/how-to-create-a-number-picker-dialog
 *   
 */
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.util.Log;


public class MainActivity extends Activity implements NumberPicker.OnValueChangeListener
{
    private static TextView tv, tv2, tv3, pickerTV;
    private static final String TAG = "GradeCalc"; 
    private static TextView statTV2, statTV4, statTV6, statTV10, statTV12, statTV14, statTV16;
    private static TextView statTV18, statTV24, statTV26;
    private String classValue, pointChoice, instructions;
    private double classTestPercent5, classAssignPercent6, classMiscPercent7;
    private double classTestPoints8, classAssignPoints9, classMiscPoints10, classTotalPoints;
    private double yourTestPoints11, yourAssignPoints12, yourMiscPoints13, yourTotalPoints, yourGrade;
    private double yourTestPercent, yourAssignPercent, yourMiscPercent, yourGradePercent;
    private int pickmin, pickmax;
    static RadioButton Android, CPP, CAS, OTH, CLTSTPCT, CLASGNPCT, CLMISCPCT;
    static RadioButton CLTSTPTS, CLASGNPTS, CLMISCPTS, YRTSTPTS, YRASGNPTS, YRMISCPTS;
    static RadioGroup RGClass, RGPoints;
    static Dialog d;
    int flag;
    DecimalFormat df = new DecimalFormat("#.0");
  
    @Override
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView1);
        instructions = getString(R.string.hello_world);
        
        RadioGroup rgClass  = (RadioGroup) findViewById(R.id.radioGroupClass); 
// 3 NIU classes and 1 generic class         
        Android = (RadioButton) findViewById(R.id.and);
        CPP = (RadioButton) findViewById(R.id.cpp);
        CAS = (RadioButton) findViewById(R.id.cas);
        OTH = (RadioButton) findViewById(R.id.oth);
        
        RadioGroup rgPoints  = (RadioGroup) findViewById(R.id.radioGroupPoints);
// Class Percentages for Tests, Assignments and Misc.        
        CLTSTPCT = (RadioButton) findViewById(R.id.clTstPct);
        CLASGNPCT = (RadioButton) findViewById(R.id.clAsgnPct);
        CLMISCPCT = (RadioButton) findViewById(R.id.clMiscPct);
// Class Points for Tests, Assignments and Misc.        
        CLTSTPTS = (RadioButton) findViewById(R.id.clTstPts);
        CLASGNPTS = (RadioButton) findViewById(R.id.clAsgnPts);
        CLMISCPTS = (RadioButton) findViewById(R.id.clMiscPts);
// Your Points for Tests, Assignments and Misc.        
        YRTSTPTS = (RadioButton) findViewById(R.id.yrTstPts);
        YRASGNPTS = (RadioButton) findViewById(R.id.yrAsgnPts);
        YRMISCPTS = (RadioButton) findViewById(R.id.yrMiscPts);
// Buttons for picking numbers, ongoing status and reseting all choices and values.        
        Button b = (Button) findViewById(R.id.numpick);
        Button s = (Button) findViewById(R.id.status);
        Button r = (Button) findViewById(R.id.reset);
        RGClass = rgClass;
        RGPoints = rgPoints;
        
// Launch the number picker       
        b.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) 
            {
                 show();
            }
          });
// Launch the Status screen.        
         s.setOnClickListener(new OnClickListener()
         {
            @Override
            public void onClick(View v) 
            {
                 status();
            }
          });
// Reset everything.         
         r.setOnClickListener(new OnClickListener()
         {
            @Override
            public void onClick(View v) 
            {
            	resetall();            	
            }
          });
// Class is selected. Display the class in the picker view.   
         RGClass.setOnCheckedChangeListener(new OnCheckedChangeListener()
   		{
   			@Override
   			public void onCheckedChanged(RadioGroup group, int checkedId)
   			{
   				int selectedId = RGClass.getCheckedRadioButtonId();
   				
   				// find which radio button is selected
    
   				if(selectedId == Android.getId()) 	
   				{
   					// Known NIU Android values
   					classValue = "Android";
   					flag = 1;
   					classTestPercent5 = 60; classAssignPercent6 = 30; classMiscPercent7 = 10;
   					classTestPoints8 = 365; classAssignPoints9 = 575; classMiscPoints10 = 100; classTotalPoints = 1040;  
   				}
   				else if(selectedId == CPP.getId()) 	
         		{
   					// Known NIU C++ class values.
        	 		classValue = "C++";
        	 		flag = 2;
        	 		classTestPercent5 = 65; classAssignPercent6 = 35; classMiscPercent7 = 0;
        	 		classTestPoints8 = 0; classAssignPoints9 = 0; classMiscPoints10 = 0; classTotalPoints = 0;
         		}
   				else if(selectedId == CAS.getId()) 
         		{
   					// Known NIU Computer Architecture values.
         			classValue = "Computer Arch.";
         			flag = 3;
         			classTestPercent5 = 75; classAssignPercent6 = 25; classMiscPercent7 = 0;
         			classTestPoints8 = 0; classAssignPoints9 = 0; classMiscPoints10 = 0; classTotalPoints = 0; 
         		}
   				else if(selectedId == OTH.getId()) 
         		{
         			classValue = "Other Class";
         			flag = 4;
         			classTestPercent5 = 0; classAssignPercent6 = 0; classMiscPercent7 = 0;
         			classTestPoints8 = 0; classAssignPoints9 = 0; classMiscPoints10 = 0; classTotalPoints = 0;
         		}
   				else
   				{
   					classValue = "";
   					classTestPercent5 = 0; classAssignPercent6 = 0; classMiscPercent7 = 0;
         			classTestPoints8 = 0; classAssignPoints9 = 0; classMiscPoints10 = 0; classTotalPoints = 0;
   				}
   			}
   		});
         
// Lots of percentages and points. Display the task chosen in the picker view.  Adjust the pickerView range based on the metric.      
        RGPoints.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				int selectedId = RGPoints.getCheckedRadioButtonId();
				
				// find which radio button is selected

				if(selectedId == CLTSTPCT.getId()) 	
				{
					pointChoice = "Class Test Percent";
					flag = 5;
					pickmin = 0; pickmax = 100;
				}
				else if(selectedId == CLASGNPCT.getId()) 	
    			{
					pointChoice = "Class Assignment Percent";
					flag = 6;
					pickmin = 0; pickmax = 100;
      			}
				else if(selectedId == CLMISCPCT.getId()) 
      			{
					pointChoice = "Class Misc. Percent";
					flag = 7;
					pickmin = 0; pickmax = 100;
      			}
      			else if(selectedId == CLTSTPTS.getId()) 
      			{
      				pointChoice = "Class Test Points";
      				flag = 8;
      				pickmin = 300; pickmax = 600;
      			}
      			else if(selectedId == CLASGNPTS.getId()) 
      			{
      				pointChoice = "Class Assignment Points";
      				flag = 9;
      				pickmin = 400; pickmax = 600;
      			}
      			else if(selectedId == CLMISCPTS.getId()) 
      			{
      				pointChoice = "Class Misc. Points";
      				flag = 10;
      				pickmin = 0; pickmax = 100;
      			}
      			else if(selectedId == YRTSTPTS.getId()) 
      			{
      				pointChoice = "Your Test Points";
      				flag = 11;
      				pickmin = 100; pickmax = 400;
      			}
      			else if(selectedId == YRASGNPTS.getId()) 
      			{
      				pointChoice = "Your Assigment Points";
      				flag = 12;
      				pickmin = 400; pickmax = 600;
      			}
      			else if(selectedId == YRMISCPTS.getId()) 
      			{
      				pointChoice = "Your Misc. Points";
      				flag = 13;
      				pickmin = 0; pickmax = 100;
      			}
      			else
      			{
      				pointChoice = "";  // used with Reset All
      			}
			}
    
		});  		
    }
     
     @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
     {
 //        Log.e(TAG, "value is" + "" + newVal); Too many messages!
     }
     
  // http://stackoverflow.com/questions/17805040/how-to-create-a-number-picker-dialog
  // Raghunandan      
    public void show()
    {
         final Dialog d = new Dialog(MainActivity.this);
         d.setTitle(classValue);
         d.setContentView(R.layout.dialog);
         Button b1 = (Button) d.findViewById(R.id.button1);
         Button b2 = (Button) d.findViewById(R.id.button2);
         pickerTV = (TextView) d.findViewById(R.id.pickTV1);
      
         pickerTV.setText(pointChoice);
         final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
         np.setMaxValue(pickmax); // dynamic maximum
         np.setMinValue(pickmin); // dynamic minimum
         np.setWrapSelectorWheel(true); // let the wheel roll all the way around in either direction
         np.setOnValueChangedListener(this);
         
         b1.setOnClickListener(new OnClickListener() // get number picked and store it
         {
          @Override
          public void onClick(View v) 
          {
        	if (flag == 5)        	
        	{
        		classTestPercent5 = (np.getValue());       	   	
        	}else if (flag == 6)
        	{
        		classAssignPercent6 = (np.getValue()); 	
        	}else if (flag == 7)
        	{
        		classMiscPercent7 = (np.getValue()); 	
        	}else if (flag == 8)
        	{
        		classTestPoints8 = (np.getValue()); 	
        	}else if (flag == 9)
        	{
        		classAssignPoints9 = (np.getValue()); 
        	}else if (flag == 10)
        	{
        		classMiscPoints10 = (np.getValue()); 
        	}else if (flag == 11)
        	{
        		yourTestPoints11 = (np.getValue()); 
        	}else if (flag == 12)
        	{
        		yourAssignPoints12 = (np.getValue()); 
        	}else if (flag == 13)
        	{
        		yourMiscPoints13 = (np.getValue()); 
        	}  
        		calc();
              d.dismiss();
          }    
          });
         
         b2.setOnClickListener(new OnClickListener() 
         {
          @Override
          public void onClick(View v) 
          {
              d.dismiss();
          }    
          });
          d.show();
    }

// Show required information and progress, also final result!    
    public void status()
    {
         final Dialog d = new Dialog(MainActivity.this);
         d.setTitle("Status");
         d.setContentView(R.layout.status);
         tv2 = (TextView) d.findViewById(R.id.textView1);
         statTV2 = (TextView) d.findViewById(R.id.textView2);
         statTV4 = (TextView) d.findViewById(R.id.textView4);
         statTV6 = (TextView) d.findViewById(R.id.textView6);
         statTV10 = (TextView) d.findViewById(R.id.textView10);
         statTV12 = (TextView) d.findViewById(R.id.textView12);
         statTV14 = (TextView) d.findViewById(R.id.textView14);
         statTV16 = (TextView) d.findViewById(R.id.textView16);
         statTV18 = (TextView) d.findViewById(R.id.textView18);
         statTV24 = (TextView) d.findViewById(R.id.textView24);
         statTV26 = (TextView) d.findViewById(R.id.textView26);
         String total5 = String.valueOf(classTestPercent5);
     	 statTV2.setText(total5);
     	 String total6 = String.valueOf(classAssignPercent6);
    	 statTV4.setText(total6);
    	 String total7 = String.valueOf(classMiscPercent7);
    	 statTV6.setText(total7);
    	 String total8 = String.valueOf(classTestPoints8);
    	 statTV10.setText(total8);
    	 String total9 = String.valueOf(classAssignPoints9);
    	 statTV12.setText(total9);
    	 String total10 = String.valueOf(classMiscPoints10);
    	 statTV14.setText(total10);
    	 String total11 = String.valueOf(yourTestPoints11);
    	 statTV18.setText(total11);
    	 String total12 = String.valueOf(yourAssignPoints12);
    	 statTV24.setText(total12);
    	 String total13 = String.valueOf(yourMiscPoints13);
    	 statTV26.setText(total13);
    	 String totalclpt = String.valueOf(classTotalPoints);
    	 statTV16.setText(totalclpt);
         
// Button to return to main view.      
         Button b1 = (Button) d.findViewById(R.id.button1);         
         
         b1.setOnClickListener(new OnClickListener()
         {
          @Override
          public void onClick(View v) 
          {                  
              d.dismiss();
          }    
         });        
          d.show();
    }
// Reset all values.    
    public void resetall()
    {    	
    	classTestPercent5 = 0;classAssignPercent6 = 0;classMiscPercent7 = 0;
    	classTestPoints8 = 0; classAssignPoints9 = 0; classMiscPoints10 = 0; classTotalPoints = 0;
    	yourTestPoints11 = 0; yourAssignPoints12 = 0; yourMiscPoints13 = 0; yourGrade = 0;
     	tv.setText(instructions);    	
    	RGClass.clearCheck();
    	RGPoints.clearCheck();    	
  	}
    
    public void calc()  // calculate all the info entered - need a better way to check for Misc values.
    {
    	if (classTestPercent5 == 0 || classAssignPercent6 == 0  || 
    			classTestPoints8 == 0 || classAssignPoints9 == 0 || 
    			yourTestPoints11 == 0 || yourAssignPoints12 == 0 )
    	{   		
    	}else if (classMiscPoints10 != 0 && yourMiscPoints13 == 0)
    	{ 
    		
    	}else if (yourTestPoints11 > classTotalPoints)
    	{
    		yourTestPoints11 = classTotalPoints;
    	}else if (yourAssignPoints12 > classAssignPoints9)
    	{
    		yourAssignPoints12 = classAssignPoints9;
    	}else if (yourMiscPoints13 > classMiscPoints10)
    	{
    		yourMiscPoints13 = classMiscPoints10;
    	}else    		
    	{
    		yourTestPercent = ((yourTestPoints11/classTestPoints8) * (classTestPercent5 /100));
    		yourAssignPercent = ((yourAssignPoints12/classAssignPoints9) * (classAssignPercent6/100));		
    		yourMiscPercent =((yourMiscPoints13/classMiscPoints10) * (classMiscPercent7/100)); 
    		yourGradePercent = (yourTestPercent + yourAssignPercent + yourMiscPercent);
    		yourGrade = (yourGradePercent * 100);    		
    		
    		double percentage = Double.valueOf(df.format(yourGrade));

    		String grade = String.valueOf(percentage);
       	 	tv.setText("Your Grade is " + grade);
       	 	
    	}
    }
}
