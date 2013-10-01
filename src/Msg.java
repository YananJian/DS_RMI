public class Msg implements java.io.Serializable{

    // required: type of message (identifies desired action by recipient)
    private Constants.MESSAGE_TYPE msg_type = null;

    // required: mailing address
    private String to_ip = "";
    private int to_port = 0;

    // optional: return address if expecting a return message
    private String sender_ip = "";
    private int sender_port = 0;

    // optional: include a remote object
    private Remote r = null;
	

    public Msg(Constants.MESSAGE_TYPE msg_type, String to_ip, int to_port) 
    {
	this.msg_type = msg_type;
	this.to_ip = to_ip;
	this.to_port = to_port;
    }

    public void set_sender_ip(String sender_ip) 
    {
	this.sender_ip = sender_ip;
    }

    public void set_sender_port(int sender_port) 
    {
	this.sender_port = sender_port;
    }

    public void set_remote(Remote r) 
    {
	this.r = r;
    }
	
}
