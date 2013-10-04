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
import java.util.Vector;
import java.io.*;
import utils.*;
import utils.Constants.*;

public class Registry {

    private HashMap<String, Remote> obj_map = new HashMap<String, Remote>(); // obj name -> obj
    private HashMap<String, String> server_map = new HashMap<String, String>(); // obj name -> serverIP_port
    private int port = utils.Constants.PORT_MASTER;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerSocket listener = null;

    public Vector<String> list()
    {
	Vector<String> list = new Vector<String>();
	for (String key : this.obj_map.keySet()) {
	    list.add(key);
	}
	return list;
    }

    public void lookup()
    {
	// TODO
    }

    public void bind(String url, Object o)
    {
	// TODO
    }

    public void rebind(String url, Object o)
    {
	// TODO
    }

    public void unbind(String url, Object o)
    {
	// TODO
    }

    public utils.Msg process(utils.Msg msg, ObjectOutputStream oos) throws IOException
    {
	System.out.println(" > processing message");
	utils.Msg reply = new utils.Msg();
	utils.Constants.MESSAGE_TYPE msg_type = msg.get_msg_tp();

	if (msg_type == MESSAGE_TYPE.LIST) { 
	    reply.set_list(this.list());
	    System.out.println(" > replying with RET_LIST message");
	    reply.set_msg_tp(MESSAGE_TYPE.RET_LIST);
	}
	if (msg_type == MESSAGE_TYPE.LOOKUP) {
	    // TODO:
	    // see if a server has this object;
	    // if so, forward the lookup message to the appropriate server (who will return the stub)
	}
	if (msg_type == MESSAGE_TYPE.BIND) {
	    this.bind(msg.get_url(), msg.get_object());
	}
	if (msg_type == MESSAGE_TYPE.REBIND) {
	    this.rebind(msg.get_url(), msg.get_object());
	}
	if (msg_type == MESSAGE_TYPE.UNBIND) {
	    this.unbind(msg.get_url(), msg.get_object());
	}
	else { 
	    System.out.println(" > replying with DEFAULT message");
	    reply.set_msg_tp(MESSAGE_TYPE.DEFAULT);
	}
	return reply;
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
		utils.Msg reply = this.process(msg, oos);  
		oos.writeObject(reply);
		oos.flush();
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