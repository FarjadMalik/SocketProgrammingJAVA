// File Name GreetingClient.java

import java.net.*;
import java.io.*;

public class Client
{
	private static String filepath="";
	private static String filename="Hello.txt";
   public static void main(String [] args)
   {
      String serverName = "192.168.119.128";
      int port = Integer.parseInt("55556");
      try
      {
         System.out.println("Connecting to " + serverName
                             + " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to "
                      + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =
                       new DataOutputStream(outToServer);

         out.writeUTF("Hello from "
                      + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         fileRead(client,out);
         out.writeUTF("Done.");
         System.out.println("Done.");
         DataInputStream in =
                        new DataInputStream(inFromServer);
         System.out.println("Server says " + in.readUTF());
         
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
   
   public static void fileRead(Socket client,DataOutputStream out) {
		
	   
		
		try {
				        
			FileReader r = new FileReader(filepath+filename);
			out.writeUTF(filename);
			BufferedReader br = new BufferedReader(r);
			String line;
			
		    while((line = br.readLine()) != null){
		    	out.writeUTF(line);		    	
		    }
		    br.close();
		    
		} catch (FileNotFoundException e) {
			// 
			e.printStackTrace();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		} finally{
			//br.close();
		}
	    
		
	}
}