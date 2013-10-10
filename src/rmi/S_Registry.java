package rmi;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;
import java.lang.*;

import utils.Constants;
import utils.Constants.MESSAGE_TYPE;
import utils.Msg;
import utils.RemoteObjectRef;

public class S_Registry implements Runnable {
	
	int port;
	HashMap<String, RemoteObjectRef> reg;
	HashMap<String, String> reg_server;
	public S_Registry(int port)
	{
		this.port = port;
		reg = new HashMap<String, RemoteObjectRef>();
		reg_server = new HashMap<String, String>();
	}

	public Msg process(Msg msg)
	{
		System.out.println("MSG TYPE:"+msg.get_msg_tp());
		Msg ret_msg = new Msg();
		if (msg.get_msg_tp() == MESSAGE_TYPE.LOOKUP)
		{
			String name = msg.getObj_name();
			
			// suppose the remote object is in the registry
			if (reg.get(name) != null)
			{
				
				ret_msg.setRemote_ref(reg.get(name));	
				ret_msg.set_msg_tp(MESSAGE_TYPE.RET_LOOKUP);
				
				return ret_msg;
			}
			
		}
		else if (msg.get_msg_tp() == MESSAGE_TYPE.REBIND)
		{
			String host = msg.getIp() + ":" + Integer.toString(msg.getPort());
			
			reg.put(msg.getObj_name(), msg.getRemote_ref());
			
			reg_server.put(msg.getObj_name(), host);
			System.out.println(" > Rebinding remote object "+msg.getObj_name());
			
			ret_msg.set_msg_tp(MESSAGE_TYPE.RET_REBIND);
		}
		else if (msg.get_msg_tp() == MESSAGE_TYPE.BIND)
		{
			String host = msg.getIp() + ":" + Integer.toString(msg.getPort());
			
			reg.put(msg.getObj_name(), msg.getRemote_ref());
			
			reg_server.put(msg.getObj_name(), host);
			System.out.println(" > Binding remote object "+msg.getObj_name());
			
			ret_msg.set_msg_tp(MESSAGE_TYPE.RET_BIND);
		}
		else if (msg.get_msg_tp() == MESSAGE_TYPE.LIST)
		{	
			Vector<String> list = new Vector<String>();
			for (String key : reg.keySet()) 
			{
			    list.add(key);
			}
			ret_msg.set_list(list);
			ret_msg.set_msg_tp(MESSAGE_TYPE.RET_BIND);
		}
		
		else if (msg.get_msg_tp() == MESSAGE_TYPE.UNBIND)
		{	
		    String url = msg.getObj_name();
		    this.reg.remove(url);
		    this.reg_server.remove(url);

		    ret_msg.set_msg_tp(MESSAGE_TYPE.RET_UNBIND);
		}
		
		
		return ret_msg;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket listener = new ServerSocket(port);
			while (true)
			{
				Socket sock = listener.accept();
				try {
                    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                	
                	Msg msg = (Msg) ois.readObject();              	
                	System.out.println(" > connection from host:" + sock.getInetAddress()+":"+sock.getPort());
                	
                	msg.setIp(sock.getInetAddress().getHostAddress());
                	msg.setPort(sock.getPort());
                	Msg ret_msg = this.process(msg);  
                	oos.writeObject(ret_msg);
                                
                } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e)
				{
					
				}
				finally {
                    sock.close();
                }
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{	
		int port;
		if (args.length < 1)
		{
			System.out.println("Wrong arguments, Format: java rmi/S_Registry <port of S_Registry>");
			return;
		}
		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Please input valid port");
			return;
		}
		S_Registry s_registry = new S_Registry(port);
		Thread t = new Thread(s_registry);
		System.out.println(" > Register started");
		t.start();
	}
}
