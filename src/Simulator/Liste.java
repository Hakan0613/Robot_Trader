package Simulator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Liste<T> {
    private ArrayList<T> liste;
    public ArrayList<T> getListe() {
        return liste;
    }
    public Liste(){
        liste= new ArrayList<T>();
    }
    public Liste(Liste liste){
        this.liste = new ArrayList<T>(liste.getAll());
    }

    public void addAll(ArrayList<T> adds){
        this.liste.addAll(adds);
    }

    public void add(T adds){
        this.liste.add(adds);
    }
    public ArrayList<T> getAll(){
        return this.liste;
    }

    public boolean delete(Object para1){
        boolean removed = false;
        if (para1 instanceof String) {
            int i = 0;
            while (i < this.liste.size() && removed == false) {
                if (this.liste.get(i) instanceof Ordre)
                    if (((Ordre) this.liste.get(i)).getNoAction().equalsIgnoreCase((String) para1)) {
                        this.liste.remove(i);
                        removed = true;
                    }
                i++;
            }
        }

        return removed;
    }

    public T get(Object para1){
        if(para1 instanceof Integer) {
            return this.liste.get((int) para1);
        }
        else if (para1 instanceof String) {
            for (int i = 0; i < liste.size(); i++) {
                if (liste.get(i) instanceof Ordre)
                    if (((Ordre) liste.get(i)).getNoAction().equalsIgnoreCase((String) para1))
                        return liste.get(i);
                if (liste.get(i) instanceof Cotation)
                    if (((Cotation) liste.get(i)).getNoAction().equalsIgnoreCase((String) para1))
                        return liste.get(i);
            }
        }
        return null;
    }

    public Liste<T> getSubList(Object para1){
        Liste<T> subListe = new Liste<T>();
        if (para1 instanceof LocalDateTime)
            for (Iterator<T> iterator = this.liste.iterator(); iterator.hasNext();) {
                if(this.liste.get(0) instanceof Cotation) {
                    Cotation cotation = (Cotation) iterator.next();
                    if (cotation.getHeure().compareTo(((LocalDateTime) para1).toLocalTime()) == 0 && cotation.getDate().isEqual(((LocalDateTime) para1).toLocalDate()))
                        subListe.add((T) cotation);
                }
            }
        return subListe;
    }

    public String getAffichage(){
        String texte = "";
        if(this.liste.get(0) instanceof Ordre){
            texte += "\t\tAction détenue :\n\t\t***";
            for (int i = 0; i < this.liste.size(); i++) {
                texte += "\n\t\t\t" + ((Ordre)this.liste.get(i)).toString();
            }
            texte += "\n\t\t***";
        }
        return texte;
    }

    public int lenght(){
        return this.liste.size();
    }

}
