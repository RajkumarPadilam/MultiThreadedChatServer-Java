/*Name: RAJKUMAR PADILAM
UTA ID: 1001001479
*/
/*
Multi Threaded Client Program*/

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadChatClient implements Runnable {

  // The client socket for sending and receiving data with server.
  private static Socket clientSocket = null;
  
  // The output stream to display results
  private static PrintStream os = null;
  
  // The input stream to store input data
  private static DataInputStream is = null;

  //A Buffer to store data which is read from input line
  private static BufferedReader inputLine = null;
  
  private static boolean closed = false;
  
  static Display displayObject=new Display();
  
  public static void main(String[] args) {

    // The default port.
    int portNumber = 2222;

    // The default host.
    String host = "localhost";

    if (args.length < 2) {
      System.out
          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
              + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }
    
    /*
     * Open a socket on a given host and port. Open input and output streams.
     */
    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    }
    catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } 
    catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }
    
    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
    if (clientSocket != null && os != null && is != null) {
      try {

   	  //This part reads the lines from client and pass it to the server
	  
	  
        /* Create a thread to read from the server. */
        new Thread(new MultiThreadChatClient()).start();
        while (!closed) {
        	String line;
        	//String line=inputLine.readLine().trim();   // reads whatever you typed
        	if(displayObject.sendMessage)
        	{
        	  line=displayObject.messageTyped.trim();
              displayObject.clientMessage.append("\n"+line);	// prints whatever you typed, in the window
              os.println(line);//inputLine.readLine().trim());
              displayObject.sendMessage=false;
        	}
        		
        }
        /*
         * Close the output stream, close the input stream, close the socket.
         */
        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  /*
   * Create a thread to read from the server. (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
	 
	 
    String responseLine;
    try {
	// reads from the server , and displays it in the Window and also on console
      while ((responseLine = is.readLine()) != null) {
    	  
    	  if(responseLine.indexOf("quit")!=-1)
    		  displayObject.clientMessage.setText("");
    	 
    	  //if(responseLine.indexOf('<')!=-1)    		  
    	  displayObject.clientMessage.append("\n"+responseLine); // displays on window
        
    	  //System.out.println(responseLine); // displays on console
        
    	  if (responseLine.indexOf("Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}