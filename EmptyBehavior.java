import lejos.robotics.subsumption.Behavior;
/**
 * Une tache qui ne fait rien et ne se termine pas. Permet de definir des taches
 * persistentes par extension, en re-definissant les methodes {takeControl},
 * {start}, {stop} et {done}.
 * 
 * @author Charles Pecheur
 * @version 2011
 */
public class EmptyBehavior implements Behavior {
	private boolean suppressed = false;
	/**
	 * @pre --
	 * @post l'action est demarree
	 */
	public void start() {
		// rien a faire
	}
	/**
	 * @pre l'action a ete demarree
	 * @post l'action est interrompue
	 */
	public void stop() {
		// rien a faire
	}
	/**
	 * @pre --
	 * @post retourne {true} ssi l'action est terminee
	 */
	public boolean done() {
		return false; // ne se termine pas
	}
	/**
	 * @pre --
	 * @post le robot a avance jusqu'a ce que {suppress} ait ete appele, et
	 *       s'est ensuite arrte.
	 */
	public void action() {
		suppressed = false;
		start();
		while (!suppressed && !done()) {
			Thread.yield();
		}
		stop();
	}
	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
		suppressed = true;
	}
	/**
	 * @pre --
	 * @post retourne {true}: la tache est toujours activable.
	 */
	public boolean takeControl() {
		return true;
	}
}