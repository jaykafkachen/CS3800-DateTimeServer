import java.net.*; 
import java.io.*; 

public class TimeClient 
{ 
	//private Socket socket		 = null; 
	//private DataInputStream input = null;
	
	String time = "time.nist.gov"; 
	int port = 13;

	public TimeClient() {	}
	public static void main(String args[]) throws Exception
	{ 
		//TimeClient tc = new TimeClient();	
		//String timemsg = tc.getTime();
	} 

	public String getTime() throws IOException
	{
		InetAddress timeIP = InetAddress.getByName(time);
		Socket client = new Socket(timeIP, port);	
		char[] buffer = new char[32];   //buffer large enough to grab the 1st 4 values for date/time
									    //all other values are irrelevant for the date/time response
		try
		{	
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String rq = "get the date"; //DAYTIME protocol will ignore data in request message
			out.writeBytes(rq);         //send TCP request to time.nist.gov
			out.flush();
			in.read(); 				    //consumes first newline \n before date msg
			in.read(buffer);			//reads time response to buffer[]
			out.close();

		} catch(IOException e)
		{	
			e.printStackTrace();
			buffer = ("time not found").toCharArray();	
		}

		client.close();
		return new String(buffer);
	}
} 
