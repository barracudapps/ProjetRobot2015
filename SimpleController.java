import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;
/**
 * Un controleur de robot simple. Utilise un robot de type "chaise roulante"
 * avec le detecteur d'ultrasons. Le robot avance tout droit mais fait une
 * manoeuvre arriere si le detecteur percoit un obstacle. Le robot s'arrete
 * immediatement si on appuye sur le bouton Escape.
 * 
 * @author Charles Pecheur, Viet Trong Ho
 * @version 2011
 */
public class SimpleController {
    // Toutes les distances sont en cm.   
    private static final float DIAM_ROUE = 5.6f;  // Diametre des roues
    private static final float DIST_ROUE = 11f;   // Empattement entre les roues
    /**
     * Cree et demarre la liste de taches
     */
    public static void main(String[] args) {
        DifferentialPilot pilote = new DifferentialPilot(DIAM_ROUE, DIST_ROUE, Motor.A, Motor.B);// Le pilote pour les roues
        UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S1);// Le senseur de distance d'ultrasons
        LightSensor ir = new LightSensor(SensorPort.S2);// Active la classe du senseur infrarouge
        TouchSensor activation = new TouchSensor(SensorPort.S3);// Senseur de contact
        
        Behavior parking = new Parking(pilote, ir, sonar);
        Behavior retour = new Retour(pilote, ir, sonar);
        Behavior chemin = new Chemin(pilote, ir, sonar);// Programme de navigation vers le positionnement du tir
        Behavior sortieCaserne = new SortieCaserne(pilote, ir, sonar);// Programme la sortie de la caserne
        Behavior tir = new Tir(pilote, ir, sonar);// Détermine l'action à exécuter pour tirer les billes
        Behavior demarrage = new Demarrage(pilote, ir, activation);// Détermine l'action à exécuter au démarrage
        Behavior calibrage = new Calibrage(ir);// Calibre le senseur infrarouge
        Behavior[] taches = {parking,retour,tir,chemin,sortieCaserne,demarrage,calibrage};
        // Initialiser et activer le controle par les trois taches
        Arbitrator arbitre = new Arbitrator(taches);
        arbitre.start();
    }
}