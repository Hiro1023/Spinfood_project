package utility;

import models.FOOD_PREFERENCE;
import modelsWrapper.FOOD_PREFERENCEWrapper;
import modelsWrapper.GroupWrapper;
import modelsWrapper.PairWrapper;
import modelsWrapper.ParticipantWrapper;

import java.util.List;

public interface MapUtility {
    FOOD_PREFERENCEWrapper mapFoodPreference(Object o);

    List<ParticipantWrapper> mapParticipantList(Object o);
    List<PairWrapper> mapPairList(Object o);
    List<GroupWrapper> mapGroupList(Object o);
}
