import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class SocketListener implements Runnable{
	
	GUI gui;
	
	String input = "rumpa";	
	ServerSocket sSocket = null;
	Socket clientSocket = null;
	Robot r;
	PrintWriter out = null;
	int port;
	float x = 1000, y = 1000;
	BufferedReader in = null;
	String xString, yString;
	boolean funcKey = false;
	int[] alphabetArray = {KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I,
			KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N,KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U,
			KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z};
	
	public SocketListener(int port) {
		try {
			r = ZeRobot.getRobot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui = GUI.getGUI();
		this.port = port;
	}
	public void init(){
		try {
			sSocket = new ServerSocket(port);

			gui.setLabelText("listening for input");
			gui.stopButton.setFocusable(true);	
			clientSocket = sSocket.accept();	
			gui.setLabelText("connected");			
			out = new PrintWriter(clientSocket.getOutputStream(), true);	
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	 
			listenToInput();
			closeStreams();
	
		if(!gui.getStopServerBoolean()){
			init();
		}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Unabled to listen to input. Server might have been shut down?");
			gui.setLabelText("closed");
		}
	}
	
	public void listenToInput() throws IOException, SocketException {
		try{
			while(!gui.getStopServerBoolean() &&(input = in.readLine()) != null){			
					readInput(input);				
			}
		}catch(SocketException e){
			System.out.println("Unabled to listen to input. Server might have been shut down?");
			closeStreams();
		}
		
	}

	public void closeStreams(){
		gui.setLabelText("closed");
		
		if(out != null)
			out.close();
		try {
			if(in != null ){
				in.close();
				
			}
			if(clientSocket != null)
				clientSocket.close();
			if(sSocket != null)
				sSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			gui.setLabelText("IOException");
		}
	}
	public void readInput(String input) throws IOException{
		switch (input){
			case "up":
			
			break;
			case "movemouse":
				while((!(xString = in.readLine()).equalsIgnoreCase("up")) && (!(yString = in.readLine()).equalsIgnoreCase("up"))){
					
					try{
					float tempX = Float.parseFloat(xString);
					float tempY = Float.parseFloat(yString);
					
					x =  (x+tempX);	
					y =  (y-tempY);
					}catch(NumberFormatException e){};
					
					r.mouseMove((int) x, (int)y);
					
				}
				r.delay(50);
				
				break;
			case "downleft":
				
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				break;
			case "downright":
				
				r.mousePress(InputEvent.BUTTON3_MASK);
				r.mouseRelease(InputEvent.BUTTON3_MASK);
				break;
			case "keydown":
			//Koden med funcKey nedan används när jag använder min HTC Desire Z som har ett hårdvarutangentbrod med en FN-knappk
				int tempKey = Integer.parseInt(in.readLine())-29;
				
				if(tempKey == 28){
					funcKey = true;
				}
				if(funcKey){
					if(funcKey && tempKey == 16){
						funcKey = false;
						r.keyPress(49);
						r.keyRelease(49);
					}
					else if(funcKey && tempKey == 22){
						funcKey = false;
						r.keyPress(50);
						r.keyRelease(50);
					}
					else if(funcKey && tempKey == 4){
						funcKey = false;
						r.keyPress(51);
						r.keyRelease(51);
					}
					else if(funcKey && tempKey == 17){
						funcKey = false;
						r.keyPress(52);
						r.keyRelease(52);
					}
					else if(funcKey && tempKey == 19){
						funcKey = false;
						r.keyPress(53);
						r.keyRelease(53);
					}
					else if(funcKey && tempKey == 24){
						funcKey = false;
						r.keyPress(54);
						r.keyRelease(54);
					}
					else if(funcKey && tempKey == 20){
						funcKey = false;
						r.keyPress(55);
						r.keyRelease(55);
					}
					else if(funcKey && tempKey == 8){
						funcKey = false;
						r.keyPress(56);
						r.keyRelease(56);
					}
					else if(funcKey && tempKey == 14){
						funcKey = false;
						r.keyPress(57);
						r.keyRelease(57);
					}
					else if(funcKey && tempKey == 15){
						funcKey = false;
						r.keyPress(48);
						r.keyRelease(48);
					}
					else if(funcKey && tempKey == 1){
						funcKey = false;
						r.keyPress(45);
						r.keyRelease(45);
					}
					else if(funcKey && tempKey == 92){
						funcKey = false;
						r.keyPress(16);
						r.keyPress(521);
						r.keyRelease(16);
						r.keyRelease(521);
						
					}
					else if(funcKey && tempKey == 11){
						funcKey = false;
						r.keyPress(16);
						r.keyPress(55);
						r.keyRelease(16);
						r.keyRelease(55);
					 
					}
					else if(funcKey && tempKey == 88){
						funcKey = false;
						r.keyPress(16);
						r.keyPress(49);
						r.keyRelease(16);
						r.keyRelease(49);
					 
					}
					
				}
				
				else{
				if(tempKey > -1 && tempKey < 26){
				r.keyPress(alphabetArray[tempKey]);
				
				r.keyRelease(alphabetArray[tempKey]);
				}
				else if(tempKey == 26){
					r.keyPress(KeyEvent.VK_COMMA);
				}
				else if(tempKey == 38){
					r.keyPress(KeyEvent.VK_BACK_SPACE);
					r.keyRelease(KeyEvent.VK_BACK_SPACE);
				}
				else if(tempKey == 37){
					r.keyPress(KeyEvent.VK_ENTER);
					r.keyRelease(KeyEvent.VK_ENTER);
				}
				else if(tempKey == 33){
					r.keyPress(KeyEvent.VK_SPACE);
					r.keyRelease(KeyEvent.VK_SPACE);
				}
				else if(tempKey == 27){
					r.keyPress(KeyEvent.VK_PERIOD);
					r.keyRelease(KeyEvent.VK_PERIOD);
				}
				else if(tempKey == 27){
					r.keyPress(KeyEvent.VK_PERIOD);
					r.keyRelease(KeyEvent.VK_PERIOD);
					
				}
				else if(tempKey == -9){
					r.keyPress(KeyEvent.VK_TAB);
					r.keyRelease(KeyEvent.VK_TAB);
				}
				else if(tempKey == 48){
					r.keyPress(17);
					r.keyPress(18);		
					r.keyPress(50);
					r.keyRelease(17);
					r.keyRelease(18);
					r.keyRelease(50);
					
					
				}
				}
				break;
			case "scrollup":
				r.mouseWheel(-2);
				break;
			case "scrolldown":
				r.mouseWheel(2);
				break;
			
	}
		
	}
	@Override
	public void run() {
		System.out.println("called");
		init();
	}
	

}
