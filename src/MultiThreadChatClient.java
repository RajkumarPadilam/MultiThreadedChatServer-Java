import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadChatClient {

  // The client socket
  private static Socket clientSocket = null;
  // The output stream
  private static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;

  private static boolean closed = false;
  
  private static  BufferedReader br=null;
  
  public static StringBuffer htmlContent=new StringBuffer();
  
  public static void main(String[] args) {

	  // Default port
	int portNumber = 2222;
    
	 // The default host.
    String host = "localhost";

     // Defualt Filename
    String filename="index.html";
   
	try
	{
		switch(args.length){
		case 1: host=args[0]; break;
		case 2: host=args[0];portNumber=Integer.parseInt(args[1]); break;
		case 3: host=args[0];portNumber=Integer.parseInt(args[1]); filename=args[2];break;
 		}
	}
	catch(NumberFormatException e)
	{
		System.out.println("The entered port is not a valid integer");
	}
         
    try {
    
    	//Open the Client Socket
      clientSocket = new Socket(host, portNumber);
      
      //Get the Input and Ouput Streams for the client Socket
      InputStream is = clientSocket.getInputStream();
      br=new BufferedReader(new InputStreamReader(is));      
      os = new PrintStream(clientSocket.getOutputStream());
    
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }
    
    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
    if (clientSocket != null && os != null && br != null) {
      try {

		os.println("GET /"+filename+" HTTP/1.1");        
		
		String responseLine;
		
		while ((responseLine = br.readLine()) != null) { 
			 System.out.println(responseLine);
			 htmlContent.append(responseLine);
			 
		  }
		writeHTML(htmlContent);
		
         //Close the output stream, close the input stream, close the socket.         
		 os.close();
         br.close();
         clientSocket.close();
		 System.out.println("Connection closed");
		 
       } catch (Exception e) {
        System.err.println("IOException:  " + e);
      }
    }
  }
  
  public static void writeHTML(StringBuffer line)
  {
  	try 
		{
			PrintWriter pw=new PrintWriter(new FileWriter("ownerList.txt",false));
			
			pw.println(line.toString());
	        pw.flush();
			pw.close();
			
		} catch (FileNotFoundException e) 
		{				
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
  
}