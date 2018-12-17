import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.*;
import java.net.*;
import javax.swing.*;

import javafx.event.ActionEvent;

public class ChatClient extends JFrame implements Runnable {

	Socket socket;
	JTextArea textArea;
	JButton send,logout;
	JTextField textField;
	
	Thread thread;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	
	String LoginName;
	
	ChatClient(String login) throws UnknownHostException, IOException{
		super(login);
		LoginName=login;
		
		addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e) {
				try {
					dataOut.writeUTF(LoginName+" LOGOUT");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(1);
				
			}
		});
		
		
		textArea=new JTextArea(18,50);
		textField=new JTextField(50);
		
		send=new JButton("Send");
		logout=new JButton("Logout");
		
		socket=new Socket("localhost",5217);
		
		dataIn=new DataInputStream(socket.getInputStream());
		dataOut=new DataOutputStream(socket.getOutputStream());
		
		dataOut.writeUTF(LoginName);
		dataOut.writeUTF(LoginName+" LOGIN");
		
		thread=new Thread(this);
		thread.start();
		setup();
	}
	
	
	private void setup() {
		// TODO Auto-generated method stub
		
		setSize(600,400);
		JPanel panel=new JPanel();
		
		panel.add(new JScrollPane(textArea));
		panel.add(textField);
		panel.add(send);
		panel.add(logout);
		
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Auto-generated method s
				
				
				
				try {
					if(textField.getText().length()>0) 
						dataOut.writeUTF(LoginName+" "+"DATA "+textField.getText().toString());
					textField.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Auto-generated method s
				
				try {
					dataOut.writeUTF(LoginName+" "+"LOG OUT");
					System.exit(1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					
					try {
						if(textField.getText().length()>0) 
							dataOut.writeUTF(LoginName+" "+"DATA "+textField.getText().toString());
						textField.setText("");
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
							
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		add(panel);
		
		setVisible(true);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true) {
			
			try {
				textArea.append("\n"+dataIn.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
		
	
	
	



