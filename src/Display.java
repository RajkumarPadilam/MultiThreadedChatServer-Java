/*Name: RAJKUMAR PADILAM
UTA ID: 1001001479
*/
/*
Display window Program*/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Display {

	//initializing Text area and Text Field
	JTextArea clientMessage,serverMessage;
	JTextField clientMessageInput;
	
	boolean sendMessage=false;
	String messageTyped=null;
	
	
	//Constructor of Display class which calls createUI() method
	Display(){
		super();
		createUI();
	}
	
	public void readMessage()
	{
		messageTyped=clientMessageInput.getText().trim();
		
	}
	
    public void createUI()
    {
    	//Creating the object of JFrame()
        JFrame guiFrame = new JFrame();
        
        //Makes sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI");
        guiFrame.setSize(500,500);
      
        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);
        
        //The first JPanel contains a JLabel and JCombobox
        final JPanel comboPanel = new JPanel();
        comboPanel.setLayout( new BoxLayout( comboPanel, BoxLayout.Y_AXIS ) );
        	
        clientMessage=new JTextArea("");		
        clientMessage.setEditable(false);
        clientMessage.setFocusable(false);
		
        serverMessage=new JTextArea("Receiver");
        clientMessageInput=new JTextField("",20);
        //clientMessageInput.setSize(100, 20);
        
        JButton send=new JButton("SEND");
        send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				readMessage();
				sendMessage=true;
				clientMessageInput.setText("");
				clientMessageInput.requestFocus();
			}
		});
        
        
        
        //Setting the properties of message window
        JPanel typingWindow = new JPanel();
        typingWindow.setVisible(true);
        //typingWindow.setLayout( new GridLayout(0 ,3) );
        typingWindow.add(clientMessageInput);
        typingWindow.add(send);
        
        
        comboPanel.add(clientMessage);

        guiFrame.add(comboPanel, BorderLayout.NORTH);
        guiFrame.add(typingWindow, BorderLayout.SOUTH);
        
        //make sure the JFrame is visible
        guiFrame.setVisible(true); 
    }
	
}
