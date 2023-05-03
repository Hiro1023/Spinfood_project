//package models;

import java.util.ArrayList;
import java.util.List;

public class NachrueckendeList {
    
    private List<Teilnehmer> nachrueckendeTeilnehmer = new ArrayList<>();
    private List<Paerchen> nachrueckendePaerchen = new ArrayList<>();
    private int anzahNachrueckende;


    public NachrueckendeList(){
        
    }

   

    public void addteilnehmer(Teilnehmer teilnehmer) {
        nachrueckendeTeilnehmer.add(teilnehmer);
        anzahNachrueckende++;
      
    }

    public void addPaerchen(Paerchen paerchen) {
        nachrueckendePaerchen.add(paerchen);
        anzahNachrueckende--;
    }

  
    // Getter methods for the canceled lists and anzahNachrueckende
    public List<Teilnehmer> getNachrueckendeTeilnehmer() {
        return nachrueckendeTeilnehmer;
    }

    public List<Paerchen> getNachrueckendePaerchen() {
        return nachrueckendePaerchen;
    }

    public int getAnzahNachrueckende() {
        return anzahNachrueckende;
    }
}
