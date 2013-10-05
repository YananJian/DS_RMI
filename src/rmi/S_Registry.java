package rmi;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
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
			ret_msg.set_msg_tp(MESSAGE_TYPE.RET_REBIND);
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
                	System.out.println(sock.getInetAddress());
                	System.out.println(sock.getPort());
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
		S_Registry s_registry = new S_Registry(Constants.PORT_REGISTER);
		Thread t = new Thread(s_registry);
		t.start();
	}
}
