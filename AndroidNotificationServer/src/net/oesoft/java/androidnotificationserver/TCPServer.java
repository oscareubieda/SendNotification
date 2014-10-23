package net.oesoft.java.androidnotificationserver;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import net.oesoft.java.androidnotificationserver.mdl.Mensaje;

public class TCPServer {
	private HashMap<String, Mensaje> msg=new HashMap<String, Mensaje>();
	private JPopupMenu jpopup;
	
	
	public TCPServer() {
		   
	       if(!SystemTray.isSupported()){
	           System.out.println("System tray is not supported !!! ");
	           return ;
	       }
	       SystemTray systemTray = SystemTray.getSystemTray();   
	       Image image = Toolkit.getDefaultToolkit().getImage("src/images/notification.png");

	       PopupMenu trayPopupMenu = new PopupMenu();

	       MenuItem about = new MenuItem("About...");
	       about.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               JOptionPane.showMessageDialog(null, "By Oscar Eubieda");          
	           }
	       });     
	       trayPopupMenu.add(about);
	       
	       
	       
	       
	       MenuItem setting = new MenuItem("Setting");
	       setting.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               try {
					JOptionPane.showMessageDialog(null, "Your Host addr: " + InetAddress.getLocalHost().getHostAddress());
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}          
	           }
	       });     
	       trayPopupMenu.add(setting);
	       

	       

	       MenuItem close = new MenuItem("Close");
	       close.addActionListener(new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               System.exit(0);             
	           }
	       });
	       trayPopupMenu.add(close);

	       TrayIcon trayIcon = new TrayIcon(image, "Android Notification", trayPopupMenu);
	       trayIcon.setImageAutoSize(true);

	       try{
	           systemTray.add(trayIcon);
	       }catch(AWTException awtException){
	           awtException.printStackTrace();
	       }
	       
	}
	
	
	private void sendPopUp(String App, String msg) {	       
	       //POPUP
	       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	       double width = screenSize.getWidth();
	       double height = screenSize.getHeight();
	       jpopup = new JPopupMenu();

	      ImageIcon iconL= new ImageIcon("src/images/gmail.png");
	      
	       
	       JMenuItem javaCupMI = new JMenuItem(msg,iconL );
	       jpopup.add(javaCupMI);
	       jpopup.addSeparator();

	       JMenuItem exitMI = new JMenuItem("Exit");
	       jpopup.add(exitMI);
	       
	       jpopup.setLocation((int) width-120, (int) height-100);
        jpopup.setInvoker(jpopup);
        jpopup.setVisible(true);
        
        	  try {
				Thread.currentThread().sleep(3000);
	        	  jpopup.setVisible(false);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				jpopup.setVisible(false);
			}
        
	}
public void getCleanMsg(String msg){
		String[] parts=msg.split(" ");
		String[] parts1=parts[1].split("&");
		String[] parts2=parts1[2].split("=");
		String ticker=parts2[1].trim();
		
		 parts1=parts[1].split("&");
		 parts2=parts1[0].split("=");
		String id=parts2[1].trim();
		
		parts1=parts[1].split("&");
		 parts2=parts1[1].split("=");
		String icon=parts2[1].trim();
		
		Mensaje m=new Mensaje();
		m.setIcon(icon);
		m.setMessage(ticker);
		m.setStatus(0);
	//	System.out.println(id);
		//System.out.println(this.msg.containsKey(id));
		if (!this.msg.containsKey(id)){		
			this.msg.put(id, m);
		}
		
		
}

public String msgClean(String msg) {
	String clean=new String();
	clean=msg.replace("%20", " ");
	clean=clean.replace("null", " ");
	return clean;
	
}

public void pMsg() {
	for (String key : this.msg.keySet()) {
		if (this.msg.get(key).getStatus()==0){
			System.out.println("Icon: "+this.msg.get(key).getIcon()+" ---Titulo: "+msgClean(this.msg.get(key).getMessage()));
			sendPopUp(this.msg.get(key).getIcon(),msgClean(this.msg.get(key).getMessage())); 
			this.msg.get(key).setStatus(1);
		}
	}
}
	
	public static void main(String argv[]) throws Exception
    {
       String clientSentence;
       ServerSocket welcomeSocket = new ServerSocket(80);
       TCPServer obj=new TCPServer();
       
       while(true)
       {
          Socket connectionSocket = welcomeSocket.accept();
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
          clientSentence = inFromClient.readLine();
          inFromClient.close();
          obj.getCleanMsg(clientSentence);
          obj.pMsg();
       }
       
 
       
       
       
       
       
      
    } 
	
	
}
