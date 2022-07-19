import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;
/**
 * Decrit les action a effectuer au demarrage
 *
 * @Groupe 11.81
 * @11-24-15
 */
public class Demarrage implements Behavior{
    private boolean suppressed=false;
    private DifferentialPilot pilote;
    private LightSensor ir;
    private TouchSensor ts;
    private boolean termine=false;
    /**
     * Constructeur d'objets de classe Demarrage
     */
    public Demarrage(DifferentialPilot pilote, LightSensor ir, TouchSensor TS){//Fait appel aux senseurs tactiles, infrarouge et au différentiel.
        super();
        this.pilote=pilote;
        this.ir=ir;
        this.ts=TS;
    }
    /**
     * Avance vers le mur puis recule, tourne et avance vers le bouton d'activation de la méthode
     */
    public void action(){
        suppressed=false;
        pilote.setTravelSpeed(15);
        pilote.setRotateSpeed(100);
        pilote.forward();
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){//S'exécute lorsque le robot détecte du gris avec une marge d'erreur de 15%
            pilote.travel(-15);
            pilote.rotate(-90);
        }
        if(ts.isPressed()){//Exécute l'action lorsque le senseur tactile est compressé.
            System.out.println("Activation de la méthode reussie... C'est parti !!!");
            pilote.travel(-15);
            pilote.rotate(-200);
            termine=true;
        }
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