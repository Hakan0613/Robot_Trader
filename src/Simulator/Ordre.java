package Simulator;

import java.time.LocalDateTime;

public class Ordre {
    private String noAction;
    private float prix;
    private float quantité;
    private LocalDateTime date;

    public Ordre(String noAction, float prix, float quantité, LocalDateTime date) {
        this.noAction = noAction;
        this.prix = prix;
        this.quantité = quantité;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ordre{" +
                "noAction='" + noAction + '\'' +
                ", prix=" + prix +
                ", quantité=" + quantité +
                ", date=" + date +
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

    public float getQuantité() {
        return quantité;
    }

    public void setQuantité(float quantité) {
        this.quantité = quantité;
    }
}
