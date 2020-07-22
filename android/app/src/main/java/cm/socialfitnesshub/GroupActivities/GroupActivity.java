package cm.socialfitnesshub.GroupActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.R;

public class GroupActivity extends ActivityWithBar {

    private ImageView btnCreateGroupActivity, btnCompany, btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.ga_main);


        btnCreateGroupActivity = findViewById(R.id.add_group_activity);
        btnCreateGroupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateGroupActivity();
            }
        });

        btnCompany = findViewById(R.id.group_activity_companies);
        btnCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCompany();
            }
        });

        btnMap = findViewById(R.id.map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
    }

    private void openCompany() {
        startActivity(new Intent(this, CompanyActivity.class));
    }

    private void openCreateGroupActivity() {
        startActivity(new Intent(this, GroupCreateActivity.class));
    }

    private void openMap() {
        startActivity(new Intent(this, GroupActivitiesMapActivity.class));
    }
}
