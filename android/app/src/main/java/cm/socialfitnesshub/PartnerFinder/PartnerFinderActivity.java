package cm.socialfitnesshub.PartnerFinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Calendar;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.Models.Activity;
import cm.socialfitnesshub.Models.PartnerFinder;
import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.R;

public class PartnerFinderActivity extends ActivityWithBar {

    private Activity activity = Activity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.pf_main);
/*
        for(PartnerFinder pf : activity.getActivities())
        {
            if(pf.getDate().before(Calendar.getInstance().getTime()) && pf.getUserId().equals(ProfileManager.getInstance().getProfile().getProfileId()))
            {
                activity.removeActivity(pf);
            }
        }*/

        ImageView createActivity = findViewById(R.id.add_activity);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateActivity();
            }
        });

        ImageView mapActivity = findViewById(R.id.map);
        mapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActivity();
            }
        });

        ImageView statsActivity = findViewById(R.id.stats);
        statsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatsActivity();
            }
        });


        ImageView findActivity = findViewById(R.id.search_activity);
        findActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFindActivity();
            }
        });
    }

    private void openCreateActivity() {
        startActivity(new Intent(this, CreateActivity.class));
    }

    private void openMapActivity() {
        startActivity(new Intent(this, PartnerFinderMapActivity.class));
    }

    private void openStatsActivity() {
        startActivity(new Intent(this, Stats.class));
    }

    private void openFindActivity() {
        startActivity(new Intent(this, AllActivities.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
