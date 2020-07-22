package cm.socialfitnesshub.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveMarker {
    private HashMap<Sports, List<PartnerFinder>> activitiesMap;

    public SaveMarker() {
        this.activitiesMap = new HashMap<>();
    }

    public void setActivities(List<? extends PartnerFinder> activities) {
        for (PartnerFinder pf : activities) {
            if (activitiesMap.containsKey(pf.getSport())) {
                activitiesMap.get(pf.getSport()).add(pf);
            } else {
                List<PartnerFinder> activitiesList = new ArrayList<>();
                activitiesList.add(pf);
                activitiesMap.put(pf.getSport(), activitiesList);
            }
        }
    }

    public void clearActivities() {
        activitiesMap.clear();
    }

    /*
    public void removeActivity(Sports sports, PartnerFinder activitiesMap)
    {
        this.activitiesMap.get(sports).
    }

    public void updateActivity(PartnerFinder activitiesMap)

    {

    }*/

    public HashMap<Sports, List<PartnerFinder>> getActivitiesMap() {
        return this.activitiesMap;
    }

    public void setActivitiesMap(HashMap<Sports, List<PartnerFinder>> activitiesMap) {
        this.activitiesMap = activitiesMap;

    }
}
