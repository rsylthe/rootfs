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
import lejos.hardware.sensor.EV3Colorsensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;

public class Hitra {
  public static void main(String[] args) {

    //Definerer Brick.sensorer

    //Definerer trykksensor

    //Definerer lydsensor

    //Definerer lyssensor

    //Setter hastighet motorer
    Motor.A.setSpeed(300);
    Motor.B.setSpeed(300);

    boolean continue = true;

    while (continue) {

    }

  }
}
