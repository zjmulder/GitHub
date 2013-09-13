package Project2;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class TrackerTest {

	private static int blackdots;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DifferentialPilot robot = new DifferentialPilot(5.5f,11.83f, Motor.A, Motor.C);
		LightSensor SensorA = new LightSensor(SensorPort.S1);
		LightSensor SensorB = new LightSensor(SensorPort.S4);
		Tracker myrobot = new Tracker(robot, SensorA, SensorB);
		myrobot.calibrate();
		followTrack(myrobot);
		followTrackFigEight(myrobot);
		followGrid(myrobot);

	}

	/**
	 * Starts the robot along an oval track with two black dots along the track
	 */
	public static void followTrack(Tracker myrobot) {
		Button.waitForAnyPress();
		blackdots=0;
		while (blackdots<17) {
			myrobot.trackLine();
			blackdots++;
			if(blackdots==8) {
				myrobot.stop();
				blackdots++;
				myrobot.turn(180);}
		}
		myrobot.stop();
	}

	/**
	 * Starts the robot along a figure eight track. MUST START IN THE COUNTERCLOCKWISE DIRECTION
	 */

	public static void followTrackFigEight(Tracker myrobot) {
		Button.waitForAnyPress();
		blackdots=0;
		while (blackdots<17) {
			myrobot.trackLine();
			blackdots++;
			if (blackdots/2%2==0) {
				myrobot.stop();
				myrobot.turn(90);}
			else{
				myrobot.stop();
				myrobot.turn(-90);}
		}
		myrobot.stop();
	}
	/**
	 * Follows the grid specified in Milestone 2 requirements
	 * @param myrobot
	 */

	public static void followGrid(Tracker myrobot) {
		Button.waitForAnyPress();
		int direction = 1;
		//One sqaure going left
		squareTwo(myrobot, direction);
		//Fcae back the correct direction
		myrobot.turn(90);
		//Second left sqaure
		squareTwo(myrobot, direction);
		//Turn 180 to start right square
		myrobot.turn(180);
		//First right square
		squareTwo(myrobot, -direction);
		//Turn back the correct direction
		myrobot.turn(-90);
		//Second right square
		squareTwo(myrobot, -direction);
		//Turn to set up next leg
		myrobot.turn(-90);
		//Go to (0,4) and back to (0,2)
		lap(myrobot,2,direction);
		//Turn around
		myrobot.turn(180);
		//Go to (0,4) and back to (0,2)
		lap(myrobot,2,direction);
		//Continue to (0,0) and back to (0,2)
		lap(myrobot,2,-direction);
		//Turn around
		myrobot.turn(-180);
		//Go to (0,0) and back to (0,2)
		lap(myrobot,2,-direction);
		//Face original direction
		myrobot.turn(-90);


	}

	/**
	 * Traces a 2 x 2 square without an end turn. 
	 * @param myrobot
	 * @param direction
	 */
	private static void squareTwo(Tracker myrobot, int direction) {
		myrobot.trackLine();
		for (int i=0;i<3;i++){
			myrobot.trackLine();
			myrobot.stop();
			myrobot.turn(direction*90);
			myrobot.trackLine();}
		myrobot.trackLine();
		myrobot.stop();

	}
	
	/**
	 * Makes a lap with a length based on the input parameter. 
	 * A positve direction will give left turns, a negative direction will give right turns.
	 * There is no end turn
	 * Must pass a robot to the method.
	 * @param myrobot
	 * @param length
	 * @param direction
	 */

	private static void lap(Tracker myrobot, int length, int direction) {
		myrobot.trackLine();
		for (int count = 1;count<length;count++){
			myrobot.trackLine();
		}
		myrobot.stop();
		myrobot.turn(180*direction);
		myrobot.trackLine();
		for (int count = 1;count<length;count++){
			myrobot.trackLine();
		}
		myrobot.stop();

	}

}
