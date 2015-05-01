/*Name: RAJKUMAR PADILAM
UTA ID: 1001001479
*/
/*
Server display window Program*/

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DisplayServer {

	JTextArea serverMessage;
	
	DisplayServer(){
		//super();
		//createUI();
	}
	
	
    public void createUI()
    {
    	
        JFrame guiFrame = new JFrame();
        serverMessage=new JTextArea();
        
        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("SERVER");
        guiFrame.setSize(900,450);
      
        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);
        
                guiFrame.add(serverMessage);
        // guiFrame.add(typingWindow, BorderLayout.SOUTH);
        //make sure the JFrame is visible
        guiFrame.setVisible(true); 
    }
	
	
}
