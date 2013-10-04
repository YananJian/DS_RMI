package rmi;

import java.util.*;
import java.net.*;
import java.io.*;

import utils.Constants.MESSAGE_TYPE;
import utils.RemoteObjectRef;
import utils.*;

public class M_Registry 
{ 
    // registry holds its port and host, and connects to it each time. 
    static String Host;
    static int Port;
    
    // ultra simple constructor.
    public M_Registry(String IPAdr, int PortNum)
    {
    	Host = IPAdr;
    	Port = PortNum;
    }

    public static Msg communicate(Msg msg)
    {
    	Socket sock;
    	Msg ret_msg = null;
		try {
			sock = new Socket(Host, Port);
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
	    	ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
	    	oos.writeObject(msg);
	    	ret_msg = (utils.Msg)ois.readObject();
	    	sock.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}	
		return ret_msg;
    }
    
    // returns the ROR (if found) or null (if else)
    public static RemoteObjectRef lookup(String serviceName) throws IOException
    {
    	// Connect to S_Registry, which is the unique registry on specified host
    	Msg msg = new Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.LOOKUP);
    	msg.setObj_name(serviceName);
    	
    	msg = communicate(msg);
		RemoteObjectRef ror = msg.getRemote_ref();
    	
		// return ROR.
		return ror;
    }

    // rebind a ROR. ROR can be null. again no check, on this or whatever. 
    // I hate this but have no time.
    public void rebind(String serviceName, RemoteObjectRef ror) throws IOException
    {
    	utils.Msg msg = new utils.Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.REBIND);
    	msg.setObj_name(serviceName);
    	msg.setRemote_ref(ror);
    	// it is locate request, with a service name.
    	msg = communicate(msg);
    	
    }
    
	public void list()
	{
		utils.Msg msg = new utils.Msg();
		msg.set_msg_tp(MESSAGE_TYPE.LIST);
		// it is locate request, with a service name.
		msg = communicate(msg);
    }


    public static void main(String args[])
    {
    	String Host = "0.0.0.0";
    	int port = 12345;
    	M_Registry m_registry = new M_Registry(Host, port);
    	m_registry.list();
    }
} 
  
