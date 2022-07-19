import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import lejos.nxt.*;
/**
* Propulse les billes
*
* @Groupe 81.11
* @11-27-15
*/
public class Tir implements Behavior{
    private DifferentialPilot pilote;
    public boolean suppressed=false;
    private LightSensor ir;
    private UltrasonicSensor us;
    private boolean termine=false;
    /**
     * Constructeur
     */
    public Tir(DifferentialPilot pilote, LightSensor ir, UltrasonicSensor us){
       super();
       this.pilote=pilote;
       this.ir=ir;
       this.us=us;
    }
    /**
     * @pre:--
     * @post: Le robot se place et projette la bille
     */
    public void action (){
        suppressed = false;
        pilote.setRotateSpeed(90); // Vitesse de rotation determinee a 90°/sec
        pilote.setTravelSpeed(10);
        pilote.travel(3); //Le robot avance de 3 cm
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getNoir())<10){
            pilote.travel(2);
            pilote.rotate(90);
            if(Math.abs(ir.readNormalizedValue()-Calibrage.getGris())<10&&us.getDistance()<=5){
                pilote.travel(-2);
                Motor.C.setSpeed(500); //Moteur C à 500°/sec
                Motor.C.rotate(5550); // projete les 5 billes.
                System.out.println("Je suis venu, j'ai vu, j'ai vaincu !...");
                try{
                    Thread.sleep(2000);
                }
                catch(InterruptedException e){
                    System.out.println("Bug...");
                }
                termine=true;
            }
        }
        if(Math.abs(ir.readNormalizedValue()-Calibrage.getBlanc())<10){
            pilote.travel(-3);
            pilote.rotate(-90);
            pilote.travel(2);
            if(Math.abs(ir.readNormalizedValue()-Calibrage.getBlanc())<10){
                pilote.rotate(180);
                pilote.travel(5);
                pilote.rotate(-90);
                pilote.travel(-2);
                Motor.C.setSpeed(500); //Moteur C à 500°/sec
                Motor.C.rotate(5550); // projete les 5 billes.
                System.out.println("Je suis venu, j'ai vu, j'ai vaincu !...");
                try{
                    Thread.sleep(2000);
                }
                catch(InterruptedException e){
                    System.out.println("Bug...");
                }
                termine=true;
            }
        }
    }
    /**
     * @pre --
     * @post {suppressed == true}, ce qui provoque l'arret de {action}.
     */
    public void suppress(){
        suppressed=true;
    }
    public boolean takeControl (){
        if(termine==true){
            return false;
        }
        else{
            return true;
        }
    }
}