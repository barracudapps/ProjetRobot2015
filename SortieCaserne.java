import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
/**
 * Permet de sortir de la caserne après validation de la mission
 *
 * @Groupe 11.81
 * @11-26-15
 */
public class SortieCaserne implements Behavior{
    private LightSensor ir;
    private DifferentialPilot pilote;
    private UltrasonicSensor sonar;
    private boolean suppressed=false;
    private boolean termine=false;
    /**
     * Constructeur d'objets de classe SortieCaserne
     */
    public SortieCaserne(DifferentialPilot pilote,LightSensor ir,UltrasonicSensor sonar){// Fait appel au différentiel et aux sennseurs infrarouge et ultrason
        super();
        this.ir=ir;
        this.pilote=pilote;
        this.sonar=sonar;
        pilote.setTravelSpeed(20);
        pilote.setRotateSpeed(100);
    }
    public void action(){
        pilote.forward();
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){//Active l'action lorsque le robot passe sur une zone grise
            pilote.travel(-4);
            pilote.rotate(90);
            pilote.travel(9);
            pilote.rotate(-90);
        }
        System.out.println("Je trouverai ce feu !!");
        pilote.travel(20);
        pilote.rotate(-90);
        termine=true;
    }
    public void suppress(){
        suppressed=true;
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