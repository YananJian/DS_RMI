/*
each server will have an instance of M_Registry
should be able to call all Registry fns: bind, rebind, unbind, lookup, list
M_Registry will send appropriate messages to S_Registry
for lookup and list, must wait for reply from S_Registry and return some value
    (speed of M_Registry dependent on speed of S_Registry)
*/

package rmi;

import java.util.*;
import java.net.*;
import java.io.*;

import utils.Constants.MESSAGE_TYPE;
import utils.RemoteObjectRef;
import utils.Constants.*;
import utils.*;

public class M_Registry 
{ 

    // IP and Port of S_Registry
    //static String S_IP = utils.Constants.S_IP;
    //static int S_Port utils.Constants.S_Port;;

    // own IP and Port
    static String M_IP;
    static int M_Port;

    public M_Registry(String IPAdr, int PortNum)
    {
    	M_IP = IPAdr;
    	M_Port = PortNum;
    }

    public static Msg communicate(Msg msg)
    {
    	Socket sock;
    	Msg ret_msg = null;
	try {
	    sock = new Socket(M_IP, M_Port);
	    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
	    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
	    oos.writeObject(msg);
	    ret_msg = (utils.Msg) ois.readObject();
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

    public Vector<String> list()
    {
	utils.Msg msg = new utils.Msg();
	msg.set_msg_tp(MESSAGE_TYPE.LIST);
	msg = communicate(msg);
	return msg.get_list();
    }
    
    // returns the ROR (if found) or null (if else)
    public RemoteObjectRef lookup(String url) throws IOException
    {
    	Msg msg = new Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.LOOKUP);
    	msg.set_url(url);       
    	msg = communicate(msg);
	RemoteObjectRef ror = msg.getRemote_ref();
	return ror;
    }

    // bind a ROR. ROR can be null. 
    public void bind(String url, RemoteObjectRef ror) throws IOException
    {
    	utils.Msg msg = new utils.Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.BIND);
    	msg.set_url(url);
    	msg.setRemote_ref(ror);
    	msg = communicate(msg);	
    }

    // rebind a ROR. ROR can be null.
    public void rebind(String url, RemoteObjectRef ror) throws IOException
    {
    	utils.Msg msg = new utils.Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.REBIND);
    	msg.set_url(url);
    	msg.setRemote_ref(ror);
    	msg = communicate(msg);	
    }
    
    // unbind a url.
    public void unbind(String url) throws IOException
    {
    	utils.Msg msg = new utils.Msg();
    	msg.set_msg_tp(MESSAGE_TYPE.UNBIND);
    	msg.set_url(url);
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
  
