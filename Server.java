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
			
			//sv.printRequest(packet);

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

	public String parseRequest(String packet) throws IOException
	{
		String msg = "HTTP/1.0 200 OK\r\nContent-Length: 144\r\nContent-Type: text/html\r\n\r\n\r\n<html>\n\n";
		String coda = "\n</html>\n";
		TimeClient tc = new TimeClient();
		String response = tc.getTime();
		String gmt = convertTime("GMT", response);
		String est = convertTime("EST", response);
		String pst = convertTime("PST", response);
		if(packet.contains("GET /time?zone=all"))
		{
			msg += gmt+"<p>"+est+"<p>"+pst;
		}
		else if(packet.contains("GET /time?zone=est"))
		{
			msg+=est;
		}
		else if(packet.contains("GET /time?zone=pst"))
		{
			msg+=pst;
		}
		else
		{
			String inv = "Invalid request";
			msg+= inv+coda;
		}
		return msg;
	}

	public String convertTime(String zone, String timemsg)
	{
		if(!(zone.equals("GMT") || zone.equals("PST") || zone.equals("EST")))
			return "invalid date/time zone input\n\n";

		String[] utcarr = timemsg.split(" ");
		String datetime = "20"+utcarr[1]+"T"+utcarr[2];
		String dst = utcarr[3];
		
		String prefix = zone + " Date/Time: ";

		LocalDateTime time = LocalDateTime.parse(datetime);
		time = daylight(zone,dst,time);
		datetime = time.getMonthValue()+"/"+time.getDayOfMonth()+"/"+time.getYear()+", ";
		
		int hr = time.getHour();
		String ampm = "AM";
		if(hr==0)
			hr=12;
		else if(hr>12)
		{
			hr-=12;
			ampm="PM";
		}
		datetime+=hr+":"+time.getMinute()+" "+ampm;
		
		return prefix + datetime;
	}

	public LocalDateTime daylight(String zone, String dst, LocalDateTime gmt)
	{
		if(zone.equals("GMT"))
			return gmt;
		int estoffset = 5;
		int pstoffset = 8;
		if(!dst.equals("00"))
		{
			estoffset--;
			pstoffset--;	
		}
		if(zone.equals("EST"))
			return gmt.minusHours(estoffset);
		else	
			return gmt.minusHours(pstoffset);
	}
}