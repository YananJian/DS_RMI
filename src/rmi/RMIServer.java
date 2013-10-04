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
    static int port = 12346;
    private ServerSocket listener = null;
    private static RMIServer rserver = null;
    private Hashtable<String, Object> obj_map = null;
    
    private RMIServer()
    {
    	obj_map = new Hashtable<String, Object>();
    	try {
	    listener = new ServerSocket(port);
	    Thread t = new Thread (this);
	    t.start();
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}   	
    }
    
    
    public static RMIServer getInstance()
    {
    	if (rserver == null)
	    rserver = new RMIServer();
    	return rserver;
    }
    
    public RemoteObjectRef create_ror(Object obj)
    {
    	Class<?> interfaces[] = obj.getClass().getInterfaces();
    	String remote_name = obj.getClass().toString() + "_stub";
    	String[] interface_names=new String[interfaces.length];
	for(int i=0;i<interfaces.length;i++){
	    interface_names[i]=interfaces[i].getName();
	    System.out.println("Creating Stub, interface:"+interface_names[i]);
	}
    	RemoteObjectRef ror = new RemoteObjectRef("0.0.0.0", port, 0, interface_names);
    	ror.setObj_Name(remote_name);
    	return ror;
    }
    
    public Msg invoke(Msg msg)
    {
    	Msg ret_msg = new Msg();
    	String obj_name = ret_msg.getObj_name();
    	String func_name = msg.getFunc_name();
    	Object obj = obj_map.get(obj_name);   	
    	Object params = msg.getParams();
    	Object ret_val = null;
    	try {
	    Method method = obj.getClass().getMethod(func_name, (Class<?>[]) params);
	    ret_val = method.invoke(obj, params);
	    ret_msg.setRets(ret_val);
	    
	} catch (SecurityException e) {
	    // ...
	} catch (NoSuchMethodException e) {
	    // ...
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
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
	    RemoteObjectRef ror = create_ror(msg);
	    
	    ret_msg.set_msg_tp(Constants.MESSAGE_TYPE.RET_LOOKUP);
	    ret_msg.setRemote_ref(ror);
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
    	while(true)
    	{
	    System.out.println("Listening for messages...");  		 
	    try 
		{
		    Socket sock = listener.accept();
		    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
		    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
		    Msg msg = (Msg) ois.readObject();
		    System.out.println(" > got a message!");
    			msg = this.process(msg);
    			oos.writeObject(msg);
    			
    		 } catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		 } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
    	}
    	
    }
    
    @Override
	public void run() {
	// TODO Auto-generated method stub
	listen();
    }
    
    public static void main(String args[])
    {
    	
	
    }
	
}