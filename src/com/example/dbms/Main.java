package com.example.dbms;

import android.os.Bundle;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Main extends Activity implements OnClickListener{

    DB cdb = new DB(this);
    EditText ed_SNO,ed_psw,ed_time;
    Button ins;
    String read;
    TextView output;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        ins = (Button) findViewById(R.id.in_button);
        ins.setOnClickListener(this);
        
	  	ed_SNO = (EditText) findViewById(R.id.edtext_SNO);
    	ed_psw = (EditText) findViewById(R.id.edtext_psw);
    	ed_time = (EditText) findViewById(R.id.edtext_time);
    	
    	cdb.openToWrite();
    	cdb.insert("1","Cola", "1991/10/17 1:00:00");
    	cdb.insert("2","Est", "1991/10/17 1:10:00");
    	cdb.insert("3","David", "1991/10/17 1:11:00");
    	cdb.insert("4","Stick", "1991/10/17 1:14:00");
		
    	cdb.close();

    	showdb();
    }

    public void showdb(){
    	output = (TextView)findViewById(R.id.output);
    	cdb.openToRead();
		read = cdb.All();
		cdb.close();
		output.setText(read);
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		cdb.openToWrite();
		
		switch(v.getId())
		{
		case  R.id.in_button:
			
			//Toast.makeText(this,"新增有Run",Toast.LENGTH_SHORT).show();
	    	cdb.insert(ed_SNO.getText().toString(),
	    			   ed_psw.getText().toString(),
	    			   ed_time.getText().toString());
	    	Toast.makeText(this,"新增有Run",Toast.LENGTH_SHORT).show();
	    	showdb();
	    	break;
		}
	}
    
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	
}



