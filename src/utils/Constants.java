package utils;

public class Constants {

    public static final int PORT_REGISTER = 12345;
    public static final String IP_REGISTER = "0.0.0.0";
    
    

   
    public enum MESSAGE_TYPE {
	LIST, BIND, REBIND, UNBIND, LOOKUP,
	    RET_LIST, RET_BIND, RET_REBIND, RET_UNBIND, RET_LOOKUP,
	    DEFAULT, INVOKE};

}
