import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;
/**
 * Décrit le chemin parcouru par le robot de la sorti de la caserne a la ligne de tir,
 * tout en pivotant de droite a gauche pour couvrir toute la largeur du robot pour eviter 
 * au mieux un obstacle et pouvoir mieux trouver la ligne de tir.
 * 
 * @Groupe 11.81
 * @11-27-15
 */
public class Chemin implements Behavior{
    private boolean suppressed=false;
    private DifferentialPilot pilote;
    private LightSensor ir;
    private UltrasonicSensor us;
    private boolean termine=false;
    public Chemin(DifferentialPilot pilote, LightSensor ir, UltrasonicSensor sonar){//Fait appel aux senseurs infrarouge et ultrason ainsi qu'au différentiel
        super();
        this.pilote=pilote;
        this.ir=ir;
        this.us=sonar;
    }
    /**
     * fait avancer le robot automatiquement et de facon autonome, pivote à droite et à 
     * gauche pour que le capteur infrarouge puisse détecter une différence de couleur
     * (blanc, gris, noir) qui l'empechera de foncer sur un obstacle.
     */
    public void action(){
        pilote.setRotateSpeed(90);//rotation de 90° par seconde
        pilote.setTravelSpeed(20);//vitesse du robot de 20 cm par seconde
        suppressed=false;
        if(us.getDistance()<5||Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){//Uniquement lorsque le robot s'approche à 2cm d'un obstacle ou quand le capteur passe sur du gris
            pilote.travel(-4);
            pilote.rotate(-90);
            pilote.travel(8);
            pilote.rotate(90);
        }
        while(Math.abs(ir.readNormalizedValue()-Calibrage.getBlanc())<10){//Tant que le robot est sur une sone blanche
            pilote.forward();
        }
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getNoir())<10){//Arrête le pilote quand le senseur infrarouge détecte du noir
            System.out.println("Nous y voilà enfin...");
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                System.out.println("Probleme...");
            }
            termine=true;
        }
    }
    /**
     * provoque suppressed (arret de "action")
     */
    public  void suppress(){
        suppressed = true;
    }
    public boolean takeControl(){
        if(termine==false){
            return true;
        }
        else{
            return false;
        }
    }
}