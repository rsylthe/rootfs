/*
*
* Rally.java - v0.0.1 - 2016-09-30
*
*/

import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
//import lejos.hardware.sensor.EV3TouchSensor;
//import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3ColorSensor;
//import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.*;
import lejos.hardware.ev3.EV3;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;

public class Rally {
	public static void main (String[] args) throws Exception {
		boolean fortsett = true;
    boolean bool = true;
		int svingVenstre = 0;
		int svingHoyre = 0;
		int retning = 0;
		int rot = 25;	//verdi for rotering
		int motorSpeed = 300;	//hastighet motorer
		int turnSpeed = 450;	//hastighet svining
		final int RUNDE = 5;

		Brick brick = BrickFinder.getDefault();
		Motor.A.setSpeed(motorSpeed);						 // setter hastighet venstre motor
		Motor.B.setSpeed(motorSpeed);						 // setter hastighet h√∏yre motor
		Port s1 = brick.getPort("S1");		 // Lyssensor
		Port s2 = brick.getPort("S2");		 // Trykksensor 2 Oppe p√• bilen

		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		Keys keys = ev3.getKeys();

		EV3ColorSensor fargesensor = new EV3ColorSensor(s1);	// Definerer fargesensor
		SampleProvider fargeLeser = fargesensor.getMode("RGB");	// svart = 0.01..
		float[] fargeSample = new float [fargeLeser.sampleSize()];	// tabell som inneholder

		/*SampleProvider trykksensor = new EV3TouchSensor(s2);	// Definerer trykksensor
		float[] trykkSample = new float[trykksensor.sampleSize()];*/

		lcd.drawString("Trykk for Â starte", 0, 1);
		keys.waitForAnyPress();

		// Beregn verdi for svart
		double svart = 0.05;
		/*for (int i = 0; i<100; i++){
			fargeLeser.fetchSample(fargeSample, 0);
			svart += fargeSample[0]* 100;
		}
		svart = svart / 100;
		System.out.println("Farge f¯r loop: " + svart);*/

    while (fortsett) {
      fargeLeser.fetchSample(fargeSample, 0);
      while (fargeSample[0] < svart) {
				bool = true;
        fargeLeser.fetchSample(fargeSample, 0);
				Motor.A.setSpeed(motorSpeed);
				Motor.B.setSpeed(motorSpeed);
        Motor.A.forward();
  			Motor.B.forward();
  			//System.out.println("Farge: " + fargeSample[0]);
      } //while < svart END
			for (int i = 0; i <= RUNDE; i++) {
				Motor.A.rotate(rot);
				Motor.B.stop();
				fargeLeser.fetchSample(fargeSample, 0);
				//System.out.println("F¯rste FOR-loop, roterer: " + i);
				if (fargeSample[0] < svart) {
					bool = false;
					break;
				}
				if (i == RUNDE) {
					for (int j = 0; j <= RUNDE; j++) {
						Motor.A.rotate(-rot);
						Motor.B.stop();
						fargeLeser.fetchSample(fargeSample, 0);
						//System.out.println("Ikke truffet svart f¯rste loop " + i);
					}
				}
				fargeLeser.fetchSample(fargeSample, 0);
				if (fargeSample[0] < svart) {
					bool = false;
				}
			} //first FOR-loop end
			if (bool) {
				for (int i = 0; i <= RUNDE; i++) {
					Motor.A.stop();
					Motor.B.rotate(rot);
					fargeLeser.fetchSample(fargeSample, 0);
					//System.out.println("Andre FOR-loop, roterer: " + i);
					if (fargeSample[0] < svart) {
						bool = false;
						break;
					}
					if (i == RUNDE) {
						for (int j = 0; j <= RUNDE; j++) {
							Motor.A.stop();
							Motor.B.rotate(-rot);
							fargeLeser.fetchSample(fargeSample, 0);
							//System.out.println("Ikke truffet svart andre loop " + i);
						}
					}
				}
			}

  			/*if (fargeSample[0] > svart){
  				LCD.clear();
  				System.out.println("Fargeakitvert: " + fargeSample[0]);
  				Motor.A.stop();
  				Motor.B.stop();
  				Motor.B.rotate(670);
  				while(Motor.B.isMoving()) Thread.yield();
  			}*/
      /*trykksensor.fetchSample(trykkSample, 0);
      if (trykkSample[0] > 0) {
        LCD.clear();
        System.out.println("Avslutter");
        fortsett = false;
      }*/
    }		//outer while-loop
	}		 // main metode
}		 // class
