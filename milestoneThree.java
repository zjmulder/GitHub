package Project2;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class milestoneThree {

	private static int xLocation=3;
	private static int yLocation=4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DifferentialPilot robot = new DifferentialPilot(5.5f,11.83f, Motor.A, Motor.C);
		LightSensor SensorA = new LightSensor(SensorPort.S1);
		LightSensor SensorB = new LightSensor(SensorPort.S4);
		Tracker myrobot = new Tracker(robot, SensorA, SensorB);
		followInput(myrobot);

	}

	private static void followInput(Tracker myrobot) {
		int x = 0,y = 0;
		getCoordinates(myrobot);
		moveToCoordinates(myrobot,x,y);

	}


	private static int[] getCoordinates(Tracker myrobot) {
		//Validation - make sure it's in the boundaries.
		//Just do it.
		//1 is orange
		//2 is left
		//4 is right
		//8 is back 
		//Bottom right corner is (99,63)
		//Font height is 8, width 5
		//6 x 8 grid
		int xselected = 1;
		boolean save = false;
		int button;
		int xgoto = xLocation;
		int ygoto = yLocation;
		LCD.drawString("Coordinates", 3, 0);
		LCD.drawChar('X', 3, 3);
		LCD.drawChar('Y', 13, 3);
		LCD.drawInt(xLocation,3,4);
		LCD.drawInt(yLocation,13,4);
		LCD.drawString("Save:", 6,6);
		LCD.drawString("Enter + L or R",1,7);
		while (!save){
			if (xselected==1) {
				//Draw x triangle
				for (int j=1;j<=3;j++){
					for (int i=21-j;i<=19+j;i++){
						LCD.setPixel(i,39+j, 1);}
				}
				//Undraw y triangle
				for (int j=1;j<=3;j++){
					for (int i=81-j;i<=79+j;i++){
						LCD.setPixel(i,39+j, 0);}
				}
			}
			else {
				//Draw y triangle
				for (int j=1;j<=3;j++){
					for (int i=81-j;i<=79+j;i++){
						LCD.setPixel(i,39+j, 1);}
				}
				//Undraw x triangle
				for (int j=1;j<=3;j++){
					for (int i=21-j;i<=19+j;i++){
						LCD.setPixel(i,39+j, 0);}
				}
			}
			button = Button.waitForAnyPress();
			if (button==2 && xselected==1){}
			else if(button==2){
				xselected=1;
			}
			else if(button==4&&xselected==0){}
			else if(button==4){
				xselected=0;
			}
			else if(button==1){
				if(xgoto==6&&ygoto==8){}
				else if(xgoto==6){
					ygoto=ygoto + (1-xselected)*1;
				}
				else if(ygoto==8){
					xgoto=xgoto + xselected*1;
				}
				else{
					xgoto=xgoto + xselected*1;
					ygoto=ygoto + (1-xselected)*1;}
			}
			else if(button==8){
				if(xgoto==0&&ygoto==0){}
				else if(xgoto==0){
					ygoto=ygoto - (1-xselected)*1;
				}
				else if(ygoto==0){
					xgoto=xgoto - xselected*1;
				}
				else{
					xgoto=xgoto - xselected*1;
					ygoto=ygoto - (1-xselected)*1;}
			}
			else {
				save=true;
			}
			LCD.drawInt(xgoto,3,4);
			LCD.drawInt(ygoto,13,4);
		}
		LCD.clear();
		System.out.println("Points saved!");
		Button.waitForAnyPress();
		return new int[] {xgoto,ygoto};
	}

	private static void moveToCoordinates(Tracker myrobot, int x, int y) {
		// 

	}


}
