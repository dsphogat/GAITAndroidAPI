
package com.msi.dig.model;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorListener;

public class digmodel extends Activity implements SensorListener {
	
	final String tag = "digmodel";
	SensorManager sm = null;
	double arrX[]=new double[1000];
	double arrY[]=new double[1000];
	double arrZ[]=new double[1000];
	double arrX1[]=new double[1000];
	double arrY1[]=new double[1000];
	double arrZ1[]=new double[1000];
	double vecv[]=new double[1000];
	ArrayList<Double> A= new ArrayList<Double>();
	int len,clen,loc1,loc2;
	int kk;
	int vari;
	long t,end;
	int cave,cave1;
	double alldis[][]=new double[20][20]; 
	private EditText edittext;
	TextView xViewA = null;
	TextView yViewA = null;
	TextView zViewA = null;
	TextView xViewO = null;
	TextView yViewO = null;
	TextView zViewO = null;
	Button recs;
	Button buttons;
	Button merec;
	Button myres;
	//AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	 
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        buttons=(Button) findViewById(R.id.button1);
        Button butexit=(Button) findViewById(R.id.button2);
        recs=(Button) findViewById(R.id.button3);
        merec=(Button) findViewById(R.id.button4);
        myres=(Button) findViewById(R.id.button5);
        buttons.setOnClickListener(startListener);
        myres.setOnClickListener(startListener5);
        butexit.setOnClickListener(startListener1);
        recs.setOnClickListener(startListener3);
        merec.setOnClickListener(startListener4);
        edittext = (EditText) findViewById(R.id.editText1);
        edittext.setText("Enter");
        xViewA = (TextView) findViewById(R.id.xbox);
        yViewA = (TextView) findViewById(R.id.ybox);
        zViewA = (TextView) findViewById(R.id.zbox);
        xViewO = (TextView) findViewById(R.id.xboxo);
        yViewO = (TextView) findViewById(R.id.yboxo);
        zViewO = (TextView) findViewById(R.id.zboxo);
    	addKeyListener();
    	vari=0;
    	kk=1;
        //onPause();
    }
    
    private OnClickListener startListener1 = new OnClickListener() {
        public void onClick(View v) {
        	onStop();
        }
        };
    
        private OnClickListener startListener5 = new OnClickListener() {
            public void onClick(View v) {
            int i=0,clen1,clen2;
            ArrayList<Double> B= new ArrayList<Double>();
        	ArrayList<Double> C= new ArrayList<Double>();
        	
            	try{	
            	String name="mnt/sdcard/";
            	String own,unk;
        		unk=name+edittext.getText()+"1.txt";
        		own=name+"dev21.txt";
        		//File file = new File(name);
        		BufferedReader in = new BufferedReader(new FileReader(unk));
        		BufferedReader in1 = new BufferedReader(new FileReader(own));
        	//write the bytes in file
        		i=0;
        	while(in.ready())
        	{
        		String text = in.readLine();
        		StringTokenizer tokenizer = new StringTokenizer(text," ");
        			String x1=tokenizer.nextToken();
        			B.add(Double.parseDouble(x1));
        			i++;
        	}
        	clen1=i;
        	i=0;
        	//done();
        	while(in1.ready())
        	{
        		String text1 = in1.readLine();
        		StringTokenizer tokenizer1 = new StringTokenizer(text1," ");
        			String x2=tokenizer1.nextToken();
        			C.add(Double.parseDouble(x2));
        			i++;
        	}
        	clen2=i;
        	//doneyes();
        	double resval=dtwdis2(B,C,clen1,clen2);
        	//done();
        	if(resval<20){
        	solut(resval);
        	}
        	else{
        	solutwr(resval);
        	//doneyes();
        	}
            }catch(Exception e){}
            }
            };
        
        private OnClickListener startListener3 = new OnClickListener() {
            public void onClick(View v) {
            	if(kk==1){
            	kk=0;
            	recs.setText("Stop");
            	onResume();
            	t= System.currentTimeMillis();
            	end = t+5000;
            	/*while( < end) {
            	  // pause to avoid churning
            	  try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	//}
            	//kk=-1;
            	//recs.setText("Paused");
            	//onPause();
            	//onResume();
            	}*/
            	}
            	else{
            		kk=-1;
            		recs.setText("Paused");
                	onPause();
            	}
            }
            };
    private OnClickListener startListener = new OnClickListener() {
        public void onClick(View v) {
        	int i=0;
        	String check=edittext.getText().toString();
        	if(check.matches("Enter")){st(); return;}
        	try{
        		String name="mnt/sdcard/";
        		name=name+edittext.getText()+".txt";
        		File file = new File(name);
        	file.createNewFile();
        	//write the bytes in file
        	if(file.exists())
        	{
        		 FileOutputStream fout = new FileOutputStream(name);
        		 for(i=0;i<1000;i++){
        			 String s="";
        			 DecimalFormat df = new DecimalFormat("#.####");
        		 	s=s+df.format(arrX[i])+" "+df.format(arrY[i])+" "+df.format(arrZ[i])+"\n";
        		 	if(arrX[i]!=0 && arrY[i]!=0 && arrZ[i]!=0)
        	     	fout.write(s.getBytes());
        		 	arrX[i]=arrY[i]=arrZ[i]=0;
        		 }
     	     fout.close();
     	     done();
     	     onPause();
        	}
        	}catch(Exception e){}
        }
    };
    private OnClickListener startListener4 = new OnClickListener() {
        public void onClick(View v) {
        	int i=0;
        	int j=0;
        	//if(check.matches("Enter")){st(); return;}
        	len=0;
        	cave=0;
        	clen=0;
        	try{
        		String name="mnt/sdcard/";
        		name=name+edittext.getText()+".txt";
        		//File file = new File(name);
        		BufferedReader in = new BufferedReader(new FileReader(name)); 
        	//write the bytes in file
        		while(in.ready())
        	{
        		String text = in.readLine();
        		StringTokenizer tokenizer = new StringTokenizer(text," ");
        			String x1=tokenizer.nextToken();
        			String y1=tokenizer.nextToken();
        			String z1=tokenizer.nextToken();
        			arrX[i]=Double.parseDouble(x1);
        			arrY[i]=Double.parseDouble(y1);
        			arrZ[i]=Double.parseDouble(z1);
        			i++;
        	}
        	len=i;
        	//done();
        	wma();
        	for(i=0;i<len;i++){
        		vecv[i]=Math.sqrt(arrX1[i]*arrX1[i]+arrY1[i]*arrY1[i]+arrZ1[i]*arrZ1[i]);
        	}
        	//done();
        	cyclen();
        	j=0;
        	int c1=0,c2=0;
        	int x=0,y=0;
        	//clen =Math.abs(loc1-loc2);
        	//clen=5;
        	//done();
        	for(i=0;i<20;i++){
        		for(j=0;j<20;j++){
        		alldis[i][j]=0;
        		}
        	}
        	//doneyes();
        	ArrayList<Double> B= new ArrayList<Double>();
        	ArrayList<Double> C= new ArrayList<Double>();
        	int x1=0;
        	int y1=0;
        	c1=0;
        	cave=0;
        	int fu=0;
        	i=0;
        	while(i<len){
        		if(i+clen<=len){
        		while(c1<clen)
        		{
        			if(i<len)
        			A.add(vecv[i]);
        			c1++;
        			i++;
        		}
        		cave++;
        		c1=0;
        		y1=x1+1;
        		c2=0;
        		j=i;
        		while(j<len){
        			if(j+clen<=len){
        				while(c2<clen){
        					if(j<len)
        				B.add(vecv[j]);
        				c2++;
        				j++;
        				}
        				c2=0;
        				if(x1<20 && y1<20){
        				alldis[x1][y1]=dtwdis(A,B);
        				alldis[y1][x1]=alldis[x1][y1];
        				}
        				y1++;
        				B.clear();
        			}
        			else{j=len;}
        		}
        		A.clear();
        		x1++;
        	}
        		else{i=len;}
        	}
        	double avg=10000.0,sum;int pos=-1;
        	 for(i=0;i<cave;i++){
        		 sum=0;
    			 for(j=0;j<cave;j++){
    				 sum=sum+alldis[i][j];
    			 }
    			 if(sum<avg){avg=sum;pos=i;}
        	 }
        	//doneyes();
        	//done();
        	String check1=edittext.getText().toString();
        	if(check1.matches("Enter")){st(); return;}
        	try{
        		String name1="mnt/sdcard/";
        		name1=name1+check1+"1.txt";
        		File file1 = new File(name1);
        	file1.createNewFile();
        	//write the bytes in file
        	if(file1.exists())
        	{
        		int tic=0;
        		
        		 FileOutputStream fout1 = new FileOutputStream(name1);
        		 for(i=(pos*clen);tic<clen;tic++){
        			// for(j=0;j<cave;j++){
        			 String s="";
        			 DecimalFormat df = new DecimalFormat("#.###");
        			 if(i<len){
        			 s=s+df.format(vecv[i])+"\n";
        		// 	fout1.write(s.getBytes());
        		 //}
             	    	fout1.write(s.getBytes());
        			}
        			 i++;
        		 }
        			 
        		 fout1.close();
        		 doneyes();
        	}
        	}catch(Exception e){}
        	}catch(Exception e){}
        }
    };

    public void addKeyListener() {
    	 
    	// get edittext component
    	edittext = (EditText) findViewById(R.id.editText1);
     
    	// add a keylistener to keep track user input
    	edittext.setOnKeyListener(new OnKeyListener(){
    	public boolean onKey(View v, int keyCode, KeyEvent event) {
     
    		// if keydown and "enter" is pressed
    		if ((event.getAction() == KeyEvent.ACTION_DOWN)
    			&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
    			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    		    imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);

    			return true;
     
    		}
    		return false;
    	}
    	 });
    	}
    
    public void onSensorChanged(int sensor, float[] values) {
        synchronized (this) {
        	int i=0,k=0;
        	Log.d(tag, "onSensorChanged: " + sensor + ", x: " + values[0] + ", y: " + values[1] + ", z: " + values[2]);
            if (sensor == SensorManager.SENSOR_ORIENTATION) {
	            xViewO.setText("Orientation X: " + values[0]);
	            yViewO.setText("Orientation Y: " + values[1]);
	            zViewO.setText("Orientation Z: " + values[2]);
            }
            if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
	            xViewA.setText("Accel X: " + values[0]);
	            yViewA.setText("Accel Y: " + values[1]);
	            zViewA.setText("Accel Z: " + values[2]);
	            if((vari<1000 && kk==0)){// && System.currentTimeMillis()<end){
	            arrX[vari]=values[0];
	            arrY[vari]=values[1];
	            arrZ[vari]=values[2];
	            vari++;
	            }
	            /*else{
            		kk=-1;
            		recs.setText("Paused");
                	onPause();
	            }*/
            }
           
        }
    }
    
    public void done(){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	// set title
    	alertDialogBuilder.setTitle("File has been Saved. Please Exit !");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Click ok")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//onStop();
						//digmodel.this.finish();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
    }

    public void doneyes(){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	// set title
    	alertDialogBuilder.setTitle(cave+" "+len+" " +clen+". Please Exit !");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Click ok")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//onStop();
						//digmodel.this.finish();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
    }

    public void solut(double ans){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	// set title
    	alertDialogBuilder.setTitle(ans+". Welcome Owner !");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Click ok")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//onStop();
						//digmodel.this.finish();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
    }

    public void solutwr(double ans){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	// set title
    	alertDialogBuilder.setTitle(ans+". Please Exit You're not the owner!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Click ok")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//onStop();
						//digmodel.this.finish();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
    }

    public void wma(){
    	int i=0;
    	int j=1;
    	arrX1[i]=arrX[i]+arrX[i+1]+arrX[i+2]+arrX[i+3]+arrX[i+4];
    	arrY1[i]=arrY[i]+arrY[i+1]+arrY[i+2]+arrY[i+3]+arrY[i+4];
    	arrZ1[i]=arrZ[i]+arrZ[i+1]+arrZ[i+2]+arrZ[i+3]+arrZ[i+4];
    	for(i=5;i<len-5;i++){
    		arrX1[j]=arrX1[j-1]-arrX[i-5]+arrX[i];
    		arrY1[j]=arrY1[j-1]-arrY[i-5]+arrY[i];
    		arrZ1[j]=arrZ1[j-1]-arrZ[i-5]+arrZ[i];
    		j++;
    	}
    	len=j;
    }
    
    public void cyclen(){
    	int j,k;
    	int i=0;
    	int le=0;
    	double l1=10000.0,va;
    	ArrayList<Double> B= new ArrayList<Double>();
    	ArrayList<Double> C= new ArrayList<Double>();
    	for(i=((len/2)-3);i<((len/2)+3);i++){
    		B.add(vecv[i]);
    	}
    	j=0;
    	for(i=0;i<((len/2)-3);i++){
    		j=0;
    		while(j<6){
    			if(i>((len/2)-3)){le=1;j=6;}
    			else{
    			C.add(vecv[i]);
    			j++;
    			i++;
    			}
    		}
    		if(le==0){
    		va=dtwdis(B,C);
    		C.clear();
    		if(va<l1){l1=va;k=i;}
    		}
    		else{
    			C.clear();
    		}
    	}
    	le=0;
    	j=0;
    	
    	for(i=((len/2)+3);i<len;i++){
    		j=0;
    		while(j<6){
    			if(i>len){le=1;j=6;}
    			else{
    			C.add(vecv[i]);
    			j++;
    			i++;
    			}
    		}
    		if(le==0){
    		va=dtwdis(B,C);
    		C.clear();
    		if(va<l1){l1=va;k=i;}
    		}
    		else{
    			C.clear();
    		}
    	}
    	B.clear();
    	clen=Math.abs(i-((len/2)+3));
    }
    
    public double dtwdis(ArrayList<Double> a1,ArrayList<Double> a2){
    	int j;
    	int i=0;
    	double cost;
    	double dtw[][]= new double[clen+1][clen+1];
    	for(i=1;i<clen;i++){
    		dtw[0][i]=1000;
    	}
    	for(i=1;i<clen;i++){
    		dtw[i][0]=1000;
    	}
    	double val=0;
    	dtw[0][0]=0;
    	for(i=1;i<clen;i++){
    		for(j=1;j<clen;j++){
    			double x=a1.get(i);
    	    	double y=a2.get(j);
    			cost=Math.abs(x-y);
    			dtw[i][j]=cost+Math.min(dtw[i-1][j], Math.min(dtw[i][j-1], dtw[i-1][j-1]));
    			if(dtw[i][j]!=0) val=dtw[i][j];
    		}
    	}
    	
    	return val;
    }
    
    public double dtwdis2(ArrayList<Double> a1,ArrayList<Double> a2,int clen1,int clen2){
    	int j;
    	int i=0;
    	double cost;
    	double dtw[][]= new double[clen1+1][clen2+1];
    	for(i=1;i<clen2;i++){
    		dtw[0][i]=1000;
    	}
    	for(i=1;i<clen1;i++){
    		dtw[i][0]=1000;
    	}
    	double val=0;
    	dtw[0][0]=0;
    	for(i=1;i<clen1;i++){
    		for(j=1;j<clen2;j++){
    			double x=a1.get(i);
    	    	double y=a2.get(j);
    			cost=Math.abs(x-y);
    			dtw[i][j]=cost+Math.min(dtw[i-1][j], Math.min(dtw[i][j-1], dtw[i-1][j-1]));
    			if(dtw[i][j]!=0) val=dtw[i][j];
    		}
    	}
    	
    	return val;
    }
    
    public void st(){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	// set title
    	alertDialogBuilder.setTitle("Enter the Name and then Click Save again");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Click ok")
				.setCancelable(false)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						onPause();
						//digmodel.this.finish();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			//	sm.unregisterListener(this);
    }
    
    public void onAccuracyChanged(int sensor, int accuracy) {
    	Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
        
    }
 

    @Override
    protected void onResume() {
    	super.onResume();
    	sm.registerListener(this, 
                SensorManager.SENSOR_ORIENTATION |
        		SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_NORMAL);
    	/*    final SensorEventListener listener = (SensorEventListener) this;
    	    new Handler().postDelayed(new Runnable() {

    	        @Override
    	        public void run() {
    	        //    do stuff with sensor values
    	            sm.unregisterListener(listener);               
    	        }
    	    }, 5000);*/
    	/*super.onResume();
        sm.registerListener(this, 
                SensorManager.SENSOR_ORIENTATION |
        		SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_NORMAL);*/
    }
    
    @Override
    protected void onStop() {
        sm.unregisterListener(this);
       System.exit(0);
        super.onStop();
    }
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
    
    
}