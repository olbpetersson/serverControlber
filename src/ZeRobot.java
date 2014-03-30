import java.awt.AWTException;
import java.awt.Robot;


public class ZeRobot extends Robot {
	private static ZeRobot r = null;
	
	
	protected ZeRobot() throws AWTException{

	}
	
	public static Robot getRobot() throws AWTException{
		
		if(r == null){
			ZeRobot z = new ZeRobot();
			
			return z;
		}
		else 
			return r;
		
	}
	
}
