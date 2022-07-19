import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
/**
 * Gare HERo après avoir accompli sa mission
 *
 * @Groupe 11.81
 * @11-28-15
 */
public class Parking implements Behavior{
    private boolean suppressed=false;
    private DifferentialPilot pilote;
    private LightSensor ir;
    private UltrasonicSensor us;
    private boolean termine=false;
    /**
     * Constructeur d'objets de classe Parking
     */
    public Parking(DifferentialPilot pilote, LightSensor ir, UltrasonicSensor us){
        super();
        this.pilote=pilote;
        this.ir=ir;
        this.us=us;
    }
    /**
     * Un exemple de méthode - remplacez ce commentaire par le vôtre
     */
    public void action(){
        pilote.setRotateSpeed(100);
        pilote.setTravelSpeed(15);
        pilote.forward();
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){
            pilote.travel(-2);
            pilote.rotate(90);
            pilote.travel(4);
            pilote.rotate(-90);
        }
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getNoir())<10){
            pilote.travel(4);
            pilote.rotate(90);
            pilote.forward();
            if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10){
                pilote.travel(-20);
                pilote.stop();
                System.out.println("C'est gagne, c'est gagné, HOURRAAA ! Yes we did it ! C'est gagne...");
                try{
                    Thread.sleep(5000);
                }
                catch(InterruptedException e){
                    System.out.println("... Que fais-tu ?...");
                }
                termine=true;
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