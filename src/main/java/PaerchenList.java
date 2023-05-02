import java.util.ArrayList;
import java.util.List;

public class PaerchenList {
    private List<Paerchen> paerchenList = new ArrayList();

    public PaerchenList() {
    }

    public void addPairToList(Paerchen paerchen) {
        this.paerchenList.add(paerchen);
    }

    public void removePairFromList(Paerchen paerchen) {
        this.paerchenList.remove(paerchen);
    }

    public List<Paerchen> getpaerchenList(){return paerchenList;}
}
