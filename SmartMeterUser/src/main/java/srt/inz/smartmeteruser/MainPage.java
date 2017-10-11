package srt.inz.smartmeteruser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainPage extends Activity {
	
	EditText eun,eps;	Button bl,br;	String sun,spas,resultout;
	LinearLayout linlaHeaderProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        
        eun=(EditText)findViewById(R.id.edituid);
        eps=(EditText)findViewById(R.id.editpass);
        
        bl=(Button)findViewById(R.id.btnlog);
        br=(Button)findViewById(R.id.btnreg);
        
        linlaHeaderProgress=(LinearLayout)findViewById(R.id.linlaHeaderProgress);
        
        
        bl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sun=eun.getText().toString();
				spas=eps.getText().toString();
				
				SharedPreferences share=getSharedPreferences("mKey", MODE_WORLD_READABLE);
				SharedPreferences.Editor ed=share.edit();
				ed.putString("keyuid", sun);
				ed.commit();
				
				new LoginApiTask().execute();							
				
			}
		});	
        
        br.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(getApplicationContext(),Userregister.class);
				startActivity(i);
				
			}
		});
    }
    
    
    public class LoginApiTask extends AsyncTask<String,String,String> {
    	    
    	    @Override
    	    protected String doInBackground(String... params) {


    	            String urlParameters = null;
    	            try {
    	                urlParameters =  "password=" + URLEncoder.encode(spas, "UTF-8") + "&&"
    	                        + "user_id=" + URLEncoder.encode(sun, "UTF-8");
    	            } catch (UnsupportedEncodingException e) {
    	                // TODO Auto-generated catch block
    	                e.printStackTrace();
    	            }

    	        String    result = Connectivity.excutePost(Constants.LOGIN_URL,
    	                    urlParameters);
    	            Log.e("You are at", "" + result);
    	            resultout=result;

    	       return result;
    	    }

    	    @Override
    	    protected void onPostExecute(String s) {
    	        super.onPostExecute(s);
    	        
    	        linlaHeaderProgress.setVisibility(View.GONE);
    	        //Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
    	      
    	        if(resultout.contains("success"))
    	        {
    	        	
    	        Toast.makeText(getApplicationContext(), ""+resultout, Toast.LENGTH_SHORT).show();
    	        Intent i=new Intent(getApplicationContext(),MainHome.class);
    	        startActivity(i);
    	        
    	        }
    	        else
    	        {
    	        	Toast.makeText(getApplicationContext(), ""+resultout, Toast.LENGTH_SHORT).show();
    	        }
    	        
    	    }

    	    @Override
    	    protected void onPreExecute() {
    	        super.onPreExecute();

    	        linlaHeaderProgress.setVisibility(View.VISIBLE);

    	    }
    	}
}
