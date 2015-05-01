import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public final class WebServer {

	
	static int portNumber=2222;
	static ServerSocket serverSocket=null;
	static Socket clientSocket=null;
	
	public static void main(String args[]){
		
		if(args.length>0){
			try{
				portNumber=Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				System.out.println("The entered port is not a valid integer");
			}
		}
		
		
		try {
		      serverSocket = new ServerSocket(portNumber);
		    } catch (IOException e) {
		      System.out.println(e);
		    }
		
		System.out.println("\nServer is now is now listening for requests on port :"+portNumber);
		
		while (true) {
			try {
				
				// Listen for a TCP connection request.
				clientSocket=serverSocket.accept();	
				System.out.println("Client Connection accepted");
				
				// Construct an object to process the HTTP request message.
				HttpRequest request = new HttpRequest( clientSocket );
				
				// Create a new thread to process the request.
				Thread thread = new Thread(request);

				// Start the thread.
				thread.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			

		}
		
	}
}
