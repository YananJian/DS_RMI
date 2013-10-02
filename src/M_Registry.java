
import java.util.*;
import java.net.*;
import java.io.*;

import utils.Constants.MESSAGE_TYPE;
import utils.RemoteObjectRef;

public class M_Registry 
{ 
    // registry holds its port and host, and connects to it each time. 
    String Host;
    int Port;
    
    // ultra simple constructor.
    public M_Registry(String IPAdr, int PortNum)
    {
	Host = IPAdr;
	Port = PortNum;
    }

    // returns the ROR (if found) or null (if else)
    public RemoteObjectRef lookup(String serviceName) throws IOException
    {
	// Connect to S_Registry, which is the unique registry on specified host
	
    	Socket sock = new Socket(Host, Port);

    	System.out.println("socket made.");
	    
    	// get TCP streams and wrap them. 
    	ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
    	ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
    	
    	System.out.println("stream made.");
    	utils.Msg msg = new utils.Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.LOOKUP);
    	// it is locate request, with a service name.
    	oos.writeObject(msg);
	
    	System.out.println("command and service name sent.");
    	utils.Msg ret_msg;
    	RemoteObjectRef ror;	
    	try 
    	{
    		ret_msg = (utils.Msg)ois.readObject();
    	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	// branch according to the answer.
		// receive ROR data, witout check.
		String ro_IPAdr = "";

		System.out.println(ro_IPAdr);

		int ro_PortNum = 0;

		System.out.println(ro_PortNum);

		int ro_ObjKey = 0;

		System.out.println(ro_ObjKey);

		String ro_InterfaceName = "";

		System.out.println(ro_InterfaceName);

		// make ROR.
		
		ror = new RemoteObjectRef(ro_IPAdr, ro_PortNum, ro_ObjKey, ro_InterfaceName);
	   
	// close the socket.
	sock.close();
		
	// return ROR.
	return ror;
    }

    // rebind a ROR. ROR can be null. again no check, on this or whatever. 
    // I hate this but have no time.
    public void rebind(String serviceName, RemoteObjectRef ror) throws IOException
    {
	// open socket. same as before.
	Socket sock = new Socket(Host, Port);
	    
	// get TCP streams and wrap them. 
	ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
	ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
	System.out.println("stream made.");
	utils.Msg msg = new utils.Msg();
	msg.set_msg_tp(MESSAGE_TYPE.REBIND);
	// it is locate request, with a service name.
	oos.writeObject(msg);
	utils.Msg ret_msg;

	// it is a rebind request, with a service name and ROR.
	System.out.println("rebind");
	// it also gets an ack, but this is not used.
	try {
		utils.Msg ack = (utils.Msg)ois.readObject();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	// close the socket.
	sock.close();
    }
    
    public static void main(String args[])
    {
    	String Host = "0.0.0.0";
    	int port = 10000;
    	M_Registry m_registry = new M_Registry(Host, port);
    	try {
			m_registry.lookup("test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
} 
  