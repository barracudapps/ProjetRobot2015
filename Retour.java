import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;
/**
 * Indique le chemin que doit parcourir le robot pour rentrer dans la caserne une fois le feu éteint
 *
 * @Groupe 11.81
 * @11-28-15
 */
public class Retour implements Behavior{
    private boolean suppressed=false;
    private DifferentialPilot pilote;
    private LightSensor ir;
    private UltrasonicSensor us;
    private boolean termine=false;
    /**
     * Constructeur d'objets de classe Retour
     */
    public Retour(DifferentialPilot pilote, LightSensor ir, UltrasonicSensor us){
        super();
        this.pilote=pilote;
        this.ir=ir;
        this.us=us;
    }
    /**
     * Détermine les actions à exécuter durant le trajet
     */
    public void action(){
        pilote.setRotateSpeed(90);
        pilote.setTravelSpeed(20);
        pilote.rotate(180);
        while(Math.abs(ir.readNormalizedValue()-Calibrage.getBlanc())<10&&us.getDistance()>4){
            pilote.forward();
        }
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){
            pilote.travel(-4);
            pilote.rotate(-90);
            if(us.getDistance()>4){
                pilote.travel(6);
                pilote.rotate(90);
            }
            else{
                pilote.rotate(180);
                pilote.travel(6);
                pilote.rotate(-90);
            }
        }
        if(us.getDistance()<4){
            pilote.rotate(90);
            if(us.getDistance()<10){
                pilote.rotate(90);
                pilote.forward();
                if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){
                    pilote.travel(-3);
                    pilote.rotate(90);
                    pilote.travel(4);
                    pilote.rotate(-90);
                }
                if(Math.abs(ir.readNormalizedValue()-Calibrage.getNoir())<10){
                    termine=true;
                }
            }
            else{
                pilote.rotate(-180);
                if(us.getDistance()<10){
                    pilote.rotate(180);
                    pilote.forward();
                    if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){
                        pilote.travel(-3);
                        pilote.rotate(90);
                        pilote.travel(4);
                        pilote.rotate(-90);
                    }
                    if(Math.abs(ir.readNormalizedValue()-Calibrage.getNoir())<10){
                        termine=true;
                    }
                }
                else{
                    pilote.travel(-4);
                    pilote.rotate(-90);
                    pilote.travel(8);
                    pilote.rotate(90);
                }
            }
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