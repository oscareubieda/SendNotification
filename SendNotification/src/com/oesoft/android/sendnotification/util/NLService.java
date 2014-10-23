package com.oesoft.android.sendnotification.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;



public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
       // System.out.println("OE-NLService: Init");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Intent i = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
       // i.putExtra("notification_event","llego  \n");
        i.putExtra("notification_event","id="+sbn.getId()+"&icon="+sbn.getPackageName()+"&ticker="+sbn.getNotification().tickerText);
        sendBroadcast(i);
    	

    	System.out.println("onNotificationPosted: "+"id="+sbn.getId()+"&icon="+sbn.getPackageName()+"&ticker="+sbn.getNotification().tickerText);
      
           
        
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
      /*  Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);*/
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
        	         	 
        	//System.out.println("Recibio\n");
        	  // if(intent.getStringExtra("command").equals("list")){
        		
        		/* Intent i1 = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                 i1.putExtra("notification_event","=====================");
                 sendBroadcast(i1);*/
/*                 int i=1;
                 if (NLService.this.getActiveNotifications().length > 0)
                 for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                     Intent i2 = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                     i2.putExtra("notification_event","id="+sbn.getId()+"&icon="+sbn.getPackageName()+"&ticker="+sbn.getNotification().tickerText);
                    // System.out.println("OE-Noti: "+sbn.getPackageName());
                     sendBroadcast(i2);
                     i++;
                 }
*/               
              
        	 //}
        	 
         /*   if(intent.getStringExtra("command").equals("clearall")){
                    NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                i1.putExtra("notification_event","=====================");
                sendBroadcast(i1);
                int i=1;
                System.out.println("OE-Noti: GET LIST: "+NLService.this.getActiveNotifications().length);
                if (NLService.this.getActiveNotifications().length > 0)
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getPackageName() + "\n");
                    System.out.println("OE-Noti: "+sbn.getPackageName());
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("com.oesoft.android.sendnotification.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                i3.putExtra("notification_event","===== Notification List ====");
                sendBroadcast(i3);

            }*/

        }
    }

}
