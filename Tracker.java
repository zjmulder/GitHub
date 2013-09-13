package Project2;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import lejos.nxt.*;


/**
 * This class needs a higher level controller to implement the navigtion logic<br>
 * Responsibility: keep robot on the line till it senses a marker, then stop <br>
 * also controls turning to a new line at +- 90 or 180 deg<br>
 * Hardware: Two light sensors , shielded, 2 LU above floor. Classes used:
 * Pilot, LightSensors<br>
 * Control Algorithm: proportional control. estimate distance from centerline<br>
 * Calibrates both sensors to line, background Updated 9/10/2007 NXT hardware
 * 
 * @author Roger Glassey
 */
public class Tracker
{

	/**
	 * controls the motors
	 */
	public DifferentialPilot pilot;
	/**
	 * set by constructor , used by trackline()
	 */
	private LightSensor leftEye;
	/**
	 * set by constructor , used by trackline()
	 */
	private LightSensor rightEye;
	
	/**
	 * Counts the number of blackdots, used to determine number of laps completed
	 */
	
	/**
	 * Set the acceleration speed. Makes testing the acceleration speed easier since
	 * it is decreased before turns and increased after turns
	 */
	private int accelerationSpeed=80;

	/**
	 * constructor - specifies which sensor ports are left and right
	 */
	
	public Tracker(DifferentialPilot thePilot, LightSensor leftEye,
			LightSensor rightEye)
	{
		pilot = thePilot;
		pilot.setTravelSpeed(18);
		pilot.setRotateSpeed(180);
		pilot.setAcceleration(accelerationSpeed);
		this.leftEye = leftEye;
		this.leftEye.setFloodlight(true);
		this.rightEye = rightEye;
		this.rightEye.setFloodlight(true);
	}

	/**
	 * follow line till intersection is detected uses proportional control <br>
	 * Error signal is supplied by CLdistance()<br>
	 * uses CLdistance(), pilot.steer() loop execution about 65 times per second
	 * in 1 sec.<br>
	 */
	public void trackLine(){
		pilot.forward();
		int lval = leftEye.getNormalizedLightValue(), rval = rightEye.getNormalizedLightValue(), lvalprevious, rvalprevious;
		double error = 0.0;
		boolean foundblackdot=false;
		while (!foundblackdot) {
			lvalprevious = lval;
			rvalprevious = rval;
			lval = leftEye.getNormalizedLightValue();
			rval = rightEye.getNormalizedLightValue();
			if (lval<=410 && rval<=410){
				Sound.beep();
				pilot.travel(20,true);
				Delay.msDelay(330);
				foundblackdot=true;}
			if (Math.abs(lval-lvalprevious)<1 && Math.abs(rval-rvalprevious)<1){
			}
			else {
				error = CLDistance(lval, rval);
				pilot.steer(-57*error);}
		}
	}
	/**
	 * helper method for Tracker; calculates distance from centerline, used as
	 * error by trackLine()
	 * 
	 * @param left
	 *            light reading
	 * @param right
	 *            light reading
	 * @return distance
	 */
	double CLDistance(int left, int right)
	{
		double sensorDifference = right - left;
		double lineDistance=0;
		lineDistance = (-.015*sensorDifference + .22);
		return lineDistance;
	}
	
	/**
	 * Scaled turns to turn an accurate amount
	 * @param angle
	 * 			The desired turn angle (follows + angle is left, - angle is right)
	 */
	
	public void turn(int angle) {
		pilot.setAcceleration(80);
		pilot.rotate(angle*88/90);
		pilot.setAcceleration(accelerationSpeed);
	}
	
	/**
	 * Stops the robot
	 */

	public void stop()
	{
		pilot.stop();
	}

	/**
	 * calibrates for line first, then background, then marker with left sensor.
	 * displays light sensor readings on LCD (percent)<br>
	 * Then displays left sensor (scaled value). Move left sensor over marker,
	 * press Enter to set marker value to sensorRead()/2
	 */
	public void calibrate()
	{
		System.out.println("Calibrate Tracker");
		Delay.msDelay(1000);

		for (byte i = 0; i < 3; i++)
		{
			while (0 == Button.readButtons())// wait for press
			{
				LCD.drawInt(leftEye.getLightValue(), 4, 6, 1 + i);
				LCD.drawInt(rightEye.getLightValue(), 4, 12, 1 + i);
				if (i == 0)
				{
					LCD.drawString("LOW", 0, 1 + i);
				} else if (i == 1)
				{
					LCD.drawString("HIGH", 0, 1 + i);
				}
			}
			Sound.playTone(1000 + 200 * i, 100);
			if (i == 0)
			{
				leftEye.calibrateLow();
				rightEye.calibrateLow();
			} else if (i == 1)
			{
				rightEye.calibrateHigh();
				leftEye.calibrateHigh();
			}
			while (0 < Button.readButtons())
			{
				Thread.yield();// button released
			}

		}
		while (0 == Button.readButtons())// while no press
		{
			int lval = leftEye.getLightValue();
			int rval = rightEye.getLightValue();
			LCD.drawInt(lval, 4, 0, 5);
			LCD.drawInt(rval, 4, 4, 5);
			LCD.drawInt(4, 12, 5);
			LCD.refresh();
		}
		LCD.clear();
	}

}
