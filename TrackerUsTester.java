package Project2;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;


public class TrackerUsTester {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		Float wheelDiameter = 5f;
		Float trackWidth = 12f;
		DifferentialPilot robot = new DifferentialPilot(wheelDiameter, trackWidth, Motor.A, Motor.C);
		LightSensor LSensor = new LightSensor(SensorPort.S1);
		LightSensor RSensor = new LightSensor(SensorPort.S2);
		TrackerUs newTrack = new TrackerUs(robot,LSensor,RSensor);
		newTrack.calibrate();
		newTrack.line();
	}

}
