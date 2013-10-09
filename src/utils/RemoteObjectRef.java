package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;

import examples.*;
import utils.Constants.MESSAGE_TYPE;

public class RemoteObjectRef implements java.io.Serializable{
	
    
	private String IP_adr;
    private int Port;
    private int Obj_Key;
    private String Obj_Name;
    private String[] Interface_names;
    

    public RemoteObjectRef(String ip, int port, int obj_key, String[] riname, String Obj_name) 
    {
    	setIP_adr(ip);
    	setPort(port);
    	setObj_Key(obj_key);
    	setObj_Name(Obj_name);
    	setRemote_Interface_Name(riname);
    }

    public static Object invoke(String url, String func_name, Object params[],String serverip, int serverport)
    {
    	Object rets = null;
    	try {
			System.out.println("Server ip_addr:"+serverip);
			System.out.println(serverport);
			Socket sock = new Socket(serverip, serverport);
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());			
			Msg msg = new Msg();
			msg.set_msg_tp(MESSAGE_TYPE.INVOKE);
			msg.setObj_name(url);
			
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
        try 
        {
        	Class<?> c = Class.forName("examples.Test_stub");
			
			Constructor<?> constructor = c.getConstructor(String.class, int.class);
			System.out.println("In Remote Object localise, server ip:"+IP_adr);
			o = constructor.newInstance(IP_adr, Port);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return o;
	
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
		this.Interface_names = remote_Interface_Name;
	}

	public String getObj_Name() {
		return Obj_Name;
	}

	public void setObj_Name(String obj_Name) {
		this.Obj_Name = obj_Name;
	}

}
