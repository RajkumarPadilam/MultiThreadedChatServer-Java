/*Name: RAJKUMAR PADILAM
UTA ID: 1001001479
*/
/*
Multi Threaded Server Program*/

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/*
 * A chat server that delivers public and private messages.
 */
public class MultiThreadChatServer {

  // The server socket for communicating with clients.
  private static ServerSocket serverSocket = null;
  
  // The client socket for sending and receiving data with server.
  private static Socket clientSocket = null;

  // This chat server can accept up to maxClientsCount clients' connections.
  private static final int maxClientsCount = 10;
  
  //Creating an array of threads which handles multiple client
  private static final clientThread[] threads = new clientThread[maxClientsCount];
  static Connection conn;
  static DisplayServer displayObject=new DisplayServer();
  public static void main(String args[]) {

    // The default port number.
    int portNumber = 2222;
    displayObject.createUI();
    displayObject.serverMessage.append("The server is currently running on :"+portNumber+"\n");
    if (args.length < 1) {
    	//Displays information about port number on which server is running
      System.out.println("Usage: java MultiThreadChatServer <portNumber>\n" + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }

    /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
     */
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) {
            (threads[i] = new clientThread(clientSocket, threads,displayObject)).start();
            break;
          }
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. When a client leaves the chat room this thread informs also
 * all the clients about that and terminates.
 */
class clientThread extends Thread {

  private DataInputStream is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread[] threads;
  private int maxClientsCount;
  String destName="";
  DisplayServer display;

  public clientThread(Socket clientSocket, clientThread[] threads, DisplayServer display) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
    this.display=display;
  }

  @SuppressWarnings("deprecation")
public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread[] threads = this.threads;
    Connection conn;

    try {
      /*
       * Create input and output streams for this client.
       */
      is = new DataInputStream(clientSocket.getInputStream());
      os = new PrintStream(clientSocket.getOutputStream());
      
      //Getting name of the client
      os.println("Enter your name.");
      @SuppressWarnings("deprecation")
      String name = is.readLine().trim();
      display.serverMessage.append("\n \n New Client named "  +name+  "has entered the chat room");
      
      
      
      //Getting the decision from the client regarding the online display
      os.println("Enter y if you want to make your presence feel to other users, else enter n.");
      String online = is.readLine().trim();
      
      //Inserting them into database through JDBC connection
      try{
  		Class.forName("oracle.jdbc.driver.OracleDriver");
  		//System.out.println("Connecting to a selected database...");
  		
  		conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vivek","123");
  		//System.out.println("Connected database successfully...");
  		
  		java.sql.Statement stmt=conn.createStatement();
  		PreparedStatement ps = conn.prepareStatement("insert into users (USERNAME, ONLINEUSER) values ('"+name+"','"+online+"')");
        ps.executeUpdate();
  
        //System.out.println("inserted");
          }
  		catch (Exception e){}
      
      //Getting the name of the client with whom the current client wants to chat with...
	  os.println("Enter name of the person you want to chat with.");
      destName = is.readLine().trim();
      os.println("Hello " + name + " to our chat room.\nTo leave enter /quit in a new line");
    
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null) {
        	  if(name.equals(threads[i].destName))
            threads[i].os.println("<" + name + ">" + line);
          }
        }
      }
      
      os.println("*** Bye " + name + " ***");
      display.serverMessage.append("\n\n Client"  +name+  "has left the chatroom");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      for (int i = 0; i < maxClientsCount; i++) {
        if (threads[i] == this) {
          threads[i] = null;
        }
      }

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}