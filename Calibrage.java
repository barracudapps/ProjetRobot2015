import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.DifferentialPilot.*;
/**
 * Calibre le senseur adéquat
 *
 * @Groupe 11.81
 * @11-22-15
 */
public class Calibrage implements Behavior{
    public static int noir;
    public static int gris;
    public static int blanc;
    private LightSensor ir;
    private boolean termine=false;
    private boolean suppressed=false;
    /**
     * Calibre le senseur infrarouge
     */
    public Calibrage(LightSensor ir){
        super();
        this.ir=ir;
    }
    /**
     * Calibrage du noir.
     */
    public void action(){
        System.out.println("Bonjour, je m'appelle HERo. Je fus cree pour vous venir en aide.");
        System.out.println("Veuillez m'aider a calibrer mon capteur infrarouge svp.");
        System.out.println();
        //Calibrage du noir.
        System.out.println("Placez le capteur sur du noir.");
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            System.out.println("Problème au calibrage du noir.");
        }
        noir=ir.readNormalizedValue();
        System.out.println("Calibrage noir: OK.");
        //Calibrage du gris.
        System.out.println("Placez le capteur sur du gris.");
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            System.out.println("Problème au calibrage du gris.");
        }
        gris=ir.readNormalizedValue();
        System.out.println("Calibrage gris: OK.");
        //Calibrage du blanc.
        System.out.println("Placez le capteur sur du blanc.");
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
            System.out.println("Problème au calibrage du blanc.");
        }
        blanc=ir.readNormalizedValue();
        System.out.println("Calibrage blanc: OK.");
        //Calibrage termine.
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){
        }
        termine=true;
    }
    /**
     * Provoque l'arret de l'action.
     */
    public void suppress(){
        suppressed=true;
    }
    /**
     * Tache activable.
     */
    public boolean takeControl(){
        if(termine==false){
            return true;
        }
        else{
            return false;
        }
    }
    public static int getBlanc(){
        return blanc;
    }
    public static int getGris(){
        return gris;
    }
    public static int getNoir(){
        return noir;
    }
}