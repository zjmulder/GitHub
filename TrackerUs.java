package Project2;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.*;

public class TrackerUs {
	
	public DifferentialPilot pilot;
	static float wheelDiameter;
	static float trackWidth;
	static SensorPort sensor;
	private LightSensor LSensor,RSensor;

	public TrackerUs(DifferentialPilot robot, LightSensor LSensor, LightSensor RSensor) {
		pilot = robot;
		pilot.setTravelSpeed(20);
		pilot.setRotateSpeed(180);
		pilot.setAcceleration(80);
		this.LSensor = LSensor;
		this.LSensor.setFloodlight(true);
		this.RSensor = RSensor;
		this.RSensor.setFloodlight(true);
		
	}

	public void calibrate() {
		int i = 0;
		while (i<2){
		System.out.println("Left Sensor Value: " + LSensor.readNormalizedValue());
		System.out.println("Right Sensor Value: " +RSensor.readNormalizedValue());
		Button.waitForAnyPress();
		System.out.println("*****************************");
		}
	}

	public void line() {
		pilot.forward();
		
	}

}
