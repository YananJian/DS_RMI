import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.lang.*;

import utils.Constants.MESSAGE_TYPE;
import utils.Msg;

public class S_Registry implements Runnable {
	
	int port;
	HashMap<String, Object> reg;
	public S_Registry(int port)
	{
		this.port = port;
	}

	public Msg process(Msg msg)
	{
		System.out.println("MSG TYPE:"+msg.get_msg_tp());
		if (msg.get_msg_tp() == MESSAGE_TYPE.LOOKUP)
		{
			String name = msg.getObj_name();
			
		}
		else if (msg.get_msg_tp() == MESSAGE_TYPE.REBIND)
		{
			
		}
		Msg ret_msg = new Msg();
		ret_msg.set_msg_tp(MESSAGE_TYPE.RET_LIST);
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
                	System.out.println("Got the msg");
                			
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
		S_Registry s_registry = new S_Registry(12345);
		Thread t = new Thread(s_registry);
		t.start();
	}
}
