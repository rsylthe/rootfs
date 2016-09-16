/**
*
* Hitra.java - v0.0.1 - 2016-09-15
*
* Program for en LEGO-robot som kan vaske hitratunnelen.
* Roboten vil kjøre sakte fram i tunnelen helt til enten
* en skarp lyd (bilhorn) eller en svart linje blir registert.
* Om roboten registrerer en skarp lyd vil den stoppe i X antall sekunder.
* Om roboten registerer en svart linje vil den snu 180 grader og fortsette
* den andre veien i tunnelen.
* Programmet vil kjøre i en loop helt til avbrutt av bruker.
*
*/

import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.*;
import lejos.hardware.ev3.EV3;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;


public class Hitra {
	public static void main (String[] args) throws Exception {
		Brick brick = BrickFinder.getDefault();
		Motor.A.setSpeed(150);						 // setter hastighet venstre motor
		Motor.B.setSpeed(150);						 // setter hastighet høyre motor
		Motor.C.setSpeed(300);						 // setter hastighet Vaskemotoren
		Port s1 = brick.getPort("S1");		 // lydsensor
		Port s2 = brick.getPort("S2");		 // Trykksensor 2 Oppe på bilen
		Port s3 = brick.getPort("S3");		 // Fargesensoren
		//Port s4 = brick.getPort("S4");	 // ultrasoniske sensoren

		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		Keys keys = ev3.getKeys();

		lcd.drawString("Trykk for å starte", 0, 1);
		keys.waitForAnyPress();

		NXTSoundSensor lydsensor = new NXTSoundSensor(s1);		// Definerer lydsensor
		SampleProvider lydInput = lydsensor.getDBAMode();
		float[] lydSample = new float[lydInput.sampleSize()]; // tabell som inneholder avlest verdi

		SampleProvider trykksensor = new EV3TouchSensor(s2);	// Definerer trykksensor
		float[] trykkSample = new float[trykksensor.sampleSize()];

		EV3ColorSensor fargesensor = new EV3ColorSensor(s3);	// Definerer fargesensor
		SampleProvider fargeLeser = fargesensor.getMode("RGB");	// svart = 0.01..
		float[] fargeSample = new float [fargeLeser.sampleSize()];	// tabell som inneholder

		// Beregn verdi for svart
		double svart = 0.05;
		/*for (int i = 0; i<100; i++){
			fargeLeser.fetchSample(fargeSample, 0);
			svart += fargeSample[0]* 100;
		}
		svart = svart / 100;
		System.out.println("Farge før loop: " + svart);*/

		boolean fortsett = true;
		int retning = 0;

		while (fortsett) {
			Motor.A.forward();
			Motor.B.forward();
			fargeLeser.fetchSample(fargeSample, 0);
			lydsensor.fetchSample(lydSample, 0);
			System.out.println("Farge: " + fargeSample[0]);
			if (fargeSample[0] < svart){
				LCD.clear();
				System.out.println("Fargeakitvert: " + fargeSample[0]);
				Motor.A.stop();
				Motor.B.stop();
				Motor.B.rotate(670);
				while(Motor.B.isMoving()) Thread.yield();
			}	else if (lydSample[0] > 1.0) {
				LCD.clear();
				System.out.println("Lyd oppdaget!");
				Motor.A.stop();
				Motor.B.stop();
				Thread.sleep(3000);
			}

			trykksensor.fetchSample(trykkSample, 0);
			if (trykkSample[0] > 0) {
				LCD.clear();
				System.out.println("Avslutter");
				fortsett = false;
			}
		}
	}		 // main metode
}		 // class
