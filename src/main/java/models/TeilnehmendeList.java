package models;

import java.util.ArrayList;
import java.util.List;

public class TeilnehmendeList {
    List<Teilnehmer> teilnehmerList  = new ArrayList<Teilnehmer>();
    public int anzahlTeilnehmer;
    private int maxTeilnehmer;
    private NachrueckendeList nachrueckendeList;
    


        public void addTeilnehmer(Teilnehmer teilnehmer){
            if(anzahlTeilnehmer < maxTeilnehmer){
                teilnehmerList.add(teilnehmer);
                anzahlTeilnehmer++;
            } else {
                nachrueckendeList.addteilnehmer(teilnehmer);
            }
        }

        public void removeTeilnehmer(Teilnehmer teilnehmer){
            teilnehmerList.remove(teilnehmer);
            anzahlTeilnehmer--;
            if(anzahlTeilnehmer < maxTeilnehmer){
                if (!nachrueckendeList.getNachrueckendeTeilnehmer().isEmpty()) {
                    Teilnehmer nachrueckenderTeilnehmer = nachrueckendeList.getNachrueckendeTeilnehmer().remove(0);
                    teilnehmerList.add(nachrueckenderTeilnehmer);
                    anzahlTeilnehmer++;
                }
            }
    }


        
    public void incrementAnzahlteilnehmer(int incrementValue) {
        anzahlTeilnehmer += incrementValue;
    }

    public void decrementAnzahlteilnehmer(int decrementValue) {
        anzahlTeilnehmer -= decrementValue;
    }

    //getters and setters
    
    public  int getAnzahlTeilnehmer() {
        return anzahlTeilnehmer;
    }
    public void setAnzahlTeilnehmer(int anzahlTeilnehmer) {
        this.anzahlTeilnehmer = anzahlTeilnehmer;
    }

    public int getMaxTeilnehmer() {
        return maxTeilnehmer;
    }

    public void setMaxTeilnehmer(int maxTeilnehmer) {
        this.maxTeilnehmer = maxTeilnehmer;
    }



}
