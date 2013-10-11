/*
 make dummy version that starts up
 sends bind / rebind / unbind messages to registry; listens for ack...
      server should start up, bind SOMETHING in main()
*/

package rmi;

import utils.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Hashtable;
import java.lang.reflect.*;

public class RMIServer implements Runnable
{
    static String host;
    static int port;
    private ServerSocket listener = null;
    private static RMIServer rserver = null;
    private static Hashtable<String, Object> obj_map = null;
    private boolean stop = false;
    
    private RMIServer(int port)
    {
    	obj_map = new Hashtable<String, Object>();
    	try {
			host = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Get host address:"+host);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			
		}
    	RMIServer.port = port;
    	try {
	    listener = new ServerSocket(port);
	    Thread t = new Thread (this);
	    t.start();
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	   // e.printStackTrace();
		System.out.println("Can not start the Server");
		return;
	}   	
    }
    
    
    public static RMIServer getInstance(int port)
    {
    	if (rserver == null)
    		rserver = new RMIServer(port);
    	return rserver;
    }
    
    public String[] iter_interfaces(Class<?> interfaces[])
    {
    	String []names = {};
    	for (int i = 0; i< interfaces.length; i++)
    	{
    		Class<?> sub_ifs[] = interfaces[i].getInterfaces();
    		if (sub_ifs.length > 0)
    			;
    		
    	}
    	return names;
    }
    public RemoteObjectRef create_ror(String url, Object obj)
    {
    	Class<?> interfaces[] = obj.getClass().getInterfaces();
    	Class<?> sub_interfaces[] = {};
    	String remote_name = obj.getClass().toString() + "_stub";
    	String[] interface_names=new String[interfaces.length];
		for(int i=0;i<interfaces.length;i++){
			interface_names[i]=interfaces[i].getName();
			sub_interfaces = interfaces[i].getInterfaces();
			for (int j = 0; j<sub_interfaces.length; j++)
				{
				System.out.println("Sub interfaces:"+sub_interfaces[i].getName());
				
				}
			iter_interfaces(sub_interfaces);
			System.out.println(" > Creating Stub, interface:"+interface_names[i]);
		}
		obj_map.put(url, obj);
    	RemoteObjectRef ror = new RemoteObjectRef(host, port, 0, interface_names,url);
    	System.out.println("Creating ROR using host address:"+host);
    	//ror.setObj_Name(url);
    	return ror;
    }
    
    public Msg invoke(Msg msg)
    {
    	Msg ret_msg = new Msg();
    	String obj_name = msg.getObj_name();
    	String func_name = msg.getFunc_name();
    	System.out.println(" > invoking "+obj_name+" on func:"+func_name);
    	
    	Object obj = obj_map.get(obj_name);   	
    	Object params[] = msg.getParams();
    	Object ret_val = null;
    	try {
    		  Class para[] = new Class[]{String.class}; 
    		  Method ms = obj.getClass().getDeclaredMethod(func_name, para);
    		  ret_val = ms.invoke(obj, params);
    		  ret_msg.setRets(ret_val);
    		 
    		} catch (SecurityException e) {
    		  // ...
    		} catch (NoSuchMethodException e) {
    			System.out.println("Can not find this method:"+func_name);
    		  // ...
    		} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				System.out.println("Wrong argument");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	return ret_msg;
    }
    
    public Msg process(Msg msg)
    {
    	Constants.MESSAGE_TYPE tp = msg.get_msg_tp();
    	Msg ret_msg = new Msg();
    	if (tp == Constants.MESSAGE_TYPE.LOOKUP)
    	{
    		RemoteObjectRef ror = create_ror(msg.getObj_name(), msg.getObj());
    		
    		ret_msg.set_msg_tp(Constants.MESSAGE_TYPE.RET_LOOKUP);
    		ret_msg.setRemote_ref(ror);
    		return ret_msg;
   		
    	}
    	else if (tp == Constants.MESSAGE_TYPE.INVOKE)
    	{
    		ret_msg = invoke(msg);  		
    		return ret_msg;
    	}
    	return ret_msg;
    }
    
    public void listen()
    {
    	if (listener == null)
    	{
	    System.out.println("Server Socket Can not establish");
	    return;
    	}
    	while(!stop)
    	{
    		 System.out.println(" > Listening for messages...");  		 
    		 try 
    		 {
    			Socket sock = listener.accept();
    			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
    			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
    			Msg msg = (Msg) ois.readObject();
    			Msg ret_msg = this.process(msg);
    			oos.writeObject(ret_msg);
    			
    		 } catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		 } catch (IOException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
    			System.out.println("Lost Connection...");
	    }
    	}
    	
    }
    
    @Override
	public void run() {
	// TODO Auto-generated method stub
	listen();
    }
    
    public void stop()
    {
    	stop = true;
    	try {
			listener.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public static void main(String args[])
    {
    	
	
    }
	
}
