package utils;

public class Constants {

    public static final int PORT_REGISTER = 12345;
    public static final String IP_REGISTER = "128.2.13.143";
    
    public static final int PORT_SERVER = 12346;

   
    public enum MESSAGE_TYPE {
	LIST, BIND, REBIND, UNBIND, LOOKUP,
	    RET_LIST, RET_BIND, RET_REBIND, RET_UNBIND, RET_LOOKUP,
	    DEFAULT, INVOKE};

}
