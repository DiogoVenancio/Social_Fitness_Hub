package cm.socialfitnesshub.GroupActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Observable;

import cm.socialfitnesshub.CustomViews.MapFinderActivity;
import cm.socialfitnesshub.Models.Activity;
import cm.socialfitnesshub.R;

public class GroupActivitiesMapActivity extends MapFinderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDataForMap(Activity.getInstance().getGroupActivities());
    }

    @Override
    public void update(Observable o, Object arg) {
        setupDataForMap(Activity.getInstance().getGroupActivities());
    }
}