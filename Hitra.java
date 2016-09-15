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
   
    Brick brick = Brickfinder.getDefault();
	Motor.A.setSpeed(200);      // setter hastighet til 500
	Motor.B.setSpeed(200);      // setter hastighet til 500
	Motor.C.setSpeed(300);       // vaskemotoren
	Port s1 = brick.getPort("S1");  // trykksensor 1  Langs bakken
	Port s2 = brick.getPort("S2");   // trykksensor 2 Oppe på bilen
	Port s4 = brick.getPort("S4");   // ultrasoniske sensoren   
	Port s3 = brick.getPort("S3"); // definerer fargesensoren
	
	EV3 ev3 = (EV3) Brickfinder.getLocal();
	TextLCD lcd = ev3.getTextLCD();
	Keys keys = ev3.getKeys();
	
	lcd.drawString("Trykk for å starte", 0, 1);
	keys.waitForAnyPress();
	
  // definerer fargesensor
  
   EV3ColorSensor fargesensor = new EV3ColorSensor(s3); // ev3-fargesensor
   SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..	
   float[] fargeSample = new float [fargeLeser.sampleSize()];  // tabell som inneholder

 // definerer trykksensor 1
 
   SampleProvider trykksensor1 = new EV3TouchSensor(s1);
   float[] trykksample1 = new float[trykksensor1.sampleSize()];       // tabell som inneholder avlest verdi, 
   
 // definerer trykksensor 2
   
   SampleProvider trykksensor2 = new EV3TouchSensor(s2);
   float[] trykksample2 = new float[trykksensor2.sampleSize()];
   
   // definerer ultrasensoren
   EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(s4);
   SampleProvider ultraLeser = ultraSensor.getDistanceMode();
   float[] ultraSample = new float[ultraLeser.sampleSize()];    // tabell som inneholder avlest verdi
   
   // definerer lydsensor
   
   
   // Beregn verdi for svart
	int svart = 0;
	for (int i = 0; i<100; i++){
		fargeLeser.fetchSample(fargeSample, 0);
		svart += fargeSample[0]* 100;
	}
	svart = svart / 100 + 5;
	System.out.println("Farge: " + svart);
   
  
 }     // main metode
}     // class
