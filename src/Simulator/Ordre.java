package Simulator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ordre {
    private String noAction;
    private float prix;
    private int quantité;
    private LocalDateTime date;

    public Ordre(String noAction, float prix, int quantité, LocalDateTime date) {
        this.noAction = noAction;
        this.prix = prix;
        this.quantité = quantité;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ordre{" +
                " noAction = '" + noAction + '\'' +
                ", prix = " + prix +
                ", quantité = " + quantité +
                ", date = " + date.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")) +
                '}';
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNoAction() {
        return noAction;
    }

    public void setNoAction(String noAction) {
        this.noAction = noAction;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }
}
