package Simulator;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Pause extends Thread{
    Portefeuille portefeuilleAction;
    public volatile boolean flag = false;



    public void setPortefeuilleAction(Portefeuille portefeuilleAction) {
        this.portefeuilleAction = portefeuilleAction;
    }

    @Override
    public void run() {
        while(this.isAlive()) {
            if(this.isInterrupted())
                this.interrupt();
            Scanner clavier = new Scanner(System.in);
            clavier.nextLine();
            flag = true;
            System.out.println(this.portefeuilleAction.getEvolutionPortfeuille());
            clavier.nextLine();
            flag = false;
        }
    }
}
