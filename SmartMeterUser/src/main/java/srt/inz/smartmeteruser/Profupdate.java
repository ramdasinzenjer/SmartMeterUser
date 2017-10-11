package srt.inz.smartmeteruser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import srt.inz.smartmeteruser.Userregister.RegisterApiTask;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profupdate extends Activity{
	
	
	Button bup;

	EditText etn,etun,etpas,etemail,etph,etaddress;
	String sn,sun,spas,sphn,saddress,semail,result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proupdate);
		
		etn=(EditText)findViewById(R.id.etname01);
		etun=(EditText)findViewById(R.id.etuname01);
		etpas=(EditText)findViewById(R.id.etpass01);
		etemail=(EditText)findViewById(R.id.etmail01);
		etph=(EditText)findViewById(R.id.etphn01);
		
		etaddress=(EditText)findViewById(R.id.etaddress01);
		SharedPreferences share=getSharedPreferences("mKey", MODE_WORLD_READABLE);
		sun=share.getString("keyuid", "");
		etun.setText(sun); etun.setEnabled(false);
		
		
		new ProfilefetchApiTask().execute();
		
	}
	public void updatepro(View v)
	{
		sn=etn.getText().toString();	
		spas=etpas.getText().toString();
		semail=etemail.getText().toString();
		sphn=etph.getText().toString();
		saddress=etaddress.getText().toString();
		
		
		new ProfileupApiTask().execute();

	}
	public class ProfileupApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	                urlParameters =  "name=" + URLEncoder.encode(sn, "UTF-8") + "&&"
	                		+ "email=" + URLEncoder.encode(semail, "UTF-8")+ "&&"
	                        + "user_id=" + URLEncoder.encode(sun, "UTF-8")+ "&&" 
	                        + "address=" + URLEncoder.encode(saddress, "UTF-8")+ "&&" 
	                        + "password=" + URLEncoder.encode(spas, "UTF-8")+ "&&" 
	                        + "phone=" + URLEncoder.encode(sphn, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }		

	            result = Connectivity.excutePost(Constants.PROFILEUP_URL,
	                    urlParameters);
	            Log.e("You are at", "" + result);

	       return result;
	    }

	    @Override
	    protected void onPostExecute(String s) {
	        super.onPostExecute(s);
	        
	      //  linlaHeaderProgress.setVisibility(View.GONE);
	        if(result.contains("success"))
	        {
	        	
	        Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
	        
	        
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
	        }
	        
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	    }
	}
	public class ProfilefetchApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	    	String urlParameters = null;
	        try {
	            urlParameters =  "user_id=" + URLEncoder.encode(sun, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        result = Connectivity.excutePost(Constants.REQUESTPROFILE_URL,
	                urlParameters);
	        Log.e("You are at", "" + result);

	        return result;
	    }

	    @Override
	    protected void onPostExecute(String s) {
	        super.onPostExecute(s);
	        
	     
	        if(result.contains("success"))
	        {
	        	
	        Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
	  keyparser();
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), ""+result, Toast.LENGTH_SHORT).show();
	        }
	        
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	    }
	}
	
	public void keyparser()
	{
		try
		{
			JSONObject  jObject = new JSONObject(result);
			JSONObject  jObject1 = jObject.getJSONObject("Event");
			JSONArray ja = jObject1.getJSONArray("Details"); 
			int length=ja.length();
			for(int i=0;i<length;i++)
			{
				JSONObject data1= ja.getJSONObject(i);
				String name=data1.getString("name");
				String email=data1.getString("email");
				String phone=data1.getString("phone");
				String password=data1.getString("password");
				String address=data1.getString("address");
				
				etn.setText(name); etaddress.setText(address); etemail.setText(email);
				etpas.setText(password); etph.setText(phone);
				
			}
		}
			catch (Exception e)         
		{
				System.out.println("Error:"+e);
		}
	}


}
