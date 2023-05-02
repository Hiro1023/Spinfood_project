import java.util.ArrayList;
import java.util.List;

public class TeilnehmendeList {
    private List<Teilnehmer> teilnehmerList = new ArrayList<>();
    private int anzahlTeilnehmer = 0;
    private int max;

    public TeilnehmendeList(){};

    public TeilnehmendeList(int max){
        this.max = max;
    }
    /**
     * add Teilnehmer in List
     * @param teilnehmer
     */
    public void addTeilnehmer(Teilnehmer teilnehmer){
        teilnehmerList.add(teilnehmer);
        anzahlTeilnehmer++;
    }
    /**
     * remove Teilnehmer from List
     * @param teilnehmer
     */
    public void removeTeilnehmer(Teilnehmer teilnehmer){
        teilnehmerList.remove(teilnehmer);
        anzahlTeilnehmer--;
    }

    public List<Teilnehmer> getTeilnehmerList(){
        return teilnehmerList;
    }

    public int getMax(){
        return this.max;
    }

    public int getAnzahlTeilnehmer(){
        return anzahlTeilnehmer;
    }
}
