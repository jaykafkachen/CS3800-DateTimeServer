import java.net.*; 
import java.io.*; 
import java.text.*;
import java.time.*;
import java.util.*;

class Server 
{

	//initialize socket and input stream 
	/*private Socket socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in = null;
	private DataOutputStream out = null;*/

	public static void main(String args[]) throws Exception
	{ 
		Server sv = new Server();

		ServerSocket server = new ServerSocket(5000);
		
		while(!server.isClosed()) 
		{ 
			Socket conn = server.accept();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			DataOutputStream out = new DataOutputStream(conn.getOutputStream()); 
			
			String packet = in.readLine();
			
			sv.printRequest(packet);

			
			String str = sv.parseRequest(packet);

			out.writeBytes(str);          
            out.flush();
			out.close();
			
			conn.close();
		} 
		server.close();
	}
	
	public String printRequest(String packet)
	{
		char[] pkt = packet.toCharArray();
		for(char c:pkt)
		{
			System.out.print(c);
		}
		System.out.println();
		return packet;
	}

	public String parseRequest(String packet)
	{
		String msg = "HTTP/1.0 200 OK\r\nContent-Length: 120\r\nContent-Type: text/html\r\n\r\n\r\n<html>\n\n";
		String coda = "\n</html>\n";

		if(packet.contains("GET /time?zone=all"))
		{
			
		}
		else if(packet.contains("GET /time?zone=est"))
		{

		}
		else if(packet.contains("GET /time?zone=pst"))
		{

		}
		else
		{
			String inv = "Invalid request";
			msg.append(inv+coda);
		}
		return msg;
	}

}