import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.*;
/**
 * Reproduit la meme tache que {EviterBehavior}, mais par extension de
 * {EmptyBehavior}. Evite d'avoir a se preoccuper de la synchronisation entre
 * les methodes {suppress} et {action}.
 * 
 * @author Charles Pecheur
 * @version 2011
 */
public class EviterVarBehavior extends EmptyBehavior {
	private DifferentialPilot pilote;
	private UltrasonicSensor sonar;
	public EviterVarBehavior(DifferentialPilot pilote, UltrasonicSensor sonar) {
		super();
		this.pilote = pilote;
		this.sonar = sonar;
	}
	public void start() {
		pilote.arc(20.0, -90.0);
	}
	public void stop() {
		pilote.stop();
	}
	public boolean done() {
		return !pilote.isMoving();
	}
	public boolean takeControl() {
		return sonar.getDistance() < 25;
	}
}