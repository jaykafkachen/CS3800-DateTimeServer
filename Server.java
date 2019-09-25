import java.net.*; 
import java.io.*; 
import java.text.*;
import java.time.*;
import java.util.*;

public class Server 
{ 
	//initialize socket and input stream 
	private Socket		 socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in	 = null;
	private DataOutputStream out = null;

	
	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
	} 
} 
