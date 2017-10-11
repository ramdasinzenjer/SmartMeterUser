package srt.inz.smartmeteruser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Userregister extends Activity{
	
	Button brg;

	EditText etn,etun,etpas,etcpas,etph,etmail,etaddress;
	String sn,sun,spas,scpas,sphn,smail,saddress,result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userreg_layout);
		
		etn=(EditText)findViewById(R.id.etname);
		etun=(EditText)findViewById(R.id.etuname);
		etpas=(EditText)findViewById(R.id.etpass);
		etcpas=(EditText)findViewById(R.id.etcps);
		etph=(EditText)findViewById(R.id.etphn);
		etmail=(EditText)findViewById(R.id.etemail);
		etaddress=(EditText)findViewById(R.id.etaddress);
	
	}

	public void regist(View v)
	{
		sn=etn.getText().toString();
		sun=etun.getText().toString();
		spas=etpas.getText().toString();
		scpas=etcpas.getText().toString();
		sphn=etph.getText().toString();
		smail=etmail.getText().toString();
		saddress=etaddress.getText().toString();
		
		if(Connectivity.getInternetConnectionStatus(getApplicationContext()))
		{
		new RegisterApiTask().execute();
		}
		else
		{
			Toast.makeText(getApplicationContext(), 
					"No Internet connection available", Toast.LENGTH_SHORT).show();
		}
	}
	
	public class RegisterApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	                urlParameters =  "name=" + URLEncoder.encode(sn, "UTF-8") + "&&"
	                        + "email=" + URLEncoder.encode(smail, "UTF-8")+ "&&" 
	                        + "user_id=" + URLEncoder.encode(sun, "UTF-8")+ "&&" 
	                        + "address=" + URLEncoder.encode(saddress, "UTF-8")+ "&&" 
	                        + "password=" + URLEncoder.encode(spas, "UTF-8")+ "&&" 
	                        + "phone=" + URLEncoder.encode(sphn, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }		

	            result = Connectivity.excutePost(Constants.REGISTER_URL,
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
	        Intent i=new Intent(getApplicationContext(),MainPage.class);
	        startActivity(i);
	        
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

}
