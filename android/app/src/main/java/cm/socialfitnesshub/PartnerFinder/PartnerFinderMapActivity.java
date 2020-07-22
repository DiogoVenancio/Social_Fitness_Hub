package cm.socialfitnesshub.PartnerFinder;

import android.os.Bundle;

import java.util.Observable;

import cm.socialfitnesshub.CustomViews.MapFinderActivity;
import cm.socialfitnesshub.Models.Activity;

public class PartnerFinderMapActivity extends MapFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDataForMap(Activity.getInstance().getActivities());
    }

    @Override
    public void update(Observable o, Object arg) {
        setupDataForMap(Activity.getInstance().getActivities());
    }
}