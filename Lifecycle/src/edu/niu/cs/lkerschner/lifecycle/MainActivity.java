package edu.niu.cs.lkerschner.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {

	private String createMsg;
	private String startMsg;
	private String resumeMsg;
	private String pauseMsg;
	private String stopMsg;
	private String restartMsg;
	private String destroyMsg;
	
	 private void initializeMessages()
     {
     	createMsg = (String)getResources().getText(R.string.create_message);
     	startMsg = (String)getResources().getText(R.string.start_message);
     	resumeMsg = (String)getResources().getText(R.string.resume_message);
     	pauseMsg = (String)getResources().getText(R.string.pause_message);
     	stopMsg = (String)getResources().getText(R.string.stop_message);
     	restartMsg = (String)getResources().getText(R.string.restart_message);
     	destroyMsg = (String)getResources().getText(R.string.destroy_message);     			
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeMessages();        
        Toast.makeText(this,createMsg,Toast.LENGTH_SHORT).show();          
    }

    @Override 
    protected void onStart()
    {
    	super.onStart();
    	Toast.makeText(this,startMsg,Toast.LENGTH_SHORT).show();   
    }
    
    @Override 
    protected void onResume()
    {
    	super.onStart();
    	Toast.makeText(this,resumeMsg,Toast.LENGTH_SHORT).show();   
    }
    
    @Override 
    protected void onPause()
    {
    	super.onStart();
    	Toast.makeText(this,pauseMsg,Toast.LENGTH_SHORT).show();   
    }
    
    @Override 
    protected void onStop()
    {
    	super.onStart();
    	Toast.makeText(this,stopMsg,Toast.LENGTH_SHORT).show();   
    }
    
    @Override 
    protected void onRestart()
    {
    	super.onStart();
    	Toast.makeText(this,restartMsg,Toast.LENGTH_SHORT).show();   
    }
    
    @Override 
    protected void onDestroy()
    {
    	Toast.makeText(this,destroyMsg,Toast.LENGTH_SHORT).show();   
    	super.onStart();    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
