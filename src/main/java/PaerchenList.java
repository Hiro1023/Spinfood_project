//package models;

import java.util.ArrayList;
import java.util.List;

//import models.TeilnehmendeList;
//import models.Teilnehmer.Geschlecht;

public class PaerchenList {
    private int ageDiff = calculateAgeDifference();
    private double sexDiversity = calculateSexDiversity();
    private static  List<Paerchen> paerchenList = new ArrayList<>();
    private NachrueckendeList nachrueckendeList = new NachrueckendeList();
    private TeilnehmendeList teilnehmendeList = new TeilnehmendeList();
    

    public int calculateAgeDifference() {
        int totalAgeDiff = 0;

        for (Paerchen pair : paerchenList) {
            totalAgeDiff += pair.getAlterdifferenz();
        }

        return totalAgeDiff;
    }

    public int calculateTotalAgeDiff() {
        int totalAgeDiff = 0;
        for (Paerchen paerchen : paerchenList) {
            totalAgeDiff += paerchen.getAlterdifferenz();
        }
        return totalAgeDiff;
    }

    public double calculateSexDiversity() {
        int numMales = 0;
        int numFemales = 0;
        int numOther = 0;
    
        for (Paerchen pair : paerchenList) {
            if (pair.getTeilnehmer1().getSex() == Teilnehmer.SEX.male) {
                numMales++;
            } else if (pair.getTeilnehmer1().getSex() == Teilnehmer.SEX.female) {
                numFemales++;
            } else {
                numOther++;
            }
    
            if (pair.getTeilnehmer2().getSex() == Teilnehmer.SEX.male) {
                numMales++;
            } else if (pair.getTeilnehmer2().getSex() == Teilnehmer.SEX.female) {
                numFemales++;
            } else {
                numOther++;
            }
        }
    
        int totalPeople = numMales + numFemales + numOther;
        double idealRatio = 0.5;
        double femaleRatio = (double) numFemales / totalPeople;
        double maleRatio = (double) numMales / totalPeople;
        double otherRatio = (double) numOther / totalPeople;
    
        double femaleDeviation = Math.abs(femaleRatio - idealRatio);
        double maleDeviation = Math.abs(maleRatio - idealRatio);
        double otherDeviation = Math.abs(otherRatio - idealRatio);
    
        double sumDeviations = femaleDeviation + maleDeviation + otherDeviation;
        return sumDeviations / paerchenList.size();
    }
    

    public void addPairToList(Paerchen paerchen) {
        if(teilnehmendeList.getAnzahlTeilnehmer() + 2 <= teilnehmendeList.getMaxTeilnehmer()){
            paerchenList.add(paerchen);
            teilnehmendeList.incrementAnzahlteilnehmer(2);
        } else {
            nachrueckendeList.addPaerchen(paerchen);
        }
    }

    public void removePairFromList(Paerchen paerchen) {
        paerchenList.remove(paerchen);
        teilnehmendeList.decrementAnzahlteilnehmer(2);
        if (teilnehmendeList.getAnzahlTeilnehmer() + 2 <= teilnehmendeList.getMaxTeilnehmer()) {
            if (!nachrueckendeList.getNachrueckendePaerchen().isEmpty()) {
                Paerchen nachrueckendesPaerchen = nachrueckendeList.getNachrueckendePaerchen().remove(0);
                paerchenList.add(nachrueckendesPaerchen);
                teilnehmendeList.incrementAnzahlteilnehmer(2);
            }
        }
    }




    //getters


    public static  List<Paerchen> getpaerchenList(){return paerchenList;}


    public double getSexDiversity() {
        return sexDiversity;
    }

    public int getAgeDiff() {
        return ageDiff;
    }
    
}

