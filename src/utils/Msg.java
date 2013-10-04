package utils;

import utils.Constants.MESSAGE_TYPE;
import java.util.Vector;

public class Msg implements java.io.Serializable{
    
    private MESSAGE_TYPE msg_type = null; // required: type of message (identifies desired action by recipient)
    private String func_name; 
    private String obj_name;
    private String params[];
    private Vector<String> list; // for LIST requests only
    private String url; // for BIND, REBIND, UNBIND
    private Object object;
    private RemoteObjectRef remote_ref;
    
    public void set_msg_tp(MESSAGE_TYPE tp)
    {
    	this.msg_type = tp;
    }
    
    public MESSAGE_TYPE get_msg_tp()
    {
    	return this.msg_type;
    }

    public void set_list(Vector<String> list)
    {
	this.list = list;
    }

    public Vector<String> get_list() 
    {
	return this.list;
    }

    public void set_url(String url) 
    {
	this.url = url;
    }

    public String get_url() 
    {
	return this.url;
    }

    public void set_object(Object object) 
    {
	this.object = object;
    }

    public Object get_object()
    {
	return this.object;
    }

    public RemoteObjectRef getRemote_ref() 
    {
	return this.remote_ref;
    }
    
    public void setRemote_ref(RemoteObjectRef remote_ref) 
    {
	this.remote_ref = remote_ref;
    }
    
    public String getObj_name() 
    {
	return this.obj_name;
    }
    
    public void setObj_name(String obj_name) 
    {
	this.obj_name = obj_name;
    }

}
