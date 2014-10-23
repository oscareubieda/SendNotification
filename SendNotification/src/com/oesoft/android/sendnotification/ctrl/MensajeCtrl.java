package com.oesoft.android.sendnotification.ctrl;

import java.util.ArrayList;
import java.util.HashMap;

import com.oesoft.android.sendnotification.mdl.MensajeMdl;

public class MensajeCtrl {
	private HashMap<String, MensajeMdl> msg = new HashMap<String, MensajeMdl>();

	public void setMsg(String msg) {
		
		
		//id=-2012753509&icon=com.oesoft.android.sendnotification&ticker=Ticker
		
		String[] parts1 = msg.trim().split("&");
		
		if (parts1.length==3){
			String[] parts2 = parts1[2].split("=");
			String ticker = parts2[1].trim();
	
			parts2 = parts1[0].split("=");
			String id = parts2[1].trim();
	
			parts2 = parts1[1].split("=");
			String icon = parts2[1].trim();
	
			MensajeMdl m = new MensajeMdl();
			m.setIcon(icon);
			m.setMessage(ticker);
			m.setStatus(0);
			
			if (!this.msg.containsKey(id)) {
				this.msg.put(id, m);
			}
		}
	}

	public ArrayList<String> getMsgs() {
		ArrayList<String> r=new ArrayList<String>();
		for (String key : this.msg.keySet()) {
			if (this.msg.get(key).getStatus() == 0) {
				//System.out.println(this.msg.get(key).getMessage());				
				this.msg.get(key).setStatus(1);
				r.add(this.msg.get(key).getMessage());
			}
		}
		return r;
	}
}
