package com.oesoft.android.sendnotification;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.oesoft.android.sendnotification.ctrl.MensajeCtrl;

public class MainActivity extends Activity {
	 private NotificationReceiver nReceiver;
     private AQuery aq;
     private MensajeCtrl msgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
        aq = new AQuery(this);
        this.msgList=new MensajeCtrl();
		
		nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nReceiver,filter);

	
		
	}
	
	public void noti(View v){
		  NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
          NotificationCompat.Builder ncomp = new NotificationCompat.Builder(this);
          ncomp.setContentTitle("Titulo");
          ncomp.setContentText("Contenido");
          ncomp.setTicker("Ticker");
          ncomp.setSmallIcon(R.drawable.ic_launcher);
          ncomp.setAutoCancel(true);
          nManager.notify((int)System.currentTimeMillis(),ncomp.build());
	}
	
	
	public void getNoti(View v){
		Intent i = new Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
	                     
        i.putExtra("command","list");
        sendBroadcast(i);
		}
	
	public void setExit(View v){
			this.finish();
			System.exit(0);
		}

	public void setActivate(View v){
		Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
		startActivity(intent);
	}
	
	public void sendServer(View v){

	System.out.println("");
		

	}
	
	private void sServer(String p){
		//id=1&ico=paquete&ticker=msj
		

	String url = "http://"+aq.id(R.id.txtIp).getText()+"?"+p;
		System.out.println("URL: "+url);
		aq.ajax(url, String.class, new AjaxCallback<String>() {
		        @Override
		        public void callback(String url, String html, AjaxStatus status) {	
		        	System.out.println("Enviado al server");
		    		//System.exit(0);
		        }		        
		});  
	
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		
	
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,false);
			return rootView;
		}
	}
	
    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
        	final String rec = intent.getStringExtra("notification_event");
        	System.out.println("Principal: "+rec);
        	sServer(rec);

/*
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					msgList.setMsg(rec);
					for (String noti : msgList.getMsgs()) {
						System.out.println("MAIN Noti: " + noti);
					}
				}
			}, 9000);*/
            
        }
    }
}
