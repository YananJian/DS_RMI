// HashMap<String, Remote> : name of obj (i.e. url of obj) -> obj 
// Communicates with client stubs, server skeleton(s):
//      can return list of available object names (urls) (client) - list()
//      can send a serialized stub of object (client) - lookup() 
//      can update Map of available stuff (server) - bind(), rebind(), unbind()

// bind(url, object);
// unbind(url, object); 

// Message types will need to include: 
// list, bind, rebind, unbind, lookup 
// ret_list, ack_bind, ack_rebind, ack_unbind, ret_lookup

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.HashMap;
import java.io.*;
//import java.lang.reflect.*;

public class Registry {

    private HashMap<String, Remote> obj_map = new HashMap<String, Remote>();
    private String manager_IP = Constants.IP_MASTER;
    private int manager_port = Constants.PORT_MASTER;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket sock = null;

    public void listen() throws IOException, ClassNotFoundException
    {
	ois = new ObjectInputStream(sock.getInputStream());
	System.out.println("Listening for messages...");
	while (true) { 
	    Msg msg = (Msg) ois.readObject();
	    System.out.println("Got a msg");
	    // TODO: process message here
	    // this.process(msg);  
	}
    }

    public void connect() throws InterruptedException, ClassNotFoundException
    {
	try {
	    sock = new Socket(this.manager_IP, this.manager_port);
	    // Currently: 
	    //      - no greeting, i.e. assume registry comes online first 
	    //      - (could alter this for robustness)
	    //      - no output socket created until an incoming message is received
	    this.listen();
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException 
    {						  
	Registry registry = new Registry();
	registry.connect();
    }

}