import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUI extends JFrame implements ActionListener{

	private static GUI gui = null;
	JTextField jtf;
	JLabel status;
	JButton startButton, stopButton;
	Boolean stopServerBoolean = false;
	SocketListener s;
	Thread t;
	public static GUI getGUI(){
		
		if(gui == null){
			gui = new GUI();
			
		}
		
			return gui;
		
	}
	public GUI(){
		
	
		this.setTitle("ContrOLBer server");
		
		setLayout(new GridLayout(2,2));
		JLabel portLabel = new JLabel("Port: ");
		jtf = new JTextField(); 
		
		JPanel jP = new JPanel();
		jP.setLayout(new GridLayout(2,1));
		
		JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new GridLayout(1,2));
		
		startButton = new JButton("Start server");
		stopButton = new JButton("Stop Server");
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		status = new JLabel("Waiting to initiate");
		
		stopButton.setEnabled(false);
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		jP.add(portLabel);
		jP.add(jtf);
		jP.add(startButton);
		jP.add(stopButton);
		//jP.add(buttonPanel);
		jP.setVisible(true);
		startButton.setVisible(true);
		jtf.setVisible(true);
		
		this.add(status);
		this.add(jP);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		this.setPreferredSize(getSize());
	}
	
	public boolean getStopServerBoolean(){
		return stopServerBoolean;
	}
	
	public void setLabelText(String s){
	
		this.status.setText(s);
		this.status.repaint();
		status.paintImmediately(new Rectangle(status.getX(), status.getY(), status.getWidth(), status.getHeight()));
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource() == startButton){
			stopButton.setEnabled(true);
			System.out.println("stopButton is true");
			stopServerBoolean = false;	
			int portInt = Integer.parseInt(jtf.getText());
		
			s =  new SocketListener(portInt);
		
			t = new Thread(s);
				
		
			t.start();
		
		}
		else if(ae.getSource() == stopButton){
			System.out.println("stopButton");
			setLabelText("stopped");
			t.interrupt();
			s.closeStreams();
			stopServerBoolean = true;
		}
	}
	
}
