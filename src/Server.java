// File Name GreetingServer.java

import java.net.*;
import java.io.*;

public class Server extends Thread
{
   private ServerSocket serverSocket;
   private static String filePath = "Server_";
   
   public Server(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to "
                  + server.getRemoteSocketAddress());
            DataInputStream in =
                  new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            
            downloadFile(server,in);
            System.out.println("Done.");
            DataOutputStream out =
                 new DataOutputStream(server.getOutputStream());
            
            out.writeUTF("Thank you for connecting to "
              + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   
   private void downloadFile(Socket server,DataInputStream in) throws IOException {
	   String filename = in.readUTF();
	   System.out.println(filePath+filename);
	   PrintWriter writer = new PrintWriter(filePath+filename,"UTF-8");
	   String line;
         try {
		
        	 while(!(line = in.readUTF()).equalsIgnoreCase("Done.")){
        		 System.out.println(line);
        		 writer.println(line);
        	 }
        	 
         } catch (IOException e) {
 			// 
 			e.printStackTrace();
 		}finally{
 			writer.flush();
 			writer.close();
 		}
	
   }

public static void main(String [] args)
   {
      int port = Integer.parseInt("55555");
      try
      {
         Thread t = new Server(port);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}