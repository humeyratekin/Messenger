import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

import javafx.event.ActionEvent;

public class Login {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		JFrame login=new JFrame("Login");
		JPanel panel=new JPanel();
		final JTextField loginName=new JTextField(20);
		JButton enter=new JButton("Login");
		
		panel.add(loginName);
		panel.add(enter);
		login.setSize(300,100);
		login.add(panel);
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		enter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				try {
					ChatClient client=new ChatClient(loginName.getText());
					login.setVisible(false);
					login.dispose();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
			
		});
		
		loginName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				
					try {
						ChatClient client=new ChatClient(loginName.getText());
						login.setVisible(false);
						login.dispose();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
		
		
		
	
		
		
	}
}
