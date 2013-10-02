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
import utils.*;

public class Registry {

    private HashMap<String, Remote> obj_map = new HashMap<String, Remote>();
    private int port = utils.Constants.PORT_MASTER;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerSocket listener = null;

    public void process(utils.Msg msg) 
    {
	System.out.println(" > pretending to process message");
    }

    public void listen() throws IOException, ClassNotFoundException
    {
	while (true) {
	    System.out.println("Listening for messages...");
	    Socket sock = listener.accept();
	    try {
		ois = new ObjectInputStream(sock.getInputStream());
		oos = new ObjectOutputStream(sock.getOutputStream());
		utils.Msg msg = (utils.Msg) ois.readObject();
		System.out.println(" > got a message!");
		this.process(msg);  
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	}
    }

    public void connect() throws InterruptedException, ClassNotFoundException
    {
	try {
	    listener = new ServerSocket(this.port);
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