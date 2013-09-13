package Project2;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class milestoneThree {
	
	private int xLocation=0;
	private int yLocation=0;

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
	

	private static void getCoordinates(Tracker myrobot) {
		//Validation - make sure it's in the boundaries.
		//Just do it.
		//1 is orange
		//2 is left
		//4 is right
		//8 is back   
		System.out.println("Enter coordinates");
		System.out.println("Use arrows to navigate, enter and exit to increment");
		
		Button.waitForAnyPress();
		
	}

	private static void moveToCoordinates(Tracker myrobot, int x, int y) {
		// 
		
	}


}
