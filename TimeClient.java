import java.net.*; 
import java.io.*; 

public class TimeClient 
{ 
	private Socket socket		 = null; 
	private DataInputStream input = null;
   
	
	public static void main(String args[]){ 
		TimeClient client = new TimeClient("time.nist.gov");		
	} 
} 
