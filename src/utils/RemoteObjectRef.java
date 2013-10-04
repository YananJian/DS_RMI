package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import utils.Constants.MESSAGE_TYPE;

public class RemoteObjectRef implements java.io.Serializable{
	
	private static String IP_adr;
    private static int Port;
    private int Obj_Key;
    private String Obj_Name;
    private String[] Interface_names;
    

    public RemoteObjectRef(String ip, int port, int obj_key, String[] riname) 
    {
    	setIP_adr(ip);
    	setPort(port);
    	setObj_Key(obj_key);
    	setRemote_Interface_Name(riname);
    }

    public static Object invoke(String func_name, Object params[])
    {
    	Object rets = null;
    	try {
			Socket sock = new Socket(IP_adr, Port);
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());			
			Msg msg = new Msg();
			msg.set_msg_tp(MESSAGE_TYPE.INVOKE);
			msg.setFunc_name(func_name);
			msg.setParams(params);
			oos.writeObject(msg);
			Msg ret_msg = (Msg) ois.readObject();
			rets = ret_msg.getRets();			
			return rets;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return rets;
    }
    // this method is important, since it is a stub creator.
    // 
    public Object localise()
    {
    	Object o = null;
    	 
        try {
        	System.out.println("Obj name:"+Obj_Name);
        	Class c = Class.forName(Obj_Name);
			o = c.newInstance();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return o;
	// Implement this as you like: essentially you should 
	// create a new stub object and returns it.
	// Assume the stub class has the name e.g.
	//
	//       Remote_Interface_Name + "_stub".
	//
	// Then you can create a new stub as follows:
	// 
	//       Class c = Class.forName(Remote_Interface_Name + "_stub");
	//       Object o = c.newinstance()
	//
	// For this to work, your stub should have a constructor without arguments.
	// You know what it does when it is called: it gives communication module
	// all what it got (use CM's static methods), including its method name, 
	// arguments etc., in a marshalled form, and CM (yourRMI) sends it out to 
	// another place. 
	// Here let it return null.
    }

	public String getIP_adr() {
		return IP_adr;
	}

	public void setIP_adr(String iP_adr) {
		IP_adr = iP_adr;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	public int getObj_Key() {
		return Obj_Key;
	}

	public void setObj_Key(int obj_Key) {
		Obj_Key = obj_Key;
	}

	public String[] getRemote_Interface_Name() {
		return Interface_names;
	}

	public void setRemote_Interface_Name(String []remote_Interface_Name) {
		Interface_names = remote_Interface_Name;
	}

	public String getObj_Name() {
		return Obj_Name;
	}

	public void setObj_Name(String obj_Name) {
		Obj_Name = obj_Name;
	}

}
