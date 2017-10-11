package srt.inz.smartmeteruser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RequestTransport extends Activity{
	
	EditText etsrc,etdest; String sun,vhid,src,dest,sdate,resout; Button brq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requesttrans_layout);
		etsrc=(EditText)findViewById(R.id.editsrc);
		etdest=(EditText)findViewById(R.id.editdest);
		
		brq=(Button)findViewById(R.id.btnrqst);
		
		SharedPreferences share=getSharedPreferences("mKey", MODE_WORLD_READABLE);
		sun=share.getString("keyuid", "");
		
		SharedPreferences share1=getSharedPreferences("mKey2", MODE_WORLD_READABLE);
		vhid=share1.getString("vid", "");
		
		brq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				src=etsrc.getText().toString();
				dest=etdest.getText().toString();
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Calendar calobj = Calendar.getInstance();
				//System.out.println(df.format(calobj.getTime()));
				sdate=df.format(calobj.getTime());
				
					new RequestApiTask().execute();		
			}
		});
		
	}
	
public class RequestApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	                urlParameters = "userid=" + URLEncoder.encode(sun, "UTF-8")+ "&&" 
	                        + "vh_id=" + URLEncoder.encode(vhid, "UTF-8")+ "&&" 
	                        + "source=" + URLEncoder.encode(src, "UTF-8")+ "&&"
	                        + "destination=" + URLEncoder.encode(dest, "UTF-8")+ "&&"
	                        + "datetime=" + URLEncoder.encode(sdate, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }		

	            resout = Connectivity.excutePost(Constants.REQUESTTRANSPORT_URL,
	                    urlParameters);
	            Log.e("You are at", "" + resout);

	       return resout;
	    }

	    @Override
	    protected void onPostExecute(String s) {
	        super.onPostExecute(s);
	        
	        if(resout.contains("success"))
	        {
	        	
	        	Toast.makeText(getApplicationContext(), ""+resout, Toast.LENGTH_SHORT).show();
	        
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), ""+resout, Toast.LENGTH_SHORT).show();
	        }
	        
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();

	    }
	}

}
