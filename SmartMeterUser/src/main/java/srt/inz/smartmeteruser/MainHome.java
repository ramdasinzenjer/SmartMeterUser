package srt.inz.smartmeteruser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainHome extends Activity implements LocationListener{
	
	Button btfnr,btnupprof; 
	ImageView btnAlert; 
	String stplace; double latitude,longitude; Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainhome);
		
		btfnr=(Button)findViewById(R.id.btnfindrk);
		btnupprof=(Button)findViewById(R.id.btnupp);
		btnAlert=(ImageView)findViewById(R.id.imagebtnalert);
		
		getmyloc();
			
		btfnr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i=new Intent(getApplicationContext(),Maphome.class);
	    	        startActivity(i);
			}
		});
		
		btnupprof.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Profupdate.class);
				startActivity(i);
				
			}
		});
		
		btnAlert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					send_msg("123");
			}
		});
		
	}
	public void send_msg(String num)
	{
    		
    	try{
    		SmsManager smsManager = SmsManager.getDefault();
    				smsManager.sendTextMessage(num, null, " Emergency : I am in trouble at  "+stplace , null, null);
    				Toast.makeText(getBaseContext(), "SMS Sent!",
    							Toast.LENGTH_SHORT).show();
    		}catch (Exception e) {
    				Toast.makeText(getApplicationContext(),
         					"Failed!",
         					Toast.LENGTH_SHORT).show();
         				e.printStackTrace();
         			  }
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Geocoder gc= new Geocoder(this, Locale.ENGLISH);
        // Getting latitude of the current location
        latitude =  location.getLatitude();

        // Getting longitude of the current location
        longitude =  location.getLongitude();

      

try {
	List<Address> addresses = gc.getFromLocation(latitude,longitude, 1);
	
	if(addresses != null) {
		Address returnedAddress = addresses.get(0);
		StringBuilder strReturnedAddress = new StringBuilder("");
		for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) 
		{
			strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
			
		}
	

		stplace=strReturnedAddress.toString();
		
		//String[] splited = stplace.split("\\s+");
		  	
		Toast.makeText( getBaseContext(),stplace,Toast.LENGTH_SHORT).show();
	}
//stores the current address to shared preferene shr.
	
	else{
		Toast.makeText(getBaseContext(),"GPS Disabled",Toast.LENGTH_SHORT).show();

		
	}
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void getmyloc()
	{
		try {
			
			
			 LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			    // Creating a criteria object to retrieve provider
			    Criteria criteria = new Criteria();

			    // Getting the name of the best provider
			    String provider = locationManager.getBestProvider(criteria, true);

			    // Getting Current Location
			    location = locationManager.getLastKnownLocation(provider);
			    

			    if(location!=null){
			            onLocationChanged(location);
			            
			    }

			    locationManager.requestLocationUpdates(provider, 120000, 0, this);
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
	    
	}
}
